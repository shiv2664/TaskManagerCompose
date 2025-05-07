package com.shivam.taskmanagercompose.data

import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(
    private val taskDao: TaskDao
) {
     suspend fun insertTask(task: Task) {
        taskDao.insert(task)
    }

    fun getAllTasks() = taskDao.getAllTasks()

    suspend fun updateTask(task: Task) = taskDao.update(task)

    suspend fun deleteTask(task: Task) = taskDao.delete(task)

} 