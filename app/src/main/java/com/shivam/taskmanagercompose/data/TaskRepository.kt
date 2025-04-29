package com.shivam.taskmanagercompose.data

import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(
    private val taskDao: TaskDao
) {
    fun getAllTasks(): Flow<List<Task>> = taskDao.getAllTasks()
    
    fun getPendingTasks(): Flow<List<Task>> = taskDao.getPendingTasks()
    
    fun getCompletedTasks(): Flow<List<Task>> = taskDao.getCompletedTasks()
    
    fun getTasksByDueDate(): Flow<List<Task>> = taskDao.getTasksByDueDate()
    
    fun getTasksByPriority(): Flow<List<Task>> = taskDao.getTasksByPriority()
    
    fun getTasksAlphabetically(): Flow<List<Task>> = taskDao.getTasksAlphabetically()
    
    suspend fun getTaskById(taskId: Long): Task? = taskDao.getTaskById(taskId)
    
    suspend fun insertTask(task: Task): Long = taskDao.insertTask(task)
    
    suspend fun updateTask(task: Task) = taskDao.updateTask(task)
    
    suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)
    
    suspend fun updateTaskOrder(taskId: Long, newOrder: Int) = taskDao.updateTaskOrder(taskId, newOrder)
    
    fun getCompletedTaskCount(): Flow<Int> = taskDao.getCompletedTaskCount()
    
    fun getTotalTaskCount(): Flow<Int> = taskDao.getTotalTaskCount()
} 