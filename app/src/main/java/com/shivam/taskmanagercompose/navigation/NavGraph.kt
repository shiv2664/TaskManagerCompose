package com.shivam.taskmanagercompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.shivam.taskmanagercompose.ui.screens.chatlist.ChatListScreen
import com.shivam.taskmanagercompose.ui.screens.chatscreen.ChatScreen
import com.shivam.taskmanagercompose.ui.screens.createlisting.CreateListingScreen
import com.shivam.taskmanagercompose.ui.screens.home.HomeScreen
import com.shivam.taskmanagercompose.ui.screens.listingdetails.ListingDetailsScreen
import com.shivam.taskmanagercompose.ui.screens.profile.ProfileScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Profile : Screen("profile/{userId}") {
        fun createRoute(userId: Long = 0) = "profile/$userId"
    }
    object CreateListing : Screen("create_listing")
    object ListingDetails : Screen("listing/{listingId}") {
        fun createRoute(listingId: Long) = "listing/$listingId"
    }
    object ChatList : Screen("chats")
    object Chat : Screen("chat/{userId}") {
        fun createRoute(userId: Long) = "chat/$userId"
    }
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onListingClick = { listingId ->
                    navController.navigate(Screen.ListingDetails.createRoute(listingId))
                },
                onCreateListingClick = {
                    navController.navigate(Screen.CreateListing.route)
                },
                onProfileClick = { userId ->
                    navController.navigate(Screen.Profile.createRoute(userId))
                },
                onChatListClick = {
                    navController.navigate(Screen.ChatList.route)
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
    }
}
