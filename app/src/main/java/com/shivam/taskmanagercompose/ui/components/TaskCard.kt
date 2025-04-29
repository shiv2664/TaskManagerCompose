package com.shivam.taskmanagercompose.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.shivam.taskmanagercompose.data.Task
import com.shivam.taskmanagercompose.data.TaskPriority
import com.shivam.taskmanagercompose.ui.theme.PriorityHigh
import com.shivam.taskmanagercompose.ui.theme.PriorityLow
import com.shivam.taskmanagercompose.ui.theme.PriorityMedium
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskCard(
    task: Task,
    onTaskClick: (Long) -> Unit,
    onTaskCheckedChange: (Task) -> Unit,
    onTaskDelete: (Task) -> Unit,
    modifier: Modifier = Modifier
) {
    val haptic = LocalHapticFeedback.current
    val alpha by animateFloatAsState(targetValue = if (task.isCompleted) 0.6f else 1f)
    val backgroundColor by animateColorAsState(
        targetValue = when (task.priority) {
            TaskPriority.HIGH -> PriorityHigh.copy(alpha = 0.1f)
            TaskPriority.MEDIUM -> PriorityMedium.copy(alpha = 0.1f)
            TaskPriority.LOW -> PriorityLow.copy(alpha = 0.1f)
        }
    )

    Card(
        onClick = { onTaskClick(task.id) },
        modifier = modifier
            .fillMaxWidth()
            .alpha(alpha),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = task.isCompleted,
                    onCheckedChange = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        onTaskCheckedChange(task)
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = when (task.priority) {
                            TaskPriority.HIGH -> PriorityHigh
                            TaskPriority.MEDIUM -> PriorityMedium
                            TaskPriority.LOW -> PriorityLow
                        }
                    )
                )
                
                Spacer(modifier = Modifier.width(16.dp))
                
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = task.title,
                        style = MaterialTheme.typography.titleMedium,
                        textDecoration = if (task.isCompleted) TextDecoration.LineThrough else null,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    
                    if (task.description.isNotBlank()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = task.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            textDecoration = if (task.isCompleted) TextDecoration.LineThrough else null
                        )
                    }
                    
                    task.dueDate?.let { dueDate ->
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Due: ${dueDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))}",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            IconButton(onClick = { onTaskDelete(task) }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete task",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
} 