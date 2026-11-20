package com.example.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.data.repository.ExamPilotRepository
import com.example.ui.screens.AuthMode
import com.example.ui.screens.AuthScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.MockSetupScreen
import com.example.ui.screens.ProgressScreen
import com.example.ui.screens.QuizAndMockScreen
import com.example.ui.screens.SubjectSelectionSetupScreen
import com.example.ui.screens.SubjectTopicsScreen
import com.example.ui.screens.SubjectsScreen
import com.example.ui.screens.TopicStudyScreen
import com.example.ui.screens.WelcomeScreen
import com.example.ui.theme.ExamPilotTheme
import com.example.ui.viewmodel.MainViewModel
import com.example.ui.viewmodel.QuizViewModel
import com.example.ui.viewmodel.StudyViewModel

object ExamPilotRoutes {
    const val HOME = "home"
    const val SUBJECTS = "subjects"
    const val MOCK = "mock"
    const val PROGRESS = "progress"
    const val SUBJECT_TOPICS = "subject/{subjectId}"
    const val TOPIC_STUDY = "topic/{topicId}"
    const val QUIZ_SESSION = "quiz_session"

    fun subjectTopics(subjectId: String) = "subject/$subjectId"
    fun topicStudy(topicId: String) = "topic/$topicId"
}

private enum class TopLevelTab(
    val route: String,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
) {
    HOME(ExamPilotRoutes.HOME, "Home", Icons.Filled.Home, Icons.Outlined.Home),
    SUBJECTS(ExamPilotRoutes.SUBJECTS, "Subjects", Icons.Filled.MenuBook, Icons.Outlined.MenuBook),
    MOCK(ExamPilotRoutes.MOCK, "Mock CBT", Icons.Filled.Timer, Icons.Outlined.Timer),
    PROGRESS(ExamPilotRoutes.PROGRESS, "Progress", Icons.Filled.BarChart, Icons.Outlined.BarChart),
}

private enum class PreHomeStage {
    WELCOME,
    AUTH,
}

