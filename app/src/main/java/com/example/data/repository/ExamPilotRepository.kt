package com.example.data.repository

import com.example.data.local.AssessmentAttemptEntity
import com.example.data.local.CurriculumJsonCodec
import com.example.data.local.ExamPilotDao
import com.example.data.local.QuestionEntity
import com.example.data.local.SubjectEntity
import com.example.data.local.TopicEntity
import com.example.data.local.TopicProgressEntity
import com.example.data.local.UserAccountEntity
import com.example.data.local.UserPreferenceEntity
import com.example.data.local.UserPreferencesDataStore
import com.example.data.local.UserSubjectSelectionEntity
import com.example.data.seed.CurriculumSeedProvider
import com.example.model.ExamType
import com.example.model.LessonSection
import com.example.model.QuizMode
import com.example.model.ReviewAnswerRecord
import com.example.model.SubjectCategory
import com.example.model.SubjectProgressSummary
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.security.MessageDigest
import java.security.SecureRandom

data class TopicWithProgress(
    val topic: TopicEntity,
    val isMastered: Boolean,
    val bestQuizPercent: Int?,
    val lastStudiedAt: Long,
    val questionCount: Int,
)

data class TopicDetailModel(
    val topic: TopicEntity,
    val subject: SubjectEntity,
    val sections: List<LessonSection>,
    val keyFormulas: List<String>,
    val isMastered: Boolean,
    val bestQuizPercent: Int?,
    val questionCount: Int,
)

sealed interface AuthResult {
    data class Success(val account: UserAccountEntity) : AuthResult
    data class Error(val message: String) : AuthResult
}

