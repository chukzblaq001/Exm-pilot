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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ui.components.formatDuration
import com.example.ui.theme.ErrorCrimson
import com.example.ui.theme.ErrorCrimsonContainer
import com.example.ui.theme.IndigoDeep
import com.example.ui.theme.JetBrainsMonoFontFamily
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.SuccessGreenContainer
import com.example.ui.theme.VioletSecondary
import com.example.ui.viewmodel.QuizUiState
import com.example.ui.viewmodel.ReviewFilter

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AssessmentReviewContent(
    uiState: QuizUiState,
    onSelectFilter: (ReviewFilter) -> Unit,
    onDone: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val total = uiState.reviewItems.size.coerceAtLeast(1)
    val score = uiState.score
    val percent = ((score * 100f) / total).toInt()

    val subjectBreakdown = remember(uiState.reviewItems) {
        uiState.reviewItems.groupBy { it.subjectName }.mapValues { (_, items) ->
            items.count { it.isCorrect } to items.size
        }
    }

    val filteredItems = remember(uiState.reviewItems, uiState.reviewFilter) {
        when (uiState.reviewFilter) {
            ReviewFilter.ALL -> uiState.reviewItems.mapIndexed { idx, item -> idx to item }
            ReviewFilter.WRONG -> uiState.reviewItems.mapIndexedNotNull { idx, item ->
                if (!item.isCorrect) idx to item else null
            }
            ReviewFilter.FLAGGED -> uiState.reviewItems.mapIndexedNotNull { idx, item ->
                if (item.isFlagged) idx to item else null
            }
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
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            // Hero Result Card
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
                            Text(
                                text = uiState.mode.label.uppercase(),
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White.copy(alpha = 0.8f),
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                verticalAlignment = Alignment.Bottom,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = when {
                                            percent >= 75 -> "Excellent Mastery!"
                                            percent >= 50 -> "Solid Progress!"
                                            else -> "Keep Practicing!"
                                        },
                                        style = MaterialTheme.typography.headlineMedium,
                                        color = Color.White,
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = uiState.subjectSummary,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.White.copy(alpha = 0.86f),
                                    )
                                }
                                Text(
                                    text = "$percent%",
                                    style = MaterialTheme.typography.displaySmall,
                                    fontFamily = JetBrainsMonoFontFamily,
                                    color = Color(0xFF5EEAD4),
                                )
                            }
                            Spacer(modifier = Modifier.height(14.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                ResultStatChip("Score", "$score / ${uiState.reviewItems.size}")
                                ResultStatChip("Time", formatDuration(uiState.elapsedSeconds))
                                ResultStatChip("Flagged", "${uiState.reviewItems.count { it.isFlagged }}")
                            }
                        }
                    }
                }
            }

            // Per-subject breakdown for multi-subject mocks
            if (subjectBreakdown.size > 1) {
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "Subject Breakdown",
                            style = MaterialTheme.typography.titleMedium,
                        )
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            subjectBreakdown.forEach { (subjectName, pair) ->
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = MaterialTheme.colorScheme.surface,
                                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                                ) {
                                    Text(
                                        text = "$subjectName: ${pair.first}/${pair.second}",
                                        style = MaterialTheme.typography.labelLarge,
                                        fontFamily = JetBrainsMonoFontFamily,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Filter Pills
            item {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    ReviewFilter.entries.forEach { filter ->
                        FilterChip(
                            selected = uiState.reviewFilter == filter,
                            onClick = { onSelectFilter(filter) },
                            label = { Text(filter.label) },
                            modifier = Modifier.testTag("review_filter_${filter.name.lowercase()}"),
                        )
                    }
                }
            }

            if (filteredItems.isEmpty()) {
                item {
                    Surface(
                        shape = MaterialTheme.shapes.medium,
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(24.dp),
                        ) {
                            Icon(
                                imageVector = Icons.Filled.DoneAll,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(32.dp),
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "No questions match this filter.",
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                    }
                }
            } else {
                itemsIndexed(filteredItems, key = { _, pair -> "${pair.first}_${pair.second.questionId}" }) { _, (origIndex, item) ->
                    val letters = listOf("A", "B", "C", "D")
                    Card(
                        shape = MaterialTheme.shapes.medium,
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(
                            1.dp,
                            if (item.isCorrect) SuccessGreen.copy(alpha = 0.45f) else ErrorCrimson.copy(alpha = 0.45f),
                        ),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = if (item.isCorrect) Icons.Filled.CheckCircle else Icons.Filled.Cancel,
                                        contentDescription = if (item.isCorrect) "Correct" else "Incorrect",
                                        tint = if (item.isCorrect) SuccessGreen else ErrorCrimson,
                                        modifier = Modifier.size(20.dp),
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Q${origIndex + 1} • ${item.subjectName}",
                                        style = MaterialTheme.typography.labelLarge,
                                    )
                                }
                                if (item.isFlagged) {
                                    Icon(
                                        imageVector = Icons.Filled.Flag,
                                        contentDescription = "Flagged",
                                        tint = MaterialTheme.colorScheme.secondary,
                                        modifier = Modifier.size(16.dp),
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = item.prompt,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Medium,
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            item.options.forEachIndexed { optIdx, optText ->
                                val isRight = optIdx == item.correctIndex
                                val isUserPick = optIdx == item.selectedIndex
                                val bg = when {
                                    isRight -> SuccessGreenContainer
                                    isUserPick && !isRight -> ErrorCrimsonContainer
                                    else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)
                                }
                                val textColor = when {
                                    isRight -> Color(0xFF14532D)
                                    isUserPick && !isRight -> Color(0xFF7F1D1D)
                                    else -> MaterialTheme.colorScheme.onSurface
                                }
                                Surface(
                                    color = bg,
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 3.dp),
                                ) {
                                    Text(
                                        text = "${letters.getOrElse(optIdx) { "" }}. $optText",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = textColor,
                                        fontWeight = if (isRight || isUserPick) FontWeight.SemiBold else FontWeight.Normal,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 9.dp),
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Surface(
                                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Text(
                                    text = "Explanation: ${item.explanation}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.padding(12.dp),
                                )
                            }
                        }
                    }
                }
            }

            item {
                Button(
                    onClick = onDone,
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("review_done_button"),
                ) {
                    Text("Back to Study Hub", fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun ResultStatChip(label: String, value: String) {
    Surface(
        color = Color.White.copy(alpha = 0.16f),
        shape = RoundedCornerShape(10.dp),
    ) {
        Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.78f),
            )
            Text(
                text = value,
                style = MaterialTheme.typography.labelLarge,
                fontFamily = JetBrainsMonoFontFamily,
                color = Color.White,
            )
        }
    }
}
