package com.shivam.taskmanagercompose.ui.screens.settings

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SettingsUiState(
    val isDarkMode: Boolean = false,
    val useDynamicColors: Boolean = true,
    val primaryColor: Color = Color(0xFF006C4C),
    val showColorPicker: Boolean = false
)

class SettingsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    fun toggleDarkMode() {
        _uiState.update { it.copy(isDarkMode = !it.isDarkMode) }
        saveSettings()
    }

    fun toggleDynamicColors() {
        _uiState.update { it.copy(useDynamicColors = !it.useDynamicColors) }
        saveSettings()
    }

    fun updatePrimaryColor(color: Color) {
        _uiState.update { it.copy(primaryColor = color) }
        saveSettings()
    }

    fun toggleColorPicker() {
        _uiState.update { it.copy(showColorPicker = !it.showColorPicker) }
    }

    private fun saveSettings() {
        viewModelScope.launch {
            // In a real app, we would save these settings to DataStore
            // For now, we'll just use SharedPreferences
            context?.getSharedPreferences("settings", Context.MODE_PRIVATE)?.edit()?.apply {
                putBoolean("dark_mode", _uiState.value.isDarkMode)
                putBoolean("dynamic_colors", _uiState.value.useDynamicColors)
                putInt("primary_color", _uiState.value.primaryColor.toArgb())
                apply()
            }
        }
    }

    private fun loadSettings() {
        viewModelScope.launch {
            context?.getSharedPreferences("settings", Context.MODE_PRIVATE)?.let { prefs ->
                _uiState.update {
                    it.copy(
                        isDarkMode = prefs.getBoolean("dark_mode", false),
                        useDynamicColors = prefs.getBoolean("dynamic_colors", true),
                        primaryColor = Color(prefs.getInt("primary_color", Color(0xFF006C4C).toArgb()))
                    )
                }
            }
        }
    }

    private var context: Context? = null

    fun setContext(context: Context) {
        this.context = context.applicationContext
        loadSettings()
    }

    override fun onCleared() {
        super.onCleared()
        context = null
    }
} 