package com.example

import android.app.Application
import com.example.data.local.ExamPilotDatabase
import com.example.data.local.UserPreferencesDataStore
import com.example.data.repository.ExamPilotRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class ExamPilotApplication : Application() {
    private val appScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    val database: ExamPilotDatabase by lazy { ExamPilotDatabase.getInstance(this) }
    val preferencesDataStore: UserPreferencesDataStore by lazy { UserPreferencesDataStore(this) }
    val repository: ExamPilotRepository by lazy {
        ExamPilotRepository(
            dao = database.examPilotDao(),
            prefsStore = preferencesDataStore,
        )
    }

    override fun onCreate() {
        super.onCreate()
        appScope.launch {
            repository.ensureSeeded()
        }
    }
}
