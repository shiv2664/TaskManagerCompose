package com.shivam.taskmanagercompose.ui.screens.home

import androidx.compose.animation.*
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.shivam.taskmanagercompose.data.GamePlatform
import com.shivam.taskmanagercompose.ui.components.ListingCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onListingClick: (Long) -> Unit,
    onCreateListingClick: () -> Unit,
    onProfileClick: (Long) -> Unit,
    onChatListClick: () -> Unit,
    onCreateTaskClick: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showFilterMenu by remember { mutableStateOf(false) }
    var showPlatformMenu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Game Buddy Finder") },
                navigationIcon = {
                    IconButton(onClick = { showFilterMenu = true }) {
                        Icon(
                            imageVector = Icons.Default.FilterList,
                            contentDescription = "Filter listings"
                        )
                    }
                    DropdownMenu(
                        expanded = showFilterMenu,
                        onDismissRequest = { showFilterMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("All Games") },
                            onClick = {
                                viewModel.setGameFilter(null)
                                showFilterMenu = false
                            }
                        )
                        uiState.availableGames.forEach { game ->
                            DropdownMenuItem(
                                text = { Text(game) },
                                onClick = {
                                    viewModel.setGameFilter(game)
                                    showFilterMenu = false
                                }
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = { showPlatformMenu = true }) {
                        Icon(
                            imageVector = Icons.Default.Devices,
                            contentDescription = "Filter by platform"
                        )
                    }
                    DropdownMenu(
                        expanded = showPlatformMenu,
                        onDismissRequest = { showPlatformMenu = false }
                    ) {
                        GamePlatform.values().forEach { platform ->
                            DropdownMenuItem(
                                text = { Text(platform.name.replace("_", " ")) },
                                onClick = {
                                    viewModel.setPlatformFilter(platform)
                                    showPlatformMenu = false
                                }
                            )
                        }
                    }
                    IconButton(onClick = { onProfileClick(0) }) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "My Profile"
                        )
                    }
                    IconButton(onClick = onChatListClick) {
                        Icon(
                            imageVector = Icons.Default.Chat,
                            contentDescription = "Chats"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreateTaskClick,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Create listing"
                )
            }
        }
    ) { paddingValues ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (uiState.listings.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "No listings found",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = onCreateListingClick) {
                        Text("Create a listing")
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = uiState.listings,
                    key = { it.id }
                ) { listing ->
                    ListingCard(
                        listing = listing,
                        onClick = { onListingClick(listing.id) },
                        onCreatorClick = { onProfileClick(listing.creatorId) }
                    )
                }
            }
        }
    }
} 