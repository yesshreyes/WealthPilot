package com.wealthpilot.app.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.wealthpilot.app.data.repository.RepositoryProvider
import com.wealthpilot.app.presentation.screens.add_transaction.AddTransactionScreen
import com.wealthpilot.app.presentation.screens.goal.GoalScreen
import com.wealthpilot.app.presentation.screens.home.HomeScreen
import com.wealthpilot.app.presentation.screens.insights.InsightsScreen

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
            HomeScreen(
                navController = navController,
                padding = padding
            )
        }

        composable<AddTransaction> { backStackEntry ->

            val context = LocalContext.current
            val repository = RepositoryProvider.provideTransactionRepository(context)

            val args = backStackEntry.toRoute<AddTransaction>()

            AddTransactionScreen(
                navController = navController,
                repository = repository,
                transactionId = args.transactionId
            )
        }

        composable<Insights> {
            InsightsScreen()
        }

        composable<Goal> {
            GoalScreen()
        }
    }
}