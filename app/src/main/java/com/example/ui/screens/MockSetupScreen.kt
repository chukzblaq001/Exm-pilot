package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.model.ExamType
import com.example.ui.components.ExamBoardBadge
import com.example.ui.components.subjectIconFor
import com.example.ui.theme.IndigoDeep
import com.example.ui.theme.JetBrainsMonoFontFamily
import com.example.ui.theme.VioletSecondary
import com.example.ui.viewmodel.MainUiState

private data class MockPreset(
    val title: String,
    val subtitle: String,
    val questionCount: Int,
    val durationMinutes: Int,
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MockSetupScreen(
    uiState: MainUiState,
    onStartMockExam: (examType: ExamType, subjectIds: List<String>, questionCount: Int, durationMinutes: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val activeExam = uiState.activeExam
    val selectedSubjects = uiState.selectedSubjects

    // null means full selected subjects mix
    var focusSubjectId by remember(activeExam, selectedSubjects) { mutableStateOf<String?>(null) }

    val presets = remember {
        listOf(
            MockPreset("Quick Sprint", "10 questions • 8 min", 10, 8),
            MockPreset("Standard CBT", "20 questions • 18 min", 20, 18),
            MockPreset("Full Marathon", "40 questions • 30 min", 40, 30),
        )
    }
    var selectedPresetIndex by remember { mutableIntStateOf(1) }

    val targetSubjectIds = remember(focusSubjectId, selectedSubjects) {
        if (focusSubjectId != null) {
            listOf(focusSubjectId!!)
        } else {
            selectedSubjects.map { it.subjectId }
        }
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter,
    ) {
        LazyColumn(
            modifier = Modifier
                .widthIn(max = 740.dp)
                .fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                Card(
                    shape = MaterialTheme.shapes.large,
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = IndigoDeep),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(IndigoDeep, VioletSecondary),
                                ),
                            )
                            .padding(20.dp),
                    ) {
                        Column {
                            ExamBoardBadge(
                                examType = activeExam,
                                containerColor = Color.White.copy(alpha = 0.18f),
                                contentColor = Color.White,
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Timed CBT Mock Simulator",
                                style = MaterialTheme.typography.headlineMedium,
                                color = Color.White,
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Build speed and exam-hall composure with a live countdown clock, question navigator palette, and full answer review.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White.copy(alpha = 0.88f),
                            )
                        }
                    }
                }
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "1. Choose Subject Scope",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        FilterChip(
                            selected = focusSubjectId == null,
                            onClick = { focusSubjectId = null },
                            label = { Text("All ${selectedSubjects.size} Selected Subjects") },
                            modifier = Modifier.testTag("mock_scope_all"),
                        )
                        selectedSubjects.forEach { subject ->
                            FilterChip(
                                selected = focusSubjectId == subject.subjectId,
                                onClick = { focusSubjectId = subject.subjectId },
                                label = { Text(subject.name) },
                                leadingIcon = {
                                    Icon(
                                        imageVector = subjectIconFor(subject.subjectId),
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp),
                                    )
                                },
                                modifier = Modifier.testTag("mock_scope_${subject.subjectId}"),
                            )
                        }
                    }
                }
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "2. Select Duration & Length",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    presets.forEachIndexed { idx, preset ->
                        val isChosen = selectedPresetIndex == idx
                        Card(
                            onClick = { selectedPresetIndex = idx },
                            shape = MaterialTheme.shapes.medium,
                            colors = CardDefaults.cardColors(
                                containerColor = if (isChosen) {
                                    MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
                                } else {
                                    MaterialTheme.colorScheme.surface
                                },
                            ),
                            border = BorderStroke(
                                width = if (isChosen) 2.dp else 1.dp,
                                color = if (isChosen) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.outlineVariant
                                },
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("mock_preset_$idx"),
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                            ) {
                                Column {
                                    Text(
                                        text = preset.title,
                                        style = MaterialTheme.typography.titleSmall,
                                    )
                                    Text(
                                        text = preset.subtitle,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    )
                                }
                                Surface(
                                    color = MaterialTheme.colorScheme.secondaryContainer,
                                    shape = RoundedCornerShape(8.dp),
                                ) {
                                    Text(
                                        text = "${preset.durationMinutes}m",
                                        style = MaterialTheme.typography.labelLarge,
                                        fontFamily = JetBrainsMonoFontFamily,
                                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item {
                Surface(
                    shape = MaterialTheme.shapes.medium,
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.65f),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Text(
                            text = "CBT Exam Features",
                            style = MaterialTheme.typography.titleSmall,
                        )
                        MockFeatureRow(Icons.Filled.Timer, "Auto-submits when the countdown clock reaches 00:00.")
                        MockFeatureRow(Icons.Filled.GridView, "Jump to any question via the color-coded question grid.")
                        MockFeatureRow(Icons.Filled.Flag, "Flag tricky questions for review before final submission.")
                    }
                }
            }

            item {
                val chosenPreset = presets[selectedPresetIndex]
                Button(
                    onClick = {
                        onStartMockExam(
                            activeExam,
                            targetSubjectIds,
                            chosenPreset.questionCount,
                            chosenPreset.durationMinutes,
                        )
                    },
                    enabled = targetSubjectIds.isNotEmpty(),
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .testTag("mock_begin_button"),
                ) {
                    Icon(
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = null,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Begin ${activeExam.shortName} CBT Mock",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(12.dp)) }
        }
    }
}

@Composable
private fun MockFeatureRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(16.dp),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
