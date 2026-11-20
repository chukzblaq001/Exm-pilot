package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.local.SubjectEntity
import com.example.data.repository.ExamPilotRepository
import com.example.data.repository.TopicDetailModel
import com.example.data.repository.TopicWithProgress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class SubjectTopicsUiState(
    val isLoading: Boolean = true,
    val subject: SubjectEntity? = null,
    val topics: List<TopicWithProgress> = emptyList(),
)

class StudyViewModel(private val repository: ExamPilotRepository) : ViewModel() {
    fun observeSubjectTopics(
        subjectId: String,
        examType: com.example.model.ExamType,
    ): StateFlow<SubjectTopicsUiState> =
        combine(
            repository.observeSubject(subjectId),
            repository.observeTopicsForSubject(subjectId, examType),
        ) { subject, topics ->
            SubjectTopicsUiState(
                isLoading = false,
                subject = subject,
                topics = topics,
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SubjectTopicsUiState(),
        )

    fun observeTopicDetail(topicId: String): Flow<TopicDetailModel?> =
        repository.observeTopicDetail(topicId)

    fun markTopicStudied(topicId: String) {
        viewModelScope.launch {
            repository.markTopicStudied(topicId)
        }
    }

    fun setTopicMastered(topicId: String, mastered: Boolean) {
        viewModelScope.launch {
            repository.setTopicMastered(topicId, mastered)
        }
    }

    class Factory(private val repository: ExamPilotRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = StudyViewModel(repository) as T
    }
}