class ExamPilotRepository(
    private val dao: ExamPilotDao,
    @Suppress("unused") private val prefsStore: UserPreferencesDataStore? = null,
) {
    val preferences: Flow<UserPreferenceEntity> = dao.observeUserPreferences()
        .map { it ?: UserPreferenceEntity() }

    suspend fun ensureSeeded() {
        val bundle = CurriculumSeedProvider.buildSeedBundle()
        if (dao.countSubjects() < bundle.subjects.size) {
            dao.insertSubjects(bundle.subjects)
            dao.insertTopics(bundle.topics)
            dao.insertQuestions(bundle.questions)
        }
        if (dao.getUserPreferencesOnce() == null) {
            dao.upsertUserPreferences(UserPreferenceEntity())
        }
        // Ensure each exam board has valid starter selections in Room if none exist yet
        ExamType.entries.forEach { exam ->
            if (dao.countSelectedSubjects(exam.name) == 0) {
                defaultSubjectIdsFor(exam).forEach { subjectId ->
                    dao.upsertSubjectSelection(
                        UserSubjectSelectionEntity(examType = exam.name, subjectId = subjectId),
                    )
                }
            }
        }
    }

    fun defaultSubjectIdsFor(exam: ExamType): List<String> = when (exam) {
        ExamType.JAMB -> listOf("eng", "mth", "phy", "chm")
        ExamType.WAEC -> listOf("eng", "mth", "civic", "bio", "chm", "phy", "eco", "agr")
        ExamType.NECO -> listOf("eng", "mth", "civic", "eco", "gov", "lit", "com", "crs")
    }

    private fun String.supportsExam(examType: ExamType): Boolean =
        split(",").any { it.trim().equals(examType.name, ignoreCase = true) }

    fun observeSubjectSummaries(examType: ExamType): Flow<List<SubjectProgressSummary>> =
        combine(
            dao.observeSubjects(),
            dao.observeAllTopics(),
            dao.observeAllQuestions(),
            dao.observeAllTopicProgress(),
            dao.observeSelectedSubjects(examType.name),
        ) { subjects, topics, questions, progressList, selections ->
            val selectedIds = selections.map { it.subjectId }.toSet()
            val progressMap = progressList.associateBy { it.topicId }
            val examTopics = topics.filter { it.examTagsCsv.supportsExam(examType) }
            val examTopicIds = examTopics.map { it.id }.toSet()
            val topicsBySubject = examTopics.groupBy { it.subjectId }
            val questionsBySubject = questions
                .filter { examTopicIds.contains(it.topicId) }
                .groupBy { it.subjectId }

            subjects
                .filter { it.supportedExamsCsv.supportsExam(examType) }
                .map { subject ->
                    val subjectTopics = topicsBySubject[subject.id].orEmpty()
                    val masteredCount = subjectTopics.count { progressMap[it.id]?.isMastered == true }
                    val quizScores = subjectTopics.mapNotNull { progressMap[it.id]?.bestQuizPercent }
                    val avgScore = if (quizScores.isEmpty()) null else quizScores.average().toInt()
                    SubjectProgressSummary(
                        subjectId = subject.id,
                        name = subject.name,
                        code = subject.code,
                        category = SubjectCategory.entries.firstOrNull { it.name == subject.categoryName }
                            ?: SubjectCategory.CORE,
                        tagline = subject.tagline,
                        totalTopics = subjectTopics.size,
                        masteredTopics = masteredCount,
                        totalQuestions = questionsBySubject[subject.id]?.size ?: 0,
                        averageQuizPercent = avgScore,
                        isSelected = selectedIds.contains(subject.id),
                    )
                }
        }

    fun observeTopicsForSubject(subjectId: String, examType: ExamType): Flow<List<TopicWithProgress>> =
        combine(
            dao.observeTopicsForSubject(subjectId),
            dao.observeAllTopicProgress(),
            dao.observeAllQuestions(),
        ) { topics, progressList, questions ->
            val progressMap = progressList.associateBy { it.topicId }
            val qCounts = questions.groupingBy { it.topicId }.eachCount()
            topics
                .filter { it.examTagsCsv.supportsExam(examType) }
                .map { topic ->
                    val progress = progressMap[topic.id]
                    TopicWithProgress(
                        topic = topic,
                        isMastered = progress?.isMastered == true,
                        bestQuizPercent = progress?.bestQuizPercent,
                        lastStudiedAt = progress?.lastStudiedAt ?: 0L,
                        questionCount = qCounts[topic.id] ?: 0,
                    )
                }
        }

    fun observeTopicDetail(topicId: String): Flow<TopicDetailModel?> =
        combine(
            dao.observeTopic(topicId),
            dao.observeSubjects(),
            dao.observeTopicProgress(topicId),
            dao.observeAllQuestions(),
        ) { topic, subjects, progress, questions ->
            if (topic == null) return@combine null
            val subject = subjects.firstOrNull { it.id == topic.subjectId } ?: return@combine null
            TopicDetailModel(
                topic = topic,
                subject = subject,
                sections = CurriculumJsonCodec.decodeLessonSections(topic.lessonSectionsJson),
                keyFormulas = CurriculumJsonCodec.decodeStringList(topic.keyFormulasJson),
                isMastered = progress?.isMastered == true,
                bestQuizPercent = progress?.bestQuizPercent,
                questionCount = questions.count { it.topicId == topic.id },
            )
        }

    fun observeAllTopicsWithProgress(examType: ExamType): Flow<List<TopicWithProgress>> =
        combine(
            dao.observeAllTopics(),
            dao.observeAllTopicProgress(),
            dao.observeAllQuestions(),
        ) { topics, progressList, questions ->
            val progressMap = progressList.associateBy { it.topicId }
            val qCounts = questions.groupingBy { it.topicId }.eachCount()
            topics
                .filter { it.examTagsCsv.supportsExam(examType) }
                .map { topic ->
                    val progress = progressMap[topic.id]
                    TopicWithProgress(
                        topic = topic,
                        isMastered = progress?.isMastered == true,
                        bestQuizPercent = progress?.bestQuizPercent,
                        lastStudiedAt = progress?.lastStudiedAt ?: 0L,
                        questionCount = qCounts[topic.id] ?: 0,
                    )
                }
        }

    fun observeSubject(subjectId: String): Flow<SubjectEntity?> =
        dao.observeSubjects().map { list -> list.firstOrNull { it.id == subjectId } }

    fun observeAttempts(): Flow<List<AssessmentAttemptEntity>> = dao.observeAttempts()

    suspend fun markWelcomeSeen() {
        val current = dao.getUserPreferencesOnce() ?: UserPreferenceEntity()
        dao.upsertUserPreferences(
            current.copy(
                hasSeenWelcome = true,
                updatedAt = System.currentTimeMillis(),
            ),
        )
    }

    suspend fun signUp(fullName: String, email: String, password: String): AuthResult {
        val trimmedName = fullName.trim()
        val trimmedEmail = email.trim().lowercase()
        if (trimmedName.length < 2) {
            return AuthResult.Error("Please enter your full name (at least 2 characters).")
        }
        if (!trimmedEmail.contains("@") || !trimmedEmail.contains(".")) {
            return AuthResult.Error("Please enter a valid email address.")
        }
        if (password.length < 6) {
            return AuthResult.Error("Password must be at least 6 characters.")
        }
        val existing = dao.getUserAccountByEmail(trimmedEmail)
        if (existing != null) {
            return AuthResult.Error("An account with this email already exists. Please log in.")
        }

        val salt = generateSalt()
        val hash = hashPassword(password, salt)
        val account = UserAccountEntity(
            fullName = trimmedName,
            email = trimmedEmail,
            passwordSalt = salt,
            passwordHash = hash,
        )
        val newId = dao.insertUserAccount(account)
        val savedAccount = account.copy(id = newId)

        val currentPrefs = dao.getUserPreferencesOnce() ?: UserPreferenceEntity()
        dao.upsertUserPreferences(
            currentPrefs.copy(
                loggedInUserId = newId,
                studentName = trimmedName,
                studentEmail = trimmedEmail,
                isGuest = false,
                hasSeenWelcome = true,
                isAuthenticated = true,
                updatedAt = System.currentTimeMillis(),
            ),
        )
        return AuthResult.Success(savedAccount)
    }

    suspend fun login(email: String, password: String): AuthResult {
        val trimmedEmail = email.trim().lowercase()
        if (trimmedEmail.isBlank() || password.isBlank()) {
            return AuthResult.Error("Enter both your email and password.")
        }
        val account = dao.getUserAccountByEmail(trimmedEmail)
            ?: return AuthResult.Error("No local account found for $trimmedEmail.")
        val computedHash = hashPassword(password, account.passwordSalt)
        if (computedHash != account.passwordHash) {
            return AuthResult.Error("Incorrect password. Please try again.")
        }

        val currentPrefs = dao.getUserPreferencesOnce() ?: UserPreferenceEntity()
        dao.upsertUserPreferences(
            currentPrefs.copy(
                loggedInUserId = account.id,
                studentName = account.fullName,
                studentEmail = account.email,
                isGuest = false,
                hasSeenWelcome = true,
                isAuthenticated = true,
                updatedAt = System.currentTimeMillis(),
            ),
        )
        return AuthResult.Success(account)
    }

    suspend fun continueAsGuest() {
        val currentPrefs = dao.getUserPreferencesOnce() ?: UserPreferenceEntity()
        dao.upsertUserPreferences(
            currentPrefs.copy(
                loggedInUserId = null,
                studentName = currentPrefs.studentName.ifBlank { "Scholar" },
                studentEmail = "",
                isGuest = true,
                hasSeenWelcome = true,
                isAuthenticated = true,
                updatedAt = System.currentTimeMillis(),
            ),
        )
    }

    suspend fun logout() {
        val currentPrefs = dao.getUserPreferencesOnce() ?: UserPreferenceEntity()
        dao.upsertUserPreferences(
            currentPrefs.copy(
                loggedInUserId = null,
                isGuest = false,
                isAuthenticated = false,
                updatedAt = System.currentTimeMillis(),
            ),
        )
    }

    suspend fun reopenSubjectSetup() {
        val currentPrefs = dao.getUserPreferencesOnce() ?: UserPreferenceEntity()
        dao.upsertUserPreferences(
            currentPrefs.copy(
                hasCompletedSubjectSetup = false,
                updatedAt = System.currentTimeMillis(),
            ),
        )
    }

    suspend fun saveSubjectSelectionSetup(
        examType: ExamType,
        selectedSubjectIds: Set<String>,
    ): String? {
        val normalized = if (examType.mandatorySubjectId != null) {
            selectedSubjectIds + examType.mandatorySubjectId
        } else {
            selectedSubjectIds
        }
        if (!examType.isSelectionCountValid(normalized.size)) {
            return when (examType) {
                ExamType.JAMB -> "JAMB requires Use of English plus 3 other subjects (4 total)."
                ExamType.WAEC -> "WAEC requires 8 to 9 subjects (currently ${normalized.size})."
                ExamType.NECO -> "NECO requires 8 to 9 subjects (currently ${normalized.size})."
            }
        }
        setSelectedSubjects(examType, normalized)
        val currentPrefs = dao.getUserPreferencesOnce() ?: UserPreferenceEntity()
        dao.upsertUserPreferences(
            currentPrefs.copy(
                activeExamName = examType.name,
                hasSeenWelcome = true,
                hasCompletedSubjectSetup = true,
                updatedAt = System.currentTimeMillis(),
            ),
        )
        return null
    }

    suspend fun setActiveExam(examType: ExamType) {
        val currentPrefs = dao.getUserPreferencesOnce() ?: UserPreferenceEntity()
        dao.upsertUserPreferences(
            currentPrefs.copy(
                activeExamName = examType.name,
                updatedAt = System.currentTimeMillis(),
            ),
        )
    }

    suspend fun updateStudentName(name: String) {
        val clean = name.trim().ifBlank { "Scholar" }
        val currentPrefs = dao.getUserPreferencesOnce() ?: UserPreferenceEntity()
        currentPrefs.loggedInUserId?.let { userId ->
            dao.updateUserAccountName(userId, clean)
        }
        dao.upsertUserPreferences(
            currentPrefs.copy(
                studentName = clean,
                updatedAt = System.currentTimeMillis(),
            ),
        )
    }

    suspend fun setDarkModeOverride(darkMode: Boolean?) {
        val currentPrefs = dao.getUserPreferencesOnce() ?: UserPreferenceEntity()
        dao.upsertUserPreferences(
            currentPrefs.copy(
                darkModeOverride = darkMode,
                updatedAt = System.currentTimeMillis(),
            ),
        )
    }

    suspend fun setSelectedSubjects(examType: ExamType, subjectIds: Set<String>) {
        val finalSet = if (examType.mandatorySubjectId != null) {
            subjectIds + examType.mandatorySubjectId
        } else {
            subjectIds
        }
        if (finalSet.isEmpty()) return

        dao.clearSubjectSelectionsForExam(examType.name)
        finalSet.forEach { subjectId ->
            dao.upsertSubjectSelection(
                UserSubjectSelectionEntity(examType = examType.name, subjectId = subjectId),
            )
        }
    }

    suspend fun toggleSubjectSelection(examType: ExamType, subjectId: String): String? {
        if (examType.mandatorySubjectId == subjectId) {
            return "${examType.shortName} requires Use of English."
        }
        val current = dao.observeSelectedSubjects(examType.name).first().map { it.subjectId }.toSet()
        return if (current.contains(subjectId)) {
            if (current.size <= 1) {
                "Keep at least 1 active subject."
            } else {
                dao.deleteSubjectSelection(examType.name, subjectId)
                null
            }
        } else {
            if (current.size >= examType.maxSubjects) {
                when (examType) {
                    ExamType.JAMB -> "JAMB UTME uses 4 subjects (Use of English + 3 electives). Deselect one first."
                    else -> "${examType.shortName} allows up to ${examType.maxSubjects} subjects. Deselect one first."
                }
            } else {
                dao.upsertSubjectSelection(
                    UserSubjectSelectionEntity(examType = examType.name, subjectId = subjectId),
                )
                null
            }
        }
    }

    suspend fun applyRecommendedSubjects(examType: ExamType) {
        setSelectedSubjects(examType, defaultSubjectIdsFor(examType).toSet())
    }

    suspend fun markTopicStudied(topicId: String) {
        val existing = dao.getTopicProgress(topicId)
        dao.upsertTopicProgress(
            TopicProgressEntity(
                topicId = topicId,
                isMastered = existing?.isMastered ?: false,
                lastStudiedAt = System.currentTimeMillis(),
                bestQuizPercent = existing?.bestQuizPercent,
            ),
        )
    }

    suspend fun setTopicMastered(topicId: String, mastered: Boolean) {
        val existing = dao.getTopicProgress(topicId)
        dao.upsertTopicProgress(
            TopicProgressEntity(
                topicId = topicId,
                isMastered = mastered,
                lastStudiedAt = System.currentTimeMillis(),
                bestQuizPercent = existing?.bestQuizPercent,
            ),
        )
    }

    suspend fun getQuestionsForTopicQuiz(topicId: String): List<QuestionEntity> =
        dao.getQuestionsForTopic(topicId).shuffled()

    suspend fun getQuestionsForMockExam(
        examType: ExamType,
        subjectIds: List<String>,
        questionLimit: Int,
    ): List<QuestionEntity> {
        if (subjectIds.isEmpty()) return emptyList()
        val allowedTopicIds = dao.observeAllTopics().first()
            .filter { it.examTagsCsv.supportsExam(examType) }
            .map { it.id }
            .toSet()

        val allSubjectQuestions = dao.getQuestionsForSubjects(subjectIds)
        val examScoped = allSubjectQuestions
            .filter { allowedTopicIds.contains(it.topicId) }
            .ifEmpty { allSubjectQuestions }
            .shuffled()

        val grouped = examScoped.groupBy { it.subjectId }.mapValues { it.value.toMutableList() }
        val picked = mutableListOf<QuestionEntity>()
        while (picked.size < questionLimit) {
            var addedInRound = false
            for (subjectId in subjectIds) {
                val pool = grouped[subjectId]
                if (!pool.isNullOrEmpty() && picked.size < questionLimit) {
                    picked.add(pool.removeAt(0))
                    addedInRound = true
                }
            }
            if (!addedInRound) break
        }
        return picked
    }

    suspend fun getSubjectsMap(): Map<String, SubjectEntity> =
        dao.observeSubjects().first().associateBy { it.id }

    suspend fun getTopicById(topicId: String): TopicEntity? = dao.getTopicById(topicId)

    suspend fun recordAssessmentAttempt(
        mode: QuizMode,
        examType: ExamType,
        subjectSummary: String,
        topicId: String?,
        durationSeconds: Int,
        reviewItems: List<ReviewAnswerRecord>,
    ): Long {
        val score = reviewItems.count { it.isCorrect }
        val total = reviewItems.size.coerceAtLeast(1)
        val percent = ((score.toFloat() / total.toFloat()) * 100f).toInt()

        if (topicId != null) {
            val existing = dao.getTopicProgress(topicId)
            val best = maxOf(existing?.bestQuizPercent ?: 0, percent)
            dao.upsertTopicProgress(
                TopicProgressEntity(
                    topicId = topicId,
                    isMastered = existing?.isMastered ?: false,
                    lastStudiedAt = System.currentTimeMillis(),
                    bestQuizPercent = best,
                ),
            )
        }

        val attempt = AssessmentAttemptEntity(
            mode = mode.name,
            examType = examType.name,
            subjectSummary = subjectSummary,
            topicId = topicId,
            score = score,
            totalQuestions = reviewItems.size,
            durationSeconds = durationSeconds,
            completedAt = System.currentTimeMillis(),
            reviewItemsJson = CurriculumJsonCodec.encodeReviewItems(reviewItems),
        )
        return dao.insertAttempt(attempt)
    }

    suspend fun getAttemptById(attemptId: Long): AssessmentAttemptEntity? =
        dao.getAttemptById(attemptId)

    suspend fun resetStudyProgress() {
        dao.clearTopicProgress()
        dao.clearAttempts()
    }

    private fun generateSalt(): String {
        val bytes = ByteArray(16)
        SecureRandom().nextBytes(bytes)
        return bytes.joinToString("") { "%02x".format(it) }
    }

    private fun hashPassword(password: String, salt: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val input = "$salt:$password".toByteArray(Charsets.UTF_8)
        return digest.digest(input).joinToString("") { "%02x".format(it) }
    }
}
