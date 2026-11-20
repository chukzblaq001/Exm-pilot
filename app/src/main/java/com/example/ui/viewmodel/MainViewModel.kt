package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.local.AssessmentAttemptEntity
import com.example.data.local.UserPreferenceEntity
import com.example.data.repository.AuthResult
import com.example.data.repository.ExamPilotRepository
import com.example.data.repository.TopicWithProgress
import com.example.model.ExamType
import com.example.model.SubjectProgressSummary
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class MainUiState(
    val isLoading: Boolean = true,
    val preferences: UserPreferenceEntity = UserPreferenceEntity(),
    val activeExam: ExamType = ExamType.JAMB,
    val allSubjects: List<SubjectProgressSummary> = emptyList(),
    val selectedSubjects: List<SubjectProgressSummary> = emptyList(),
    val subjectsByExam: Map<ExamType, List<SubjectProgressSummary>> = emptyMap(),
    val allTopics: List<TopicWithProgress> = emptyList(),
    val recentAttempts: List<AssessmentAttemptEntity> = emptyList(),
    val resumeTopic: TopicWithProgress? = null,
    val totalMasteredTopics: Int = 0,
    val totalSelectedTopics: Int = 0,
    val overallMasteryPercent: Int = 0,
    val averageAccuracyPercent: Int? = null,
    val feedbackMessage: String? = null,
    val authError: String? = null,
    val isAuthBusy: Boolean = false,
)

private data class CombinedAuxState(
    val feedback: String?,
    val authError: String?,
    val isAuthBusy: Boolean,
)

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModel(private val repository: ExamPilotRepository) : ViewModel() {
    private val feedbackFlow = MutableStateFlow<String?>(null)
    private val authErrorFlow = MutableStateFlow<String?>(null)
    private val authBusyFlow = MutableStateFlow(false)

    private val allExamsSubjectsFlow = combine(
        repository.observeSubjectSummaries(ExamType.WAEC),
        repository.observeSubjectSummaries(ExamType.JAMB),
        repository.observeSubjectSummaries(ExamType.NECO),
    ) { waec, jamb, neco ->
        mapOf(
            ExamType.WAEC to waec,
            ExamType.JAMB to jamb,
            ExamType.NECO to neco,
        )
    }

    private val topicsForActiveExam = repository.preferences.flatMapLatest { prefs ->
        repository.observeAllTopicsWithProgress(ExamType.fromName(prefs.activeExamName))
    }

    private val auxFlow = combine(feedbackFlow, authErrorFlow, authBusyFlow) { feedback, authError, busy ->
        CombinedAuxState(feedback = feedback, authError = authError, isAuthBusy = busy)
    }

    val uiState: StateFlow<MainUiState> = combine(
        repository.preferences,
        allExamsSubjectsFlow,
        topicsForActiveExam,
        repository.observeAttempts(),
        auxFlow,
    ) { prefs, subjectsMap, topics, attempts, aux ->
        val activeExam = ExamType.fromName(prefs.activeExamName)
        val subjects = subjectsMap[activeExam].orEmpty()
        val selected = subjects.filter { it.isSelected }
        val selectedIds = selected.map { it.subjectId }.toSet()
        val selectedTopics = topics.filter { selectedIds.contains(it.topic.subjectId) }
        val masteredCount = selectedTopics.count { it.isMastered }
        val totalTopicsCount = selectedTopics.size
        val masteryPct = if (totalTopicsCount == 0) 0 else ((masteredCount * 100f) / totalTopicsCount).toInt()

        val avgAccuracy = if (attempts.isEmpty()) {
            null
        } else {
            attempts.map { attempt ->
                if (attempt.totalQuestions == 0) 0 else (attempt.score * 100) / attempt.totalQuestions
            }.average().toInt()
        }

        val resume = selectedTopics
            .filter { it.lastStudiedAt > 0L && !it.isMastered }
            .maxByOrNull { it.lastStudiedAt }
            ?: selectedTopics.firstOrNull { !it.isMastered }
            ?: selectedTopics.firstOrNull()

        MainUiState(
            isLoading = false,
            preferences = prefs,
            activeExam = activeExam,
            allSubjects = subjects,
            selectedSubjects = selected,
            subjectsByExam = subjectsMap,
            allTopics = topics,
            recentAttempts = attempts,
            resumeTopic = resume,
            totalMasteredTopics = masteredCount,
            totalSelectedTopics = totalTopicsCount,
            overallMasteryPercent = masteryPct,
            averageAccuracyPercent = avgAccuracy,
            feedbackMessage = aux.feedback,
            authError = aux.authError,
            isAuthBusy = aux.isAuthBusy,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MainUiState(),
    )

    init {
        viewModelScope.launch {
            repository.ensureSeeded()
        }
    }

    fun markWelcomeSeen() {
        viewModelScope.launch {
            repository.markWelcomeSeen()
        }
    }

    fun signUp(fullName: String, email: String, password: String) {
        viewModelScope.launch {
            authBusyFlow.value = true
            authErrorFlow.value = null
            when (val result = repository.signUp(fullName, email, password)) {
                is AuthResult.Success -> Unit
                is AuthResult.Error -> authErrorFlow.value = result.message
            }
            authBusyFlow.value = false
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            authBusyFlow.value = true
            authErrorFlow.value = null
            when (val result = repository.login(email, password)) {
                is AuthResult.Success -> Unit
                is AuthResult.Error -> authErrorFlow.value = result.message
            }
            authBusyFlow.value = false
        }
    }

    fun continueAsGuest() {
        viewModelScope.launch {
            authErrorFlow.value = null
            repository.continueAsGuest()
        }
    }

    fun logout() {
        viewModelScope.launch {
            authErrorFlow.value = null
            repository.logout()
        }
    }

    fun clearAuthError() {
        authErrorFlow.value = null
    }

    fun saveSubjectSelectionSetup(examType: ExamType, subjectIds: Set<String>) {
        viewModelScope.launch {
            val error = repository.saveSubjectSelectionSetup(examType, subjectIds)
            if (error != null) {
                feedbackFlow.value = error
            }
        }
    }

    fun reopenSubjectSetup() {
        viewModelScope.launch {
            repository.reopenSubjectSetup()
        }
    }

    fun switchExam(examType: ExamType) {
        viewModelScope.launch {
            repository.setActiveExam(examType)
        }
    }

    fun toggleSubject(subjectId: String) {
        val activeExam = uiState.value.activeExam
        viewModelScope.launch {
            val errorOrNotice = repository.toggleSubjectSelection(activeExam, subjectId)
            if (errorOrNotice != null) {
                feedbackFlow.value = errorOrNotice
            }
        }
    }

    fun applyRecommendedSubjects(examType: ExamType) {
        viewModelScope.launch {
            repository.applyRecommendedSubjects(examType)
            feedbackFlow.value = "Loaded recommended ${examType.shortName} subjects."
        }
    }

    fun updateStudentName(name: String) {
        viewModelScope.launch {
            repository.updateStudentName(name)
            feedbackFlow.value = "Profile name updated in Room."
        }
    }

    fun setDarkModeOverride(darkMode: Boolean?) {
        viewModelScope.launch {
            repository.setDarkModeOverride(darkMode)
        }
    }

    fun resetProgress() {
        viewModelScope.launch {
            repository.resetStudyProgress()
            feedbackFlow.value = "Study progress and quiz history cleared."
        }
    }

    fun clearFeedback() {
        feedbackFlow.value = null
    }

    class Factory(private val repository: ExamPilotRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = MainViewModel(repository) as T
    }
}
