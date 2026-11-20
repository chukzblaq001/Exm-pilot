package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ExamPilotDao {
    @Query("SELECT * FROM subjects ORDER BY sortOrder ASC")
    fun observeSubjects(): Flow<List<SubjectEntity>>

    @Query("SELECT COUNT(*) FROM subjects")
    suspend fun countSubjects(): Int

    @Query("SELECT * FROM topics ORDER BY orderIndex ASC")
    fun observeAllTopics(): Flow<List<TopicEntity>>

    @Query("SELECT * FROM topics WHERE subjectId = :subjectId ORDER BY orderIndex ASC")
    fun observeTopicsForSubject(subjectId: String): Flow<List<TopicEntity>>

    @Query("SELECT * FROM topics WHERE id = :topicId LIMIT 1")
    fun observeTopic(topicId: String): Flow<TopicEntity?>

    @Query("SELECT * FROM topics WHERE id = :topicId LIMIT 1")
    suspend fun getTopicById(topicId: String): TopicEntity?

    @Query("SELECT * FROM questions")
    fun observeAllQuestions(): Flow<List<QuestionEntity>>

    @Query("SELECT * FROM questions WHERE topicId = :topicId")
    suspend fun getQuestionsForTopic(topicId: String): List<QuestionEntity>

    @Query("SELECT * FROM questions WHERE subjectId IN (:subjectIds)")
    suspend fun getQuestionsForSubjects(subjectIds: List<String>): List<QuestionEntity>

    @Query("SELECT * FROM user_subject_selections WHERE examType = :examType")
    fun observeSelectedSubjects(examType: String): Flow<List<UserSubjectSelectionEntity>>

    @Query("SELECT COUNT(*) FROM user_subject_selections WHERE examType = :examType")
    suspend fun countSelectedSubjects(examType: String): Int

    @Query("SELECT * FROM topic_progress")
    fun observeAllTopicProgress(): Flow<List<TopicProgressEntity>>

    @Query("SELECT * FROM topic_progress WHERE topicId = :topicId LIMIT 1")
    fun observeTopicProgress(topicId: String): Flow<TopicProgressEntity?>

    @Query("SELECT * FROM topic_progress WHERE topicId = :topicId LIMIT 1")
    suspend fun getTopicProgress(topicId: String): TopicProgressEntity?

    @Query("SELECT * FROM assessment_attempts ORDER BY completedAt DESC")
    fun observeAttempts(): Flow<List<AssessmentAttemptEntity>>

    @Query("SELECT * FROM assessment_attempts WHERE id = :attemptId LIMIT 1")
    suspend fun getAttemptById(attemptId: Long): AssessmentAttemptEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubjects(subjects: List<SubjectEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopics(topics: List<TopicEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<QuestionEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertSubjectSelection(selection: UserSubjectSelectionEntity)

    @Query("DELETE FROM user_subject_selections WHERE examType = :examType AND subjectId = :subjectId")
    suspend fun deleteSubjectSelection(examType: String, subjectId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertTopicProgress(progress: TopicProgressEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttempt(attempt: AssessmentAttemptEntity): Long

    @Query("DELETE FROM topic_progress")
    suspend fun clearTopicProgress()

    @Query("DELETE FROM assessment_attempts")
    suspend fun clearAttempts()

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUserAccount(account: UserAccountEntity): Long

    @Query("SELECT * FROM user_accounts WHERE LOWER(email) = LOWER(:email) LIMIT 1")
    suspend fun getUserAccountByEmail(email: String): UserAccountEntity?

    @Query("SELECT * FROM user_accounts WHERE id = :userId LIMIT 1")
    suspend fun getUserAccountById(userId: Long): UserAccountEntity?

    @Query("UPDATE user_accounts SET fullName = :fullName WHERE id = :userId")
    suspend fun updateUserAccountName(userId: Long, fullName: String)

    @Query("SELECT * FROM user_preferences WHERE id = 1 LIMIT 1")
    fun observeUserPreferences(): Flow<UserPreferenceEntity?>

    @Query("SELECT * FROM user_preferences WHERE id = 1 LIMIT 1")
    suspend fun getUserPreferencesOnce(): UserPreferenceEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertUserPreferences(preferences: UserPreferenceEntity)

    @Query("DELETE FROM user_subject_selections WHERE examType = :examType")
    suspend fun clearSubjectSelectionsForExam(examType: String)
}
