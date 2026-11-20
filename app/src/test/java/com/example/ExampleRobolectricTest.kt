package com.example

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.data.local.ExamPilotDatabase
import com.example.data.repository.AuthResult
import com.example.data.repository.ExamPilotRepository
import com.example.data.seed.CurriculumSeedProvider
import com.example.model.ExamType
import com.example.model.QuizMode
import com.example.model.ReviewAnswerRecord
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    private lateinit var context: Context
    private lateinit var db: ExamPilotDatabase
    private lateinit var repository: ExamPilotRepository

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, ExamPilotDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        repository = ExamPilotRepository(dao = db.examPilotDao())
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `app name matches Exam Pilot`() {
        assertEquals("Exam Pilot", context.getString(R.string.app_name))
    }

    @Test
    fun `seeded Nigerian curriculum covers 15 subjects and valid questions`() {
        val bundle = CurriculumSeedProvider.buildSeedBundle()
        assertEquals(15, bundle.subjects.size)
        assertTrue(bundle.topics.size >= 40)
        assertTrue(bundle.questions.size >= 90)
        bundle.questions.forEach { q ->
            assertTrue("Invalid correctIndex for ${q.id}", q.correctIndex in 0..3)
            assertTrue("Blank explanation for ${q.id}", q.explanation.isNotBlank())
        }
    }

    @Test
    fun `repository stores Room preferences authenticates locally and enforces exam subject rules`() = runBlocking {
        repository.ensureSeeded()

        // Sign up & Login persisted in Room
        val signUpResult = repository.signUp("Adaeze Okafor", "adaeze@example.com", "secret123")
        assertTrue(signUpResult is AuthResult.Success)
        val duplicateSignUp = repository.signUp("Adaeze Okafor", "adaeze@example.com", "secret123")
        assertTrue(duplicateSignUp is AuthResult.Error)

        repository.logout()
        val loggedOutPrefs = repository.preferences.first()
        assertFalse(loggedOutPrefs.isAuthenticated)

        val badLogin = repository.login("adaeze@example.com", "wrongpass")
        assertTrue(badLogin is AuthResult.Error)
        val goodLogin = repository.login("adaeze@example.com", "secret123")
        assertTrue(goodLogin is AuthResult.Success)

        val prefs = repository.preferences.first()
        assertTrue(prefs.isAuthenticated)
        assertEquals("Adaeze Okafor", prefs.studentName)

        // WAEC requires 8-9 subjects
        val invalidWaec = repository.saveSubjectSelectionSetup(
            ExamType.WAEC,
            setOf("eng", "mth", "bio"),
        )
        assertNotNull(invalidWaec)
        val validWaec = repository.saveSubjectSelectionSetup(
            ExamType.WAEC,
            setOf("eng", "mth", "civic", "bio", "chm", "phy", "eco", "agr"),
        )
        assertNull(validWaec)
        val waecSubjects = repository.observeSubjectSummaries(ExamType.WAEC).first()
        assertEquals(15, waecSubjects.size)
        assertEquals(8, waecSubjects.count { it.isSelected })

        // JAMB enforces Use of English + 3 other subjects (4 total) & excludes WAEC/NECO-only subjects
        val jambSubjects = repository.observeSubjectSummaries(ExamType.JAMB).first()
        assertEquals(13, jambSubjects.size) // excludes Civic Education & Further Mathematics
        assertEquals(4, jambSubjects.count { it.isSelected })
        val englishWarning = repository.toggleSubjectSelection(ExamType.JAMB, "eng")
        assertNotNull(englishWarning)

        // Exam-specific topic filtering
        val jambEnglishTopics = repository.observeTopicsForSubject("eng", ExamType.JAMB).first().map { it.topic.id }
        val waecEnglishTopics = repository.observeTopicsForSubject("eng", ExamType.WAEC).first().map { it.topic.id }
        assertTrue(jambEnglishTopics.contains("eng_cloze_register"))
        assertFalse(jambEnglishTopics.contains("eng_essay_summary"))
        assertTrue(waecEnglishTopics.contains("eng_essay_summary"))
        assertFalse(waecEnglishTopics.contains("eng_cloze_register"))

        // Mastery & Attempt recording
        repository.setTopicMastered("eng_concord", true)
        val updatedSubjects = repository.observeSubjectSummaries(ExamType.JAMB).first()
        val englishSummary = updatedSubjects.first { it.subjectId == "eng" }
        assertEquals(1, englishSummary.masteredTopics)

        val attemptId = repository.recordAssessmentAttempt(
            mode = QuizMode.TOPIC_QUIZ,
            examType = ExamType.JAMB,
            subjectSummary = "Use of English • Concord",
            topicId = "eng_concord",
            durationSeconds = 95,
            reviewItems = listOf(
                ReviewAnswerRecord(
                    questionId = "q_eng_1",
                    subjectId = "eng",
                    subjectName = "Use of English",
                    prompt = "Prompt",
                    options = listOf("was", "is", "were", "has been"),
                    selectedIndex = 2,
                    correctIndex = 2,
                    explanation = "Nearest subject rule.",
                ),
            ),
        )
        val saved = repository.getAttemptById(attemptId)
        assertNotNull(saved)
        assertEquals(1, saved?.score)
        assertEquals(1, saved?.totalQuestions)
    }
}
