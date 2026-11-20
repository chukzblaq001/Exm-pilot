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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoFixHigh
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.model.ExamType
import com.example.model.SubjectCategory
import com.example.model.SubjectProgressSummary
import com.example.ui.components.ExamSwitchChip
import com.example.ui.components.subjectIconFor
import com.example.ui.theme.JetBrainsMonoFontFamily

enum class SubjectFilterOption(val label: String) {
    ALL("All"),
    SELECTED("Selected"),
    CORE("Core"),
    SCIENCE("Sciences"),
    ARTS_COMMERCIAL("Arts & Commercial"),
}

@Composable
fun SubjectSelectionSetupScreen(
    studentName: String,
    initialExam: ExamType,
    subjectsByExam: Map<ExamType, List<SubjectProgressSummary>>,
    recommendedIdsProvider: (ExamType) -> Set<String>,
    onSaveAndContinue: (examType: ExamType, selectedIds: Set<String>) -> Unit,
) {
    var selectedExam by remember(initialExam) { mutableStateOf(initialExam) }

    var draftSelections by remember(subjectsByExam) {
        mutableStateOf(
            ExamType.entries.associateWith { exam ->
                val saved = subjectsByExam[exam].orEmpty()
                    .filter { it.isSelected }
                    .map { it.subjectId }
                    .toSet()
                val base = if (saved.isNotEmpty()) saved else recommendedIdsProvider(exam)
                if (exam.mandatorySubjectId != null) base + exam.mandatorySubjectId else base
            },
        )
    }

    var searchQuery by remember { mutableStateOf("") }
    var activeFilter by remember { mutableStateOf(SubjectFilterOption.ALL) }
    var inlineNotice by remember { mutableStateOf<String?>(null) }

    val availableSubjects = subjectsByExam[selectedExam].orEmpty()
    val currentSelectedIds = draftSelections[selectedExam].orEmpty()
    val isCountValid = selectedExam.isSelectionCountValid(currentSelectedIds.size)

    val filteredSubjects = remember(availableSubjects, currentSelectedIds, searchQuery, activeFilter) {
        availableSubjects.filter { subject ->
            val matchesQuery = searchQuery.isBlank() ||
                subject.name.contains(searchQuery, ignoreCase = true) ||
                subject.code.contains(searchQuery, ignoreCase = true)
            val matchesFilter = when (activeFilter) {
                SubjectFilterOption.ALL -> true
                SubjectFilterOption.SELECTED -> currentSelectedIds.contains(subject.subjectId)
                SubjectFilterOption.CORE -> subject.category == SubjectCategory.CORE
                SubjectFilterOption.SCIENCE -> subject.category == SubjectCategory.SCIENCE
                SubjectFilterOption.ARTS_COMMERCIAL -> subject.category == SubjectCategory.ARTS_COMMERCIAL
            }
            matchesQuery && matchesFilter
        }
    }

    fun toggleDraftSubject(subjectId: String) {
        inlineNotice = null
        if (selectedExam.mandatorySubjectId == subjectId) {
            inlineNotice = "Use of English is compulsory for JAMB UTME."
            return
        }
        val current = currentSelectedIds
        if (current.contains(subjectId)) {
            if (current.size <= 1) {
                inlineNotice = "Keep at least 1 subject selected."
                return
            }
            draftSelections = draftSelections + (selectedExam to (current - subjectId))
        } else {
            if (current.size >= selectedExam.maxSubjects) {
                inlineNotice = if (selectedExam == ExamType.JAMB) {
                    "JAMB UTME requires Use of English + 3 subjects (4 total). Deselect one first."
                } else {
                    "${selectedExam.shortName} allows up to ${selectedExam.maxSubjects} subjects. Deselect one first."
                }
                return
            }
            draftSelections = draftSelections + (selectedExam to (current + subjectId))
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
        bottomBar = {
            Surface(
                tonalElevation = 3.dp,
                shadowElevation = 6.dp,
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Box(
                    modifier = Modifier
                        .windowInsetsPadding(WindowInsets.safeDrawing)
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(
                        modifier = Modifier.widthIn(max = 720.dp).fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        if (!isCountValid) {
                            val remaining = (selectedExam.minSubjects - currentSelectedIds.size).coerceAtLeast(0)
                            Text(
                                text = when (selectedExam) {
                                    ExamType.JAMB -> "Select $remaining more elective(s) with Use of English to reach 4 subjects."
                                    else -> "Select $remaining more subject(s) to meet ${selectedExam.shortName}'s ${selectedExam.minSubjects}–${selectedExam.maxSubjects} requirement."
                                },
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.error,
                                fontWeight = FontWeight.Medium,
                            )
                        }
                        Button(
                            onClick = {
                                onSaveAndContinue(selectedExam, currentSelectedIds)
                            },
                            enabled = isCountValid,
                            shape = MaterialTheme.shapes.medium,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                                .testTag("subject_setup_save_button"),
                        ) {
                            Text(
                                text = "Save Preferences & Go to Home",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                            )
                        }
                    }
                }
            }
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding),
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
                        Text(
                            text = "Select Your Exam & Subjects",
                            style = MaterialTheme.typography.headlineMedium,
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Hi $studentName — choose your target examination so all study topics and mock exams match your syllabus.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }

                // Exam Board Selector
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        ExamType.entries.forEach { exam ->
                            ExamSwitchChip(
                                examType = exam,
                                isSelected = selectedExam == exam,
                                onClick = {
                                    selectedExam = exam
                                    inlineNotice = null
                                },
                            )
                        }
                    }
                }

                // Exam Rule & Progress Status Card
                item {
                    val progressFraction = (currentSelectedIds.size.toFloat() / selectedExam.minSubjects.toFloat())
                        .coerceIn(0f, 1f)
                    Card(
                        shape = MaterialTheme.shapes.medium,
                        colors = CardDefaults.cardColors(
                            containerColor = if (isCountValid) {
                                MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.6f)
                            } else {
                                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
                            },
                        ),
                        border = BorderStroke(
                            1.dp,
                            if (isCountValid) {
                                MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f)
                            } else {
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.35f)
                            },
                        ),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "${selectedExam.fullName} Requirement",
                                        style = MaterialTheme.typography.titleSmall,
                                    )
                                    Text(
                                        text = selectedExam.recommendedSubjectsLabel,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Surface(
                                    color = if (isCountValid) {
                                        MaterialTheme.colorScheme.tertiary
                                    } else {
                                        MaterialTheme.colorScheme.primary
                                    },
                                    shape = RoundedCornerShape(999.dp),
                                ) {
                                    val countLabel = if (selectedExam.minSubjects == selectedExam.maxSubjects) {
                                        "${currentSelectedIds.size} / ${selectedExam.maxSubjects}"
                                    } else {
                                        "${currentSelectedIds.size} / ${selectedExam.minSubjects}–${selectedExam.maxSubjects}"
                                    }
                                    Text(
                                        text = countLabel,
                                        style = MaterialTheme.typography.labelMedium,
                                        fontFamily = JetBrainsMonoFontFamily,
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                    )
                                }
                            }
                            LinearProgressIndicator(
                                progress = { progressFraction },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(7.dp)
                                    .clip(RoundedCornerShape(999.dp)),
                                color = if (isCountValid) {
                                    MaterialTheme.colorScheme.tertiary
                                } else {
                                    MaterialTheme.colorScheme.primary
                                },
                                strokeCap = StrokeCap.Round,
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Text(
                                    text = if (isCountValid) {
                                        "Selection satisfies ${selectedExam.shortName} rules."
                                    } else {
                                        "Pick at least ${selectedExam.minSubjects} subjects to continue."
                                    },
                                    style = MaterialTheme.typography.labelMedium,
                                    color = if (isCountValid) {
                                        MaterialTheme.colorScheme.tertiary
                                    } else {
                                        MaterialTheme.colorScheme.primary
                                    },
                                )
                                TextButton(
                                    onClick = {
                                        draftSelections = draftSelections + (selectedExam to recommendedIdsProvider(selectedExam))
                                        inlineNotice = "Recommended ${selectedExam.shortName} combination loaded."
                                    },
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.AutoFixHigh,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp),
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Auto-fill")
                                }
                            }
                        }
                    }
                }

                if (inlineNotice != null) {
                    item {
                        Surface(
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            shape = MaterialTheme.shapes.small,
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(12.dp),
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Info,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier.size(18.dp),
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = inlineNotice!!,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                                )
                            }
                        }
                    }
                }

                // Search & Filter Chips
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("Search ${selectedExam.shortName} subjects or codes...") },
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
                                .testTag("subject_setup_search_input"),
                        )

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
                                    modifier = Modifier.testTag("subject_setup_filter_${filter.name.lowercase()}"),
                                )
                            }
                        }
                    }
                }

                if (filteredSubjects.isEmpty()) {
                    item {
                        Surface(
                            shape = MaterialTheme.shapes.medium,
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f),
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(24.dp),
                            ) {
                                Text(
                                    text = "No matching subjects found",
                                    style = MaterialTheme.typography.titleSmall,
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Try clearing your search or switching category filters.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                        }
                    }
                } else {
                    items(filteredSubjects, key = { it.subjectId }) { subject ->
                        val isMandatory = selectedExam.mandatorySubjectId == subject.subjectId
                        val isSelected = currentSelectedIds.contains(subject.subjectId) || isMandatory
                        SubjectSelectableRowCard(
                            subject = subject,
                            isSelected = isSelected,
                            isMandatory = isMandatory,
                            examShortName = selectedExam.shortName,
                            onClick = { toggleDraftSubject(subject.subjectId) },
                        )
                    }
                }

                item { Spacer(modifier = Modifier.height(12.dp)) }
            }
        }
    }
}

