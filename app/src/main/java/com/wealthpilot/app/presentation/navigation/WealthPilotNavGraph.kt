package com.wealthpilot.app.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun WealthPilotNavGraph(
    navController: NavHostController,
    padding: PaddingValues
) {

    NavHost(
        navController = navController,
        startDestination = Home
    ) {

        composable<Home> {
            // HomeScreen(padding)
        }

        composable<AddTransaction> {
            // AddTransactionScreen()
        }

        composable<Insights> {
            // InsightsScreen()
        }

        composable<Goal> {
            // GoalScreen()
        }
    }
}