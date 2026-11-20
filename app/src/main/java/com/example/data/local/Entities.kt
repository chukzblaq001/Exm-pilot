package com.example.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "subjects")
data class SubjectEntity(
    @PrimaryKey val id: String,
    val name: String,
    val code: String,
    val categoryName: String,
    val supportedExamsCsv: String,
    val tagline: String,
    val sortOrder: Int,
)

@Entity(
    tableName = "topics",
    foreignKeys = [
        ForeignKey(
            entity = SubjectEntity::class,
            parentColumns = ["id"],
            childColumns = ["subjectId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index("subjectId")],
)
data class TopicEntity(
    @PrimaryKey val id: String,
    val subjectId: String,
    val title: String,
    val examTagsCsv: String,
    val orderIndex: Int,
    val estimatedMinutes: Int,
    val summary: String,
    val lessonSectionsJson: String,
    val keyFormulasJson: String,
    val workedExample: String,
)

@Entity(
    tableName = "questions",
    foreignKeys = [
        ForeignKey(
            entity = SubjectEntity::class,
            parentColumns = ["id"],
            childColumns = ["subjectId"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = TopicEntity::class,
            parentColumns = ["id"],
            childColumns = ["topicId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index("subjectId"), Index("topicId")],
)
data class QuestionEntity(
    @PrimaryKey val id: String,
    val subjectId: String,
    val topicId: String,
    val examTag: String,
    val prompt: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val correctIndex: Int,
    val explanation: String,
)

@Entity(
    tableName = "user_subject_selections",
    primaryKeys = ["examType", "subjectId"],
)
data class UserSubjectSelectionEntity(
    val examType: String,
    val subjectId: String,
    val addedAt: Long = System.currentTimeMillis(),
)

@Entity(tableName = "topic_progress")
data class TopicProgressEntity(
    @PrimaryKey val topicId: String,
    val isMastered: Boolean = false,
    val lastStudiedAt: Long = 0L,
    val bestQuizPercent: Int? = null,
)

@Entity(tableName = "assessment_attempts")
data class AssessmentAttemptEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val mode: String,
    val examType: String,
    val subjectSummary: String,
    val topicId: String? = null,
    val score: Int,
    val totalQuestions: Int,
    val durationSeconds: Int,
    val completedAt: Long = System.currentTimeMillis(),
    val reviewItemsJson: String,
)

@Entity(
    tableName = "user_accounts",
    indices = [Index(value = ["email"], unique = true)],
)
data class UserAccountEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val fullName: String,
    val email: String,
    val passwordSalt: String,
    val passwordHash: String,
    val createdAt: Long = System.currentTimeMillis(),
)

@Entity(tableName = "user_preferences")
data class UserPreferenceEntity(
    @PrimaryKey val id: Int = 1,
    val loggedInUserId: Long? = null,
    val studentName: String = "Scholar",
    val studentEmail: String = "",
    val isGuest: Boolean = false,
    val activeExamName: String = "JAMB",
    val hasSeenWelcome: Boolean = false,
    val isAuthenticated: Boolean = false,
    val hasCompletedSubjectSetup: Boolean = false,
    val darkModeOverride: Boolean? = null,
    val updatedAt: Long = System.currentTimeMillis(),
)

