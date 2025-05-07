package com.shivam.taskmanagercompose.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.shivam.taskmanagercompose.ui.screens.chatlist.ChatListScreen
import androidx.compose.material3.Scaffold
import com.shivam.taskmanagercompose.ui.screens.chatscreen.ChatScreen
import com.shivam.taskmanagercompose.ui.screens.createlisting.CreateListingScreen
import com.shivam.taskmanagercompose.ui.screens.home.HomeScreen
import com.shivam.taskmanagercompose.ui.screens.listingdetails.ListingDetailsScreen
import com.shivam.taskmanagercompose.ui.screens.settings.SettingsScreen
import com.shivam.taskmanagercompose.ui.screens.profile.ProfileScreen
import com.shivam.taskmanagercompose.ui.screens.task.TaskScreen

sealed class Screen(val route: String, val title: String, val icon: ImageVector, val selectedIcon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Outlined.Home, Icons.Filled.Home)
    object Profile : Screen("profile/{userId}", "Profile", Icons.Outlined.Person, Icons.Filled.Person) {
        fun createRoute(userId: Long = 0) = "profile/$userId"
    }
    object CreateListing : Screen("create_listing")
    object ListingDetails : Screen("listing/{listingId}") {
        fun createRoute(listingId: Long) = "listing/$listingId"
    }
    object ChatList : Screen("chats")
    object Chat : Screen("chat/{userId}","Chat", Icons.Outlined.List, Icons.Filled.List) {
        fun createRoute(userId: Long) = "chat/$userId"
    }

    object Task : Screen("task", "Task", Icons.Outlined.List, Icons.Filled.List)
    object Settings : Screen("settings","Settings", Icons.Outlined.Settings, Icons.Filled.Settings)

    companion object {
        val items = listOf(
            Home, Chat,Task, Settings, Profile,
        )
    }

}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        Screen.items.forEach { screen ->
            NavigationBarItem(
                icon = {
                    if (currentDestination?.route == screen.route) {
                        Icon(imageVector = screen.selectedIcon, contentDescription = screen.title)
                    } else {
                        Icon(imageVector = screen.icon, contentDescription = screen.title)
                    }

                },
                label = { Text(screen.title) },
                selected = currentDestination?.route == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun NavGraph(navController: NavHostController) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { innerPadding ->









    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    )  {
        composable(Screen.Home.route) {
            HomeScreen(
                onListingClick = { listingId ->
                    navController.navigate(Screen.ListingDetails.createRoute(listingId))
                },
                onCreateListingClick = {
                    navController.navigate(Screen.CreateListing.route) {
                    }
                },
                onProfileClick = { userId ->
                    navController.navigate(Screen.Profile.createRoute(userId))
                },
                onChatListClick = {
                    navController.navigate(Screen.ChatList.route)
                },
                onCreateTaskClick = {taskId ->
                    navController.navigate(Screen.Task.route) 
                }

            )
        }

        composable(
            route = Screen.Profile.route,
            arguments = listOf(
                navArgument("userId") {
                    type = NavType.LongType
                    defaultValue = 0L
                }
            )
        ) { backStackEntry ->
            ProfileScreen(
                userId = backStackEntry.arguments?.getLong("userId") ?: 0L,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.CreateListing.route) {
            CreateListingScreen(
                onNavigateBack = { navController.popBackStack() },
                onListingCreated = { listingId ->
                    navController.navigate(Screen.ListingDetails.createRoute(listingId)) {
                        popUpTo(Screen.Home.route)
                    }
                }
            )
        }

        composable(
            route = Screen.ListingDetails.route,
            arguments = listOf(
                navArgument("listingId") {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->
            ListingDetailsScreen(
                listingId = backStackEntry.arguments?.getLong("listingId") ?: 0L,
                onNavigateBack = { navController.popBackStack() },
                onCreatorProfileClick = { userId ->
                    navController.navigate(Screen.Profile.createRoute(userId))
                }
            )
        }

        composable(Screen.ChatList.route) {
            ChatListScreen(
                onNavigateBack = { navController.popBackStack() },
                onChatClick = { userId ->
                    navController.navigate(Screen.Chat.createRoute(userId))
                }
            )
        }

        composable(
            route = Screen.Chat.route,
            arguments = listOf(
                navArgument("userId") {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->
            ChatScreen(
                userId = backStackEntry.arguments?.getLong("userId") ?: 0L,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.Task.route,
           
        ) {
            TaskScreen()
           
        }
    }
}}
