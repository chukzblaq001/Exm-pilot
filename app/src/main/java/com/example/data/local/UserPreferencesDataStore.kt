package com.example.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.model.ExamType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.userPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "exam_pilot_prefs",
)

data class UserPreferences(
    val studentName: String = "Scholar",
    val activeExam: ExamType = ExamType.JAMB,
    val hasCompletedOnboarding: Boolean = false,
    val darkModeOverride: Boolean? = null,
)

class UserPreferencesDataStore(private val context: Context) {
    private object Keys {
        val STUDENT_NAME = stringPreferencesKey("student_name")
        val ACTIVE_EXAM = stringPreferencesKey("active_exam")
        val ONBOARDING_DONE = booleanPreferencesKey("onboarding_done")
        val DARK_MODE_OVERRIDE = stringPreferencesKey("dark_mode_override")
    }

    val preferences: Flow<UserPreferences> = context.userPreferencesDataStore.data.map { prefs ->
        UserPreferences(
            studentName = prefs[Keys.STUDENT_NAME]?.takeIf { it.isNotBlank() } ?: "Scholar",
            activeExam = ExamType.fromName(prefs[Keys.ACTIVE_EXAM]),
            hasCompletedOnboarding = prefs[Keys.ONBOARDING_DONE] ?: false,
            darkModeOverride = when (prefs[Keys.DARK_MODE_OVERRIDE]) {
                "dark" -> true
                "light" -> false
                else -> null
            },
        )
    }

    suspend fun completeOnboarding(studentName: String, examType: ExamType) {
        context.userPreferencesDataStore.edit { prefs ->
            prefs[Keys.STUDENT_NAME] = studentName.trim().ifBlank { "Scholar" }
            prefs[Keys.ACTIVE_EXAM] = examType.name
            prefs[Keys.ONBOARDING_DONE] = true
        }
    }

    suspend fun setActiveExam(examType: ExamType) {
        context.userPreferencesDataStore.edit { prefs ->
            prefs[Keys.ACTIVE_EXAM] = examType.name
        }
    }

    suspend fun updateStudentName(studentName: String) {
        context.userPreferencesDataStore.edit { prefs ->
            prefs[Keys.STUDENT_NAME] = studentName.trim().ifBlank { "Scholar" }
        }
    }

    suspend fun setDarkModeOverride(darkMode: Boolean?) {
        context.userPreferencesDataStore.edit { prefs ->
            when (darkMode) {
                true -> prefs[Keys.DARK_MODE_OVERRIDE] = "dark"
                false -> prefs[Keys.DARK_MODE_OVERRIDE] = "light"
                null -> prefs.remove(Keys.DARK_MODE_OVERRIDE)
            }
        }
    }
}
