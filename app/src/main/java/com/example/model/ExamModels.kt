package com.example.model

enum class ExamType(
    val shortName: String,
    val fullName: String,
    val description: String,
    val recommendedSubjectsLabel: String,
    val mandatorySubjectId: String?,
    val minSubjects: Int,
    val maxSubjects: Int,
    val defaultMockMinutes: Int,
) {
    WAEC(
        shortName = "WAEC",
        fullName = "WAEC WASSCE",
        description = "West African Senior School Certificate Examination",
        recommendedSubjectsLabel = "Select 8–9 subjects (English & Maths recommended)",
        mandatorySubjectId = null,
        minSubjects = 8,
        maxSubjects = 9,
        defaultMockMinutes = 25,
    ),
    JAMB(
        shortName = "JAMB UTME",
        fullName = "JAMB UTME CBT",
        description = "Unified Tertiary Matriculation Examination",
        recommendedSubjectsLabel = "Use of English (mandatory) + 3 other subjects",
        mandatorySubjectId = "eng",
        minSubjects = 4,
        maxSubjects = 4,
        defaultMockMinutes = 20,
    ),
    NECO(
        shortName = "NECO",
        fullName = "NECO SSCE",
        description = "National Examinations Council Senior School Certificate",
        recommendedSubjectsLabel = "Select 8–9 subjects aligned to your track",
        mandatorySubjectId = null,
        minSubjects = 8,
        maxSubjects = 9,
        defaultMockMinutes = 25,
    );

    fun isSelectionCountValid(count: Int): Boolean = count in minSubjects..maxSubjects

    companion object {
        fun fromName(value: String?): ExamType =
            entries.firstOrNull { it.name.equals(value, ignoreCase = true) } ?: JAMB
    }
}

enum class SubjectCategory(val label: String) {
    CORE("Core Compulsory"),
    SCIENCE("Sciences"),
    ARTS_COMMERCIAL("Arts & Commercial"),
}

enum class QuizMode(val label: String) {
    TOPIC_QUIZ("Topic Quiz"),
    CBT_MOCK("CBT Mock"),
}

data class LessonSection(
    val heading: String,
    val body: String,
    val keyPoints: List<String> = emptyList(),
)

data class ReviewAnswerRecord(
    val questionId: String,
    val subjectId: String,
    val subjectName: String,
    val prompt: String,
    val options: List<String>,
    val selectedIndex: Int?,
    val correctIndex: Int,
    val explanation: String,
    val isFlagged: Boolean = false,
) {
    val isCorrect: Boolean get() = selectedIndex == correctIndex
}

data class SubjectProgressSummary(
    val subjectId: String,
    val name: String,
    val code: String,
    val category: SubjectCategory,
    val tagline: String,
    val totalTopics: Int,
    val masteredTopics: Int,
    val totalQuestions: Int,
    val averageQuizPercent: Int?,
    val isSelected: Boolean,
) {
    val masteryFraction: Float
        get() = if (totalTopics == 0) 0f else masteredTopics.toFloat() / totalTopics.toFloat()

    val masteryPercent: Int
        get() = (masteryFraction * 100).toInt()
}
