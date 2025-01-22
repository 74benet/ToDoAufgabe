package com.example.restaufgabe

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun AppNavigator() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController)
        }
        composable(
            "logged_in/{firstName}/{lastName}/{udid}",
            arguments = listOf(
                navArgument("firstName") { type = NavType.StringType },
                navArgument("lastName") { type = NavType.StringType },
                navArgument("udid") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            LoggedInScreen(
                navController = navController,
                firstName = backStackEntry.arguments?.getString("firstName") ?: "",
                lastName = backStackEntry.arguments?.getString("lastName") ?: "",
                udid = backStackEntry.arguments?.getString("udid") ?: ""
            )
        }
    }
}