package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        SubjectEntity::class,
        TopicEntity::class,
        QuestionEntity::class,
        UserSubjectSelectionEntity::class,
        TopicProgressEntity::class,
        AssessmentAttemptEntity::class,
        UserAccountEntity::class,
        UserPreferenceEntity::class,
    ],
    version = 2,
    exportSchema = false,
)
abstract class ExamPilotDatabase : RoomDatabase() {
    abstract fun examPilotDao(): ExamPilotDao

    companion object {
        @Volatile
        private var INSTANCE: ExamPilotDatabase? = null

        fun getInstance(context: Context): ExamPilotDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    ExamPilotDatabase::class.java,
                    "exam_pilot.db",
                )
                    .fallbackToDestructiveMigration(dropAllTables = true)
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