@Composable
fun SubjectSelectableRowCard(
    subject: SubjectProgressSummary,
    isSelected: Boolean,
    isMandatory: Boolean,
    examShortName: String,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("subject_select_card_${subject.subjectId}")
            .clip(MaterialTheme.shapes.medium)
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.42f)
            } else {
                MaterialTheme.colorScheme.surface
            },
        ),
        border = BorderStroke(
            width = if (isSelected) 1.5.dp else 1.dp,
            color = if (isSelected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.outlineVariant
            },
        ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        if (isSelected) {
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
                    tint = if (isSelected) {
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
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(6.dp),
                    ) {
                        Text(
                            text = subject.code,
                            style = MaterialTheme.typography.labelSmall,
                            fontFamily = JetBrainsMonoFontFamily,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        )
                    }
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "${subject.category.label} • ${subject.totalTopics} $examShortName topics • ${subject.totalQuestions} Qs",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subject.tagline,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Surface(
                shape = RoundedCornerShape(999.dp),
                color = if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.surfaceVariant
                },
            ) {
                Icon(
                    imageVector = when {
                        isMandatory -> Icons.Filled.Lock
                        isSelected -> Icons.Filled.CheckCircle
                        else -> Icons.Filled.Check
                    },
                    contentDescription = if (isMandatory) {
                        "Mandatory"
                    } else if (isSelected) {
                        "Selected"
                    } else {
                        "Tap to select"
                    },
                    tint = if (isSelected) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.45f)
                    },
                    modifier = Modifier
                        .padding(8.dp)
                        .size(18.dp),
                )
            }
        }
    }
}
