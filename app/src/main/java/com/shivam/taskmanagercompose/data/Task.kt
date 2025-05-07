package com.shivam.taskmanagercompose.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

enum class TaskPriority {
    LOW, MEDIUM, HIGH
}

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String,
    val priority: TaskPriority = TaskPriority.MEDIUM,
    val dueDate: LocalDateTime? = null,
    val isCompleted: Boolean = false, // New field with default value
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val order: Int = 0 // For drag and drop reordering
)