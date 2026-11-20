package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.local.CurriculumJsonCodec
import com.example.data.local.QuestionEntity
import com.example.data.repository.ExamPilotRepository
import com.example.model.ExamType
import com.example.model.QuizMode
import com.example.model.ReviewAnswerRecord
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class ReviewFilter(val label: String) {
    ALL("All"),
    WRONG("Incorrect"),
    FLAGGED("Flagged"),
}

sealed interface QuizScreenStatus {
    data object Idle : QuizScreenStatus
    data object Loading : QuizScreenStatus
    data object Active : QuizScreenStatus
    data object Completed : QuizScreenStatus
    data class Empty(val message: String) : QuizScreenStatus
}

data class QuizUiState(
    val status: QuizScreenStatus = QuizScreenStatus.Idle,
    val mode: QuizMode = QuizMode.TOPIC_QUIZ,
    val examType: ExamType = ExamType.JAMB,
    val title: String = "",
    val subjectSummary: String = "",
    val topicId: String? = null,
    val questions: List<QuestionEntity> = emptyList(),
    val subjectNamesById: Map<String, String> = emptyMap(),
    val currentIndex: Int = 0,
    val selectedAnswers: Map<Int, Int> = emptyMap(),
    val revealedQuestions: Set<Int> = emptySet(),
    val flaggedQuestions: Set<Int> = emptySet(),
    val remainingSeconds: Int = 0,
    val totalDurationSeconds: Int = 0,
    val elapsedSeconds: Int = 0,
    val reviewItems: List<ReviewAnswerRecord> = emptyList(),
    val reviewFilter: ReviewFilter = ReviewFilter.ALL,
    val showPaletteSheet: Boolean = false,
) {
    val currentQuestion: QuestionEntity? get() = questions.getOrNull(currentIndex)
    val isCurrentRevealed: Boolean get() = revealedQuestions.contains(currentIndex)
    val score: Int get() = reviewItems.count { it.isCorrect }
    val totalQuestions: Int get() = if (reviewItems.isNotEmpty()) reviewItems.size else questions.size
}

