package com.shivam.taskmanagercompose.ui.screens.task

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shivam.taskmanagercompose.data.Task
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.material3.Checkbox
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
fun TaskScreen(
    taskId: Long,
    onNavigateBack: () -> Unit,
    viewModel: TaskViewModel = hiltViewModel<TaskViewModel, TaskViewModelFactory> { factory ->
        factory.create(taskId)
    }
) {
    val tasks by viewModel.tasks.collectAsState()
    var newTaskTitle by remember { mutableStateOf("") }
    var newTaskDescription by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Add Task Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = newTaskTitle,
                onValueChange = { newTaskTitle = it },
                label = { Text("Task Title") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))

            OutlinedTextField(
                value = newTaskDescription,
                onValueChange = { newTaskDescription = it },
                label = { Text("Task Description") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                if (newTaskTitle.isNotBlank() && newTaskDescription.isNotBlank()) {
                    viewModel.addTask(Task(title = newTaskTitle, description = newTaskDescription))
                    newTaskTitle = ""
                    newTaskDescription = ""
                }
            }) {
                Text("Add")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Task List
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(tasks) { task ->
                TaskItem(task, viewModel)
            }
        }
    }
}

@Composable
fun TaskItem(task: Task, viewModel: TaskViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = task.isCompleted,
            onCheckedChange = { viewModel.updateTask(task.copy(isCompleted = it)) }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = task.title, style = TextStyle(fontWeight = FontWeight.Bold))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = task.description)
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { viewModel.deleteTask(task) }) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
        }
    }
} 