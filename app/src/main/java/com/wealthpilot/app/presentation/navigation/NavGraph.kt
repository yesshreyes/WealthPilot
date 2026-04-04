package com.wealthpilot.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun WealthPilotNavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Home
    ) {

        composable<Home> {
            // TODO: Replace with HomeScreen()
        }

        composable<AddTransaction> {
            // TODO: Replace with AddTransactionScreen()
        }

        composable<Insights> {
            // TODO: Replace with InsightsScreen()
        }

        composable<Goal> {
            // TODO: Replace with GoalScreen()
        }
    }
}