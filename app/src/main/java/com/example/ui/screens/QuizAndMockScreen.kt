package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.model.QuizMode
import com.example.ui.components.ExamTagPill
import com.example.ui.components.formatDuration
import com.example.ui.theme.ErrorCrimson
import com.example.ui.theme.ErrorCrimsonContainer
import com.example.ui.theme.JetBrainsMonoFontFamily
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.SuccessGreenContainer
import com.example.ui.theme.WarningAmber
import com.example.ui.viewmodel.QuizScreenStatus
import com.example.ui.viewmodel.QuizViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun QuizAndMockScreen(
    quizViewModel: QuizViewModel,
    onExit: () -> Unit,
) {
    val uiState by quizViewModel.uiState.collectAsStateWithLifecycle()
    var showLeaveConfirmDialog by remember { mutableStateOf(false) }
    var showSubmitConfirmDialog by remember { mutableStateOf(false) }

    val handleBack: () -> Unit = {
        if (uiState.status == QuizScreenStatus.Active) {
            showLeaveConfirmDialog = true
        } else {
            onExit()
        }
    }

    BackHandler(onBack = handleBack)

    if (showLeaveConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showLeaveConfirmDialog = false },
            title = { Text("Exit Assessment?") },
            text = { Text("Your current progress in this session will not be saved unless you submit first.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showLeaveConfirmDialog = false
                        onExit()
                    },
                ) {
                    Text("Leave")
                }
            },
            dismissButton = {
                TextButton(onClick = { showLeaveConfirmDialog = false }) {
                    Text("Continue")
                }
            },
        )
    }

    if (showSubmitConfirmDialog) {
        val unanswered = uiState.questions.size - uiState.selectedAnswers.size
        AlertDialog(
            onDismissRequest = { showSubmitConfirmDialog = false },
            title = { Text("Submit ${uiState.mode.label}?") },
            text = {
                Text(
                    if (unanswered > 0) {
                        "You have $unanswered unanswered question(s). Submit anyway to see your full score and explanations?"
                    } else {
                        "All ${uiState.questions.size} questions answered. Ready to grade and view your breakdown?"
                    },
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showSubmitConfirmDialog = false
                        quizViewModel.submitAssessment()
                    },
                ) {
                    Text("Submit Now")
                }
            },
            dismissButton = {
                TextButton(onClick = { showSubmitConfirmDialog = false }) {
                    Text("Review More")
                }
            },
        )
    }

    if (uiState.showPaletteSheet) {
        ModalBottomSheet(
            onDismissRequest = { quizViewModel.setPaletteSheetVisible(false) },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
            ) {
                Text(
                    text = "Question Navigator",
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Answered: ${uiState.selectedAnswers.size}/${uiState.questions.size} • Flagged: ${uiState.flaggedQuestions.size}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Spacer(modifier = Modifier.height(14.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    uiState.questions.indices.forEach { idx ->
                        val isCurrent = idx == uiState.currentIndex
                        val isAnswered = uiState.selectedAnswers.containsKey(idx)
                        val isFlagged = uiState.flaggedQuestions.contains(idx)

                        val bgColor = when {
                            isCurrent -> MaterialTheme.colorScheme.primary
                            isFlagged -> MaterialTheme.colorScheme.secondaryContainer
                            isAnswered -> MaterialTheme.colorScheme.tertiaryContainer
                            else -> MaterialTheme.colorScheme.surfaceVariant
                        }
                        val fgColor = when {
                            isCurrent -> MaterialTheme.colorScheme.onPrimary
                            isFlagged -> MaterialTheme.colorScheme.onSecondaryContainer
                            isAnswered -> MaterialTheme.colorScheme.onTertiaryContainer
                            else -> MaterialTheme.colorScheme.onSurfaceVariant
                        }

                        Surface(
                            color = bgColor,
                            contentColor = fgColor,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .clickable { quizViewModel.jumpToQuestion(idx) }
                                .testTag("palette_q_$idx"),
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "${idx + 1}",
                                    style = MaterialTheme.typography.labelLarge,
                                    fontFamily = JetBrainsMonoFontFamily,
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = uiState.title.ifBlank { "Assessment" },
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1,
                        )
                        if (uiState.status == QuizScreenStatus.Active) {
                            Text(
                                text = "Question ${uiState.currentIndex + 1} of ${uiState.questions.size}",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = handleBack,
                        modifier = Modifier.testTag("quiz_close_button"),
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Close assessment",
                        )
                    }
                },
                actions = {
                    if (uiState.status == QuizScreenStatus.Active) {
                        if (uiState.mode == QuizMode.CBT_MOCK) {
                            val timerColor = when {
                                uiState.remainingSeconds <= 30 -> ErrorCrimson
                                uiState.remainingSeconds <= 120 -> WarningAmber
                                else -> MaterialTheme.colorScheme.primary
                            }
                            Surface(
                                color = timerColor.copy(alpha = 0.14f),
                                shape = RoundedCornerShape(999.dp),
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Timer,
                                        contentDescription = "Countdown timer",
                                        tint = timerColor,
                                        modifier = Modifier.size(16.dp),
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = formatDuration(uiState.remainingSeconds),
                                        style = MaterialTheme.typography.labelLarge,
                                        fontFamily = JetBrainsMonoFontFamily,
                                        color = timerColor,
                                    )
                                }
                            }
                            IconButton(
                                onClick = { quizViewModel.setPaletteSheetVisible(true) },
                                modifier = Modifier.testTag("cbt_palette_button"),
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.GridView,
                                    contentDescription = "Open question grid",
                                )
                            }
                        } else {
                            val isFlagged = uiState.flaggedQuestions.contains(uiState.currentIndex)
                            IconButton(
                                onClick = { quizViewModel.toggleFlagCurrent() },
                                modifier = Modifier.testTag("quiz_flag_button"),
                            ) {
                                Icon(
                                    imageVector = if (isFlagged) Icons.Filled.Flag else Icons.Outlined.Flag,
                                    contentDescription = "Flag question",
                                    tint = if (isFlagged) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                ),
            )
        },
        bottomBar = {
            if (uiState.status == QuizScreenStatus.Active) {
                Surface(
                    tonalElevation = 3.dp,
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier
                            .windowInsetsPadding(WindowInsets.safeDrawing)
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 12.dp),
                    ) {
                        OutlinedButton(
                            onClick = { quizViewModel.previousQuestion() },
                            enabled = uiState.currentIndex > 0,
                            shape = MaterialTheme.shapes.small,
                            modifier = Modifier
                                .height(48.dp)
                                .testTag("quiz_prev_button"),
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Previous",
                            )
                        }

                        if (uiState.mode == QuizMode.CBT_MOCK) {
                            val isFlagged = uiState.flaggedQuestions.contains(uiState.currentIndex)
                            OutlinedButton(
                                onClick = { quizViewModel.toggleFlagCurrent() },
                                shape = MaterialTheme.shapes.small,
                                modifier = Modifier
                                    .height(48.dp)
                                    .testTag("cbt_flag_button"),
                            ) {
                                Icon(
                                    imageVector = if (isFlagged) Icons.Filled.Flag else Icons.Outlined.Flag,
                                    contentDescription = "Flag",
                                    tint = if (isFlagged) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                        }

                        val isLast = uiState.currentIndex == uiState.questions.lastIndex
                        Button(
                            onClick = {
                                if (isLast) {
                                    if (uiState.mode == QuizMode.CBT_MOCK) {
                                        showSubmitConfirmDialog = true
                                    } else {
                                        quizViewModel.submitAssessment()
                                    }
                                } else {
                                    quizViewModel.nextQuestion()
                                }
                            },
                            shape = MaterialTheme.shapes.small,
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .testTag(if (isLast) "quiz_submit_button" else "quiz_next_button"),
                        ) {
                            Text(
                                text = if (isLast) "Finish & Score" else "Next Question",
                                fontWeight = FontWeight.Bold,
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp),
                            )
                        }
                    }
                }
            }
        },
    ) { innerPadding ->
        when (val status = uiState.status) {
            QuizScreenStatus.Idle, QuizScreenStatus.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }
            is QuizScreenStatus.Empty -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(24.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(status.message, style = MaterialTheme.typography.bodyLarge)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = onExit) { Text("Go Back") }
                    }
                }
            }
            QuizScreenStatus.Completed -> {
                AssessmentReviewContent(
                    uiState = uiState,
                    onSelectFilter = quizViewModel::setReviewFilter,
                    onDone = onExit,
                    modifier = Modifier.padding(innerPadding),
                )
            }
            QuizScreenStatus.Active -> {
                val question = uiState.currentQuestion ?: return@Scaffold
                val selectedOption = uiState.selectedAnswers[uiState.currentIndex]
                val revealed = uiState.isCurrentRevealed
                val progress = (uiState.currentIndex + 1).toFloat() / uiState.questions.size.coerceAtLeast(1)
                val options = listOf(question.optionA, question.optionB, question.optionC, question.optionD)
                val letters = listOf("A", "B", "C", "D")

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.TopCenter,
                ) {
                    Column(
                        modifier = Modifier
                            .widthIn(max = 720.dp)
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = 20.dp, vertical = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp),
                    ) {
                        LinearProgressIndicator(
                            progress = { progress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(7.dp)
                                .clip(RoundedCornerShape(999.dp)),
                            strokeCap = StrokeCap.Round,
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            ExamTagPill(
                                label = question.examTag,
                                isHighlighted = true,
                            )
                            val subjectName = uiState.subjectNamesById[question.subjectId]
                            if (subjectName != null) {
                                ExamTagPill(label = subjectName)
                            }
                        }

                        Card(
                            shape = MaterialTheme.shapes.large,
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(
                                text = question.prompt,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(18.dp),
                            )
                        }

                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            options.forEachIndexed { idx, optionText ->
                                val isPicked = selectedOption == idx
                                val isCorrect = question.correctIndex == idx

                                val containerColor = when {
                                    revealed && isCorrect -> SuccessGreenContainer
                                    revealed && isPicked && !isCorrect -> ErrorCrimsonContainer
                                    !revealed && isPicked -> MaterialTheme.colorScheme.primaryContainer
                                    else -> MaterialTheme.colorScheme.surface
                                }
                                val borderColor = when {
                                    revealed && isCorrect -> SuccessGreen
                                    revealed && isPicked && !isCorrect -> ErrorCrimson
                                    !revealed && isPicked -> MaterialTheme.colorScheme.primary
                                    else -> MaterialTheme.colorScheme.outlineVariant
                                }
                                val textColor = when {
                                    revealed && isCorrect -> Color(0xFF14532D)
                                    revealed && isPicked && !isCorrect -> Color(0xFF7F1D1D)
                                    !revealed && isPicked -> MaterialTheme.colorScheme.onPrimaryContainer
                                    else -> MaterialTheme.colorScheme.onSurface
                                }

                                Surface(
                                    color = containerColor,
                                    shape = MaterialTheme.shapes.medium,
                                    border = BorderStroke(if (isPicked || (revealed && isCorrect)) 2.dp else 1.dp, borderColor),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(MaterialTheme.shapes.medium)
                                        .clickable { quizViewModel.selectOption(idx) }
                                        .testTag("quiz_option_$idx"),
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 14.dp, vertical = 14.dp),
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(30.dp)
                                                .clip(CircleShape)
                                                .background(
                                                    if (isPicked || (revealed && isCorrect)) {
                                                        borderColor
                                                    } else {
                                                        MaterialTheme.colorScheme.surfaceVariant
                                                    },
                                                ),
                                            contentAlignment = Alignment.Center,
                                        ) {
                                            Text(
                                                text = letters[idx],
                                                style = MaterialTheme.typography.labelLarge,
                                                fontFamily = JetBrainsMonoFontFamily,
                                                color = if (isPicked || (revealed && isCorrect)) {
                                                    Color.White
                                                } else {
                                                    MaterialTheme.colorScheme.onSurfaceVariant
                                                },
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Text(
                                            text = optionText,
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = textColor,
                                            modifier = Modifier.weight(1f),
                                        )
                                        if (revealed && isCorrect) {
                                            Icon(
                                                imageVector = Icons.Filled.CheckCircle,
                                                contentDescription = "Correct option",
                                                tint = SuccessGreen,
                                                modifier = Modifier.size(20.dp),
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        // Instant-feedback explanation card (Topic Quiz mode)
                        AnimatedVisibility(
                            visible = revealed,
                            enter = fadeIn() + expandVertically(),
                        ) {
                            val gotItRight = selectedOption == question.correctIndex
                            Card(
                                shape = MaterialTheme.shapes.medium,
                                colors = CardDefaults.cardColors(
                                    containerColor = if (gotItRight) {
                                        SuccessGreenContainer.copy(alpha = 0.7f)
                                    } else {
                                        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
                                    },
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("quiz_explanation_card"),
                            ) {
                                Row(
                                    verticalAlignment = Alignment.Top,
                                    modifier = Modifier.padding(16.dp),
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Info,
                                        contentDescription = null,
                                        tint = if (gotItRight) SuccessGreen else MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(20.dp),
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(
                                            text = if (gotItRight) "Correct!" else "Explanation",
                                            style = MaterialTheme.typography.titleSmall,
                                            color = MaterialTheme.colorScheme.onSurface,
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = question.explanation,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSurface,
                                        )
                                    }
                                }
                            }
                        }

                        if (uiState.mode == QuizMode.CBT_MOCK && uiState.currentIndex == uiState.questions.lastIndex) {
                            OutlinedButton(
                                onClick = { showSubmitConfirmDialog = true },
                                shape = MaterialTheme.shapes.medium,
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Text("Review Palette & Submit Mock")
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}