@Composable
fun ExamPilotApp(repository: ExamPilotRepository) {
    val mainViewModel: MainViewModel = viewModel(factory = MainViewModel.Factory(repository))
    val studyViewModel: StudyViewModel = viewModel(factory = StudyViewModel.Factory(repository))
    val quizViewModel: QuizViewModel = viewModel(factory = QuizViewModel.Factory(repository))

    val mainState by mainViewModel.uiState.collectAsStateWithLifecycle()
    val useDarkTheme = mainState.preferences.darkModeOverride ?: isSystemInDarkTheme()

    ExamPilotTheme(darkTheme = useDarkTheme) {
        if (mainState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
            return@ExamPilotTheme
        }

        val prefs = mainState.preferences
        var preHomeStage by rememberSaveable { mutableStateOf(PreHomeStage.WELCOME) }
        var authMode by rememberSaveable { mutableStateOf(AuthMode.SIGN_UP) }

        if (!prefs.isAuthenticated) {
            when (preHomeStage) {
                PreHomeStage.WELCOME -> {
                    WelcomeScreen(
                        onNavigateToSignUp = {
                            authMode = AuthMode.SIGN_UP
                            mainViewModel.markWelcomeSeen()
                            mainViewModel.clearAuthError()
                            preHomeStage = PreHomeStage.AUTH
                        },
                        onNavigateToLogin = {
                            authMode = AuthMode.LOGIN
                            mainViewModel.markWelcomeSeen()
                            mainViewModel.clearAuthError()
                            preHomeStage = PreHomeStage.AUTH
                        },
                        onContinueAsGuest = {
                            mainViewModel.continueAsGuest()
                        },
                    )
                }

                PreHomeStage.AUTH -> {
                    AuthScreen(
                        initialMode = authMode,
                        authError = mainState.authError,
                        isBusy = mainState.isAuthBusy,
                        onBackToWelcome = {
                            mainViewModel.clearAuthError()
                            preHomeStage = PreHomeStage.WELCOME
                        },
                        onLogin = mainViewModel::login,
                        onSignUp = mainViewModel::signUp,
                        onContinueAsGuest = mainViewModel::continueAsGuest,
                        onClearError = mainViewModel::clearAuthError,
                    )
                }
            }
            return@ExamPilotTheme
        }

        if (!prefs.hasCompletedSubjectSetup) {
            SubjectSelectionSetupScreen(
                studentName = prefs.studentName,
                initialExam = mainState.activeExam,
                subjectsByExam = mainState.subjectsByExam,
                recommendedIdsProvider = { exam -> repository.defaultSubjectIdsFor(exam).toSet() },
                onSaveAndContinue = mainViewModel::saveSubjectSelectionSetup,
            )
            return@ExamPilotTheme
        }

        val navController = rememberNavController()
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route
        val topLevelRoutes = remember { TopLevelTab.entries.map { it.route }.toSet() }
        val showTopLevelNav = currentRoute in topLevelRoutes

        val snackbarHostState = remember { SnackbarHostState() }
        LaunchedEffect(mainState.feedbackMessage) {
            val message = mainState.feedbackMessage
            if (message != null) {
                snackbarHostState.showSnackbar(message)
                mainViewModel.clearFeedback()
            }
        }

        val navigateToTab: (String) -> Unit = { route ->
            navController.navigate(route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }

        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val useNavigationRail = maxWidth >= 600.dp

            Scaffold(
                snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                contentWindowInsets = if (showTopLevelNav) WindowInsets.safeDrawing else WindowInsets(0.dp),
                bottomBar = {
                    if (showTopLevelNav && !useNavigationRail) {
                        NavigationBar {
                            TopLevelTab.entries.forEach { tab ->
                                val selected = currentRoute == tab.route
                                NavigationBarItem(
                                    selected = selected,
                                    onClick = { navigateToTab(tab.route) },
                                    icon = {
                                        Icon(
                                            imageVector = if (selected) tab.selectedIcon else tab.unselectedIcon,
                                            contentDescription = tab.label,
                                        )
                                    },
                                    label = { Text(tab.label) },
                                    modifier = Modifier.testTag("nav_tab_${tab.route}"),
                                )
                            }
                        }
                    }
                },
            ) { innerPadding ->
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(if (showTopLevelNav) innerPadding else PaddingValues(0.dp)),
                ) {
                    if (showTopLevelNav && useNavigationRail) {
                        NavigationRail(
                            modifier = Modifier
                                .fillMaxHeight()
                                .windowInsetsPadding(WindowInsets.safeDrawing),
                        ) {
                            Spacer(modifier = Modifier.weight(1f))
                            TopLevelTab.entries.forEach { tab ->
                                val selected = currentRoute == tab.route
                                NavigationRailItem(
                                    selected = selected,
                                    onClick = { navigateToTab(tab.route) },
                                    icon = {
                                        Icon(
                                            imageVector = if (selected) tab.selectedIcon else tab.unselectedIcon,
                                            contentDescription = tab.label,
                                        )
                                    },
                                    label = { Text(tab.label) },
                                    modifier = Modifier.testTag("rail_tab_${tab.route}"),
                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }

                    NavHost(
                        navController = navController,
                        startDestination = ExamPilotRoutes.HOME,
                        modifier = Modifier.weight(1f).fillMaxHeight(),
                    ) {
                        composable(ExamPilotRoutes.HOME) {
                            HomeScreen(
                                uiState = mainState,
                                onSwitchExam = mainViewModel::switchExam,
                                onOpenSubject = { subjectId ->
                                    navController.navigate(ExamPilotRoutes.subjectTopics(subjectId))
                                },
                                onOpenTopic = { topicId ->
                                    navController.navigate(ExamPilotRoutes.topicStudy(topicId))
                                },
                                onOpenSubjectsTab = { navigateToTab(ExamPilotRoutes.SUBJECTS) },
                                onOpenMockTab = { navigateToTab(ExamPilotRoutes.MOCK) },
                            )
                        }

                        composable(ExamPilotRoutes.SUBJECTS) {
                            SubjectsScreen(
                                uiState = mainState,
                                onSwitchExam = mainViewModel::switchExam,
                                onToggleSubject = mainViewModel::toggleSubject,
                                onApplyRecommended = mainViewModel::applyRecommendedSubjects,
                                onOpenSubject = { subjectId ->
                                    navController.navigate(ExamPilotRoutes.subjectTopics(subjectId))
                                },
                            )
                        }

                        composable(ExamPilotRoutes.MOCK) {
                            MockSetupScreen(
                                uiState = mainState,
                                onStartMockExam = { examType, subjectIds, questionCount, durationMinutes ->
                                    quizViewModel.startMockExam(
                                        examType = examType,
                                        subjectIds = subjectIds,
                                        questionLimit = questionCount,
                                        durationMinutes = durationMinutes,
                                    )
                                    navController.navigate(ExamPilotRoutes.QUIZ_SESSION)
                                },
                            )
                        }

                        composable(ExamPilotRoutes.PROGRESS) {
                            ProgressScreen(
                                uiState = mainState,
                                onOpenAttemptReview = { attemptId ->
                                    quizViewModel.loadAttemptReview(attemptId)
                                    navController.navigate(ExamPilotRoutes.QUIZ_SESSION)
                                },
                                onUpdateStudentName = mainViewModel::updateStudentName,
                                onSetDarkModeOverride = mainViewModel::setDarkModeOverride,
                                onOpenSubjectSetup = mainViewModel::reopenSubjectSetup,
                                onLogout = {
                                    preHomeStage = PreHomeStage.WELCOME
                                    mainViewModel.logout()
                                },
                                onResetProgress = mainViewModel::resetProgress,
                            )
                        }

                        composable(
                            route = ExamPilotRoutes.SUBJECT_TOPICS,
                            arguments = listOf(navArgument("subjectId") { type = NavType.StringType }),
                        ) { entry ->
                            val subjectId = entry.arguments?.getString("subjectId") ?: return@composable
                            SubjectTopicsScreen(
                                subjectId = subjectId,
                                activeExam = mainState.activeExam,
                                studyViewModel = studyViewModel,
                                onBack = { navController.popBackStack() },
                                onOpenTopic = { topicId ->
                                    navController.navigate(ExamPilotRoutes.topicStudy(topicId))
                                },
                                onStartSubjectMock = { targetSubjectId ->
                                    quizViewModel.startMockExam(
                                        examType = mainState.activeExam,
                                        subjectIds = listOf(targetSubjectId),
                                        questionLimit = 12,
                                        durationMinutes = 10,
                                    )
                                    navController.navigate(ExamPilotRoutes.QUIZ_SESSION)
                                },
                            )
                        }

                        composable(
                            route = ExamPilotRoutes.TOPIC_STUDY,
                            arguments = listOf(navArgument("topicId") { type = NavType.StringType }),
                        ) { entry ->
                            val topicId = entry.arguments?.getString("topicId") ?: return@composable
                            TopicStudyScreen(
                                topicId = topicId,
                                activeExam = mainState.activeExam,
                                studyViewModel = studyViewModel,
                                onBack = { navController.popBackStack() },
                                onStartTopicQuiz = { targetTopicId ->
                                    quizViewModel.startTopicQuiz(
                                        topicId = targetTopicId,
                                        examType = mainState.activeExam,
                                    )
                                    navController.navigate(ExamPilotRoutes.QUIZ_SESSION)
                                },
                            )
                        }

                        composable(ExamPilotRoutes.QUIZ_SESSION) {
                            QuizAndMockScreen(
                                quizViewModel = quizViewModel,
                                onExit = { navController.popBackStack() },
                            )
                        }
                    }
                }
            }
        }
    }
}
