package com.shivam.taskmanagercompose.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shivam.taskmanagercompose.data.GameListing
import com.shivam.taskmanagercompose.data.GamePlatform
import com.shivam.taskmanagercompose.data.repository.ListingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val listings: List<GameListing> = emptyList(),
    val isLoading: Boolean = true,
    val gameFilter: String? = null,
    val platformFilter: GamePlatform? = null,
    val availableGames: List<String> = emptyList()
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ListingRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _gameFilter = MutableStateFlow<String?>(null)
    private val _platformFilter = MutableStateFlow<GamePlatform?>(null)

    init {
        viewModelScope.launch {
            combine(
                _gameFilter,
                _platformFilter,
                repository.getAvailableGames(),
                repository.getAllListings()
            ) { gameFilter, platformFilter, availableGames, listings ->
                var filteredListings = listings

                if (gameFilter != null) {
                    filteredListings = filteredListings.filter { it.game == gameFilter }
                }

                if (platformFilter != null) {
                    filteredListings = filteredListings.filter { 
                        it.platform == platformFilter || it.platform == GamePlatform.ANY 
                    }
                }

                HomeUiState(
                    listings = filteredListings,
                    isLoading = false,
                    gameFilter = gameFilter,
                    platformFilter = platformFilter,
                    availableGames = availableGames
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }

    fun setGameFilter(game: String?) {
        _gameFilter.value = game
    }

    fun setPlatformFilter(platform: GamePlatform?) {
        _platformFilter.value = platform
    }

    fun joinListing(listingId: Long) {
        viewModelScope.launch {
            repository.joinListing(listingId)
        }
    }

    fun leaveListing(listingId: Long) {
        viewModelScope.launch {
            repository.leaveListing(listingId)
        }
    }
} 