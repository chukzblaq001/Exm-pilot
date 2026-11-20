package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoFixHigh
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.model.ExamType
import com.example.model.SubjectCategory
import com.example.ui.components.ExamBoardBadge
import com.example.ui.components.ExamSwitchChip
import com.example.ui.components.subjectIconFor
import com.example.ui.theme.JetBrainsMonoFontFamily
import com.example.ui.viewmodel.MainUiState

@Composable
fun SubjectsScreen(
    uiState: MainUiState,
    onSwitchExam: (ExamType) -> Unit,
    onToggleSubject: (String) -> Unit,
    onApplyRecommended: (ExamType) -> Unit,
    onOpenSubject: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var activeFilter by remember { mutableStateOf(SubjectFilterOption.ALL) }
    var searchQuery by remember { mutableStateOf("") }
    val activeExam = uiState.activeExam
    val selectedCount = uiState.selectedSubjects.size
    val isCountValid = activeExam.isSelectionCountValid(selectedCount)

    val filteredSubjects = remember(uiState.allSubjects, activeFilter, searchQuery) {
        uiState.allSubjects.filter { subject ->
            val matchesQuery = searchQuery.isBlank() ||
                subject.name.contains(searchQuery, ignoreCase = true) ||
                subject.code.contains(searchQuery, ignoreCase = true)
            val matchesFilter = when (activeFilter) {
                SubjectFilterOption.ALL -> true
                SubjectFilterOption.SELECTED -> subject.isSelected
                SubjectFilterOption.CORE -> subject.category == SubjectCategory.CORE
                SubjectFilterOption.SCIENCE -> subject.category == SubjectCategory.SCIENCE
                SubjectFilterOption.ARTS_COMMERCIAL -> subject.category == SubjectCategory.ARTS_COMMERCIAL
            }
            matchesQuery && matchesFilter
        }
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter,
    ) {
        LazyColumn(
            modifier = Modifier
                .widthIn(max = 760.dp)
                .fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            text = "Subject Selection",
                            style = MaterialTheme.typography.headlineMedium,
                        )
                        ExamBoardBadge(examType = activeExam)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        ExamType.entries.forEach { exam ->
                            ExamSwitchChip(
                                examType = exam,
                                isSelected = exam == activeExam,
                                onClick = { onSwitchExam(exam) },
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Surface(
                        color = if (isCountValid) {
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.55f)
                        } else {
                            MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.65f)
                        },
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = activeExam.recommendedSubjectsLabel,
                                        style = MaterialTheme.typography.titleSmall,
                                        color = if (isCountValid) {
                                            MaterialTheme.colorScheme.onPrimaryContainer
                                        } else {
                                            MaterialTheme.colorScheme.onErrorContainer
                                        },
                                    )
                                    Text(
                                        text = "Saved locally in Room. Tap a card for ${activeExam.shortName} topics, or toggle + / ✓.",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = if (isCountValid) {
                                            MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                                        } else {
                                            MaterialTheme.colorScheme.onErrorContainer.copy(alpha = 0.85f)
                                        },
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Surface(
                                    color = if (isCountValid) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        MaterialTheme.colorScheme.error
                                    },
                                    shape = RoundedCornerShape(999.dp),
                                ) {
                                    val countBadge = if (activeExam.minSubjects == activeExam.maxSubjects) {
                                        "$selectedCount/${activeExam.maxSubjects}"
                                    } else {
                                        "$selectedCount/${activeExam.minSubjects}–${activeExam.maxSubjects}"
                                    }
                                    Text(
                                        text = countBadge,
                                        style = MaterialTheme.typography.labelMedium,
                                        fontFamily = JetBrainsMonoFontFamily,
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                    )
                                }
                            }
                            if (!isCountValid) {
                                Spacer(modifier = Modifier.height(6.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth(),
                                ) {
                                    Text(
                                        text = "Add ${(activeExam.minSubjects - selectedCount).coerceAtLeast(1)} more subject(s) for ${activeExam.shortName}.",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.error,
                                        fontWeight = FontWeight.Bold,
                                    )
                                    TextButton(onClick = { onApplyRecommended(activeExam) }) {
                                        Icon(
                                            imageVector = Icons.Filled.AutoFixHigh,
                                            contentDescription = null,
                                            modifier = Modifier.size(15.dp),
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Auto-fill")
                                    }
                                }
                            }
                        }
                    }
                }
            }

            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Filter ${activeExam.shortName} subjects by name or code...") },
                    leadingIcon = {
                        Icon(Icons.Filled.Search, contentDescription = null)
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(Icons.Filled.Close, contentDescription = "Clear search")
                            }
                        }
                    },
                    singleLine = true,
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("subjects_search_input"),
                )
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    SubjectFilterOption.entries.forEach { filter ->
                        FilterChip(
                            selected = activeFilter == filter,
                            onClick = { activeFilter = filter },
                            label = { Text(filter.label) },
                            modifier = Modifier.testTag("subjects_filter_${filter.name.lowercase()}"),
                        )
                    }
                }
            }

            items(filteredSubjects, key = { it.subjectId }) { subject ->
                val isMandatory = activeExam.mandatorySubjectId == subject.subjectId
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("subject_catalog_card_${subject.subjectId}")
                        .clip(MaterialTheme.shapes.medium)
                        .clickable { onOpenSubject(subject.subjectId) },
                    shape = MaterialTheme.shapes.medium,
                    colors = CardDefaults.cardColors(
                        containerColor = if (subject.isSelected) {
                            MaterialTheme.colorScheme.surface
                        } else {
                            MaterialTheme.colorScheme.surfaceContainerLow
                        },
                    ),
                    border = BorderStroke(
                        width = if (subject.isSelected) 1.5.dp else 1.dp,
                        color = if (subject.isSelected) {
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.55f)
                        } else {
                            MaterialTheme.colorScheme.outlineVariant
                        },
                    ),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .size(46.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(
                                    if (subject.isSelected) {
                                        MaterialTheme.colorScheme.primaryContainer
                                    } else {
                                        MaterialTheme.colorScheme.surfaceVariant
                                    },
                                ),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                imageVector = subjectIconFor(subject.subjectId),
                                contentDescription = subject.name,
                                tint = if (subject.isSelected) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.onSurfaceVariant
                                },
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = subject.name,
                                    style = MaterialTheme.typography.titleMedium,
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = subject.category.label,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.secondary,
                                    fontWeight = FontWeight.SemiBold,
                                )
                            }
                            Spacer(modifier = Modifier.height(3.dp))
                            Text(
                                text = subject.tagline,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${subject.totalTopics} ${activeExam.shortName} topics • ${subject.totalQuestions} practice Qs • ${subject.masteredTopics} mastered",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary,
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(
                            onClick = { onToggleSubject(subject.subjectId) },
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = if (subject.isSelected) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.surfaceVariant
                                },
                                contentColor = if (subject.isSelected) {
                                    MaterialTheme.colorScheme.onPrimary
                                } else {
                                    MaterialTheme.colorScheme.onSurfaceVariant
                                },
                            ),
                            modifier = Modifier
                                .minimumInteractiveComponentSize()
                                .testTag("subject_toggle_${subject.subjectId}"),
                        ) {
                            Icon(
                                imageVector = when {
                                    isMandatory -> Icons.Filled.Lock
                                    subject.isSelected -> Icons.Filled.Check
                                    else -> Icons.Filled.Add
                                },
                                contentDescription = if (subject.isSelected) {
                                    "Selected ${subject.name}"
                                } else {
                                    "Add ${subject.name}"
                                },
                            )
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(12.dp)) }
        }
    }
}
