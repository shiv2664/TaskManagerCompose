package com.shivam.taskmanagercompose.ui.screens.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shivam.taskmanagercompose.data.Task
import com.shivam.taskmanagercompose.data.TaskPriority
import com.shivam.taskmanagercompose.data.TaskRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime

data class TaskUiState(
    val id: Long = 0,
    val title: String = "",
    val description: String = "",
    val priority: TaskPriority = TaskPriority.MEDIUM,
    val dueDate: LocalDateTime? = null,
    val isLoading: Boolean = false,
    val isCompleted: Boolean = false,
    val showDatePicker: Boolean = false,
    val showDeleteConfirmation: Boolean = false,
    val navigateBack: Boolean = false,
    val error: String? = null
)

@AssistedFactory
interface TaskViewModelFactory {
    fun create(taskId: Long): TaskViewModel
}

@HiltViewModel(assistedFactory = TaskViewModelFactory::class)
class TaskViewModel @AssistedInject constructor(
    private val repository: TaskRepository,
    @Assisted private val taskId: Long
) : ViewModel() {

    private val _uiState = MutableStateFlow(TaskUiState())
    val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()

    init {
        if (taskId != -1L) {
            loadTask()
        }
    }

    private fun loadTask() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val task = repository.getTaskById(taskId)
                if (task != null) {
                    _uiState.update {
                        it.copy(
                            id = task.id,
                            title = task.title,
                            description = task.description,
                            priority = task.priority,
                            dueDate = task.dueDate,
                            isCompleted = task.isCompleted,
                            isLoading = false
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            error = "Task not found",
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        error = e.message ?: "Unknown error occurred",
                        isLoading = false
                    )
                }
            }
        }
    }

    fun updateTitle(title: String) {
        _uiState.update { it.copy(title = title) }
    }

    fun updateDescription(description: String) {
        _uiState.update { it.copy(description = description) }
    }

    fun updatePriority(priority: TaskPriority) {
        _uiState.update { it.copy(priority = priority) }
    }

    fun updateDueDate(dueDate: LocalDateTime?) {
        _uiState.update { it.copy(dueDate = dueDate) }
    }

    fun toggleDatePicker() {
        _uiState.update { it.copy(showDatePicker = !it.showDatePicker) }
    }

    fun toggleDeleteConfirmation() {
        _uiState.update { it.copy(showDeleteConfirmation = !it.showDeleteConfirmation) }
    }

    fun saveTask() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                val currentState = _uiState.value
                
                if (currentState.title.isBlank()) {
                    _uiState.update {
                        it.copy(
                            error = "Title cannot be empty",
                            isLoading = false
                        )
                    }
                    return@launch
                }

                val task = Task(
                    id = currentState.id,
                    title = currentState.title,
                    description = currentState.description,
                    priority = currentState.priority,
                    dueDate = currentState.dueDate,
                    isCompleted = currentState.isCompleted
                )

                if (task.id == 0L) {
                    repository.insertTask(task)
                } else {
                    repository.updateTask(task)
                }

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        navigateBack = true
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        error = e.message ?: "Failed to save task",
                        isLoading = false
                    )
                }
            }
        }
    }

    fun deleteTask() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                val currentState = _uiState.value
                
                if (currentState.id != 0L) {
                    val task = Task(
                        id = currentState.id,
                        title = currentState.title,
                        description = currentState.description,
                        priority = currentState.priority,
                        dueDate = currentState.dueDate,
                        isCompleted = currentState.isCompleted
                    )
                    repository.deleteTask(task)
                }

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        navigateBack = true
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        error = e.message ?: "Failed to delete task",
                        isLoading = false
                    )
                }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
} 