class QuizViewModel(private val repository: ExamPilotRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    private var timerJob: Job? = null

    fun startTopicQuiz(topicId: String, examType: ExamType) {
        timerJob?.cancel()
        viewModelScope.launch {
            _uiState.value = QuizUiState(status = QuizScreenStatus.Loading, mode = QuizMode.TOPIC_QUIZ, examType = examType)
            val topic = repository.getTopicById(topicId)
            val questions = repository.getQuestionsForTopicQuiz(topicId)
            val subjectsMap = repository.getSubjectsMap().mapValues { it.value.name }
            if (questions.isEmpty() || topic == null) {
                _uiState.value = QuizUiState(
                    status = QuizScreenStatus.Empty("No quiz questions found for this topic yet."),
                )
                return@launch
            }
            val subjectName = subjectsMap[topic.subjectId] ?: "Subject"
            _uiState.value = QuizUiState(
                status = QuizScreenStatus.Active,
                mode = QuizMode.TOPIC_QUIZ,
                examType = examType,
                title = topic.title,
                subjectSummary = "$subjectName • ${topic.title}",
                topicId = topicId,
                questions = questions,
                subjectNamesById = subjectsMap,
                currentIndex = 0,
                elapsedSeconds = 0,
            )
            startElapsedTicker()
        }
    }

    fun startMockExam(
        examType: ExamType,
        subjectIds: List<String>,
        questionLimit: Int,
        durationMinutes: Int,
    ) {
        timerJob?.cancel()
        viewModelScope.launch {
            _uiState.value = QuizUiState(status = QuizScreenStatus.Loading, mode = QuizMode.CBT_MOCK, examType = examType)
            val subjectsMap = repository.getSubjectsMap().mapValues { it.value.name }
            val questions = repository.getQuestionsForMockExam(examType, subjectIds, questionLimit)
            if (questions.isEmpty()) {
                _uiState.value = QuizUiState(
                    status = QuizScreenStatus.Empty("Select at least one subject with questions to start a mock exam."),
                )
                return@launch
            }
            val selectedNames = subjectIds.mapNotNull { subjectsMap[it] }
            val summary = if (selectedNames.size <= 2) {
                selectedNames.joinToString(" & ")
            } else {
                "${selectedNames.take(2).joinToString(", ")} +${selectedNames.size - 2}"
            }
            val totalSecs = durationMinutes.coerceAtLeast(1) * 60
            _uiState.value = QuizUiState(
                status = QuizScreenStatus.Active,
                mode = QuizMode.CBT_MOCK,
                examType = examType,
                title = "${examType.shortName} CBT Mock",
                subjectSummary = summary,
                topicId = null,
                questions = questions,
                subjectNamesById = subjectsMap,
                currentIndex = 0,
                remainingSeconds = totalSecs,
                totalDurationSeconds = totalSecs,
                elapsedSeconds = 0,
            )
            startCountdownTimer()
        }
    }

    fun loadAttemptReview(attemptId: Long) {
        timerJob?.cancel()
        viewModelScope.launch {
            _uiState.value = QuizUiState(status = QuizScreenStatus.Loading)
            val attempt = repository.getAttemptById(attemptId)
            if (attempt == null) {
                _uiState.value = QuizUiState(status = QuizScreenStatus.Empty("Attempt record could not be found."))
                return@launch
            }
            val items = CurriculumJsonCodec.decodeReviewItems(attempt.reviewItemsJson)
            val mode = QuizMode.entries.firstOrNull { it.name == attempt.mode } ?: QuizMode.TOPIC_QUIZ
            val exam = ExamType.fromName(attempt.examType)
            _uiState.value = QuizUiState(
                status = QuizScreenStatus.Completed,
                mode = mode,
                examType = exam,
                title = if (mode == QuizMode.CBT_MOCK) "${exam.shortName} CBT Review" else "Topic Quiz Review",
                subjectSummary = attempt.subjectSummary,
                topicId = attempt.topicId,
                elapsedSeconds = attempt.durationSeconds,
                reviewItems = items,
                reviewFilter = ReviewFilter.ALL,
            )
        }
    }

    fun selectOption(optionIndex: Int) {
        val current = _uiState.value
        if (current.status != QuizScreenStatus.Active) return
        val qIdx = current.currentIndex
        if (current.mode == QuizMode.TOPIC_QUIZ && current.revealedQuestions.contains(qIdx)) {
            return // Instant-feedback quiz locks choice once revealed
        }
        _uiState.update { state ->
            val updatedAnswers = state.selectedAnswers.toMutableMap().apply { put(qIdx, optionIndex) }
            val updatedRevealed = if (state.mode == QuizMode.TOPIC_QUIZ) {
                state.revealedQuestions + qIdx
            } else {
                state.revealedQuestions
            }
            state.copy(
                selectedAnswers = updatedAnswers,
                revealedQuestions = updatedRevealed,
            )
        }
    }

    fun toggleFlagCurrent() {
        val current = _uiState.value
        val qIdx = current.currentIndex
        _uiState.update { state ->
            val updated = if (state.flaggedQuestions.contains(qIdx)) {
                state.flaggedQuestions - qIdx
            } else {
                state.flaggedQuestions + qIdx
            }
            state.copy(flaggedQuestions = updated)
        }
    }

    fun nextQuestion() {
        val current = _uiState.value
        if (current.currentIndex < current.questions.lastIndex) {
            _uiState.update { it.copy(currentIndex = it.currentIndex + 1) }
        }
    }

    fun previousQuestion() {
        val current = _uiState.value
        if (current.currentIndex > 0) {
            _uiState.update { it.copy(currentIndex = it.currentIndex - 1) }
        }
    }

    fun jumpToQuestion(index: Int) {
        val current = _uiState.value
        if (index in current.questions.indices) {
            _uiState.update { it.copy(currentIndex = index, showPaletteSheet = false) }
        }
    }

    fun setPaletteSheetVisible(visible: Boolean) {
        _uiState.update { it.copy(showPaletteSheet = visible) }
    }

    fun setReviewFilter(filter: ReviewFilter) {
        _uiState.update { it.copy(reviewFilter = filter) }
    }

    fun submitAssessment() {
        val current = _uiState.value
        if (current.status != QuizScreenStatus.Active) return
        timerJob?.cancel()

        val reviewRecords = current.questions.mapIndexed { idx, q ->
            ReviewAnswerRecord(
                questionId = q.id,
                subjectId = q.subjectId,
                subjectName = current.subjectNamesById[q.subjectId] ?: q.subjectId.uppercase(),
                prompt = q.prompt,
                options = listOf(q.optionA, q.optionB, q.optionC, q.optionD),
                selectedIndex = current.selectedAnswers[idx],
                correctIndex = q.correctIndex,
                explanation = q.explanation,
                isFlagged = current.flaggedQuestions.contains(idx),
            )
        }

        _uiState.update {
            it.copy(
                status = QuizScreenStatus.Completed,
                reviewItems = reviewRecords,
                showPaletteSheet = false,
            )
        }

        viewModelScope.launch {
            repository.recordAssessmentAttempt(
                mode = current.mode,
                examType = current.examType,
                subjectSummary = current.subjectSummary,
                topicId = current.topicId,
                durationSeconds = current.elapsedSeconds,
                reviewItems = reviewRecords,
            )
        }
    }

    private fun startElapsedTicker() {
        timerJob = viewModelScope.launch {
            while (_uiState.value.status == QuizScreenStatus.Active) {
                delay(1_000L)
                _uiState.update { it.copy(elapsedSeconds = it.elapsedSeconds + 1) }
            }
        }
    }

    private fun startCountdownTimer() {
        timerJob = viewModelScope.launch {
            while (_uiState.value.status == QuizScreenStatus.Active) {
                delay(1_000L)
                val nextRemaining = (_uiState.value.remainingSeconds - 1).coerceAtLeast(0)
                _uiState.update {
                    it.copy(
                        remainingSeconds = nextRemaining,
                        elapsedSeconds = it.elapsedSeconds + 1,
                    )
                }
                if (nextRemaining == 0) {
                    submitAssessment()
                    break
                }
            }
        }
    }

    override fun onCleared() {
        timerJob?.cancel()
        super.onCleared()
    }

    class Factory(private val repository: ExamPilotRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = QuizViewModel(repository) as T
    }
}
