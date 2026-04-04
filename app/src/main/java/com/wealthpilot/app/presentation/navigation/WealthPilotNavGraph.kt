package com.wealthpilot.app.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.wealthpilot.app.data.repository.RepositoryProvider
import com.wealthpilot.app.presentation.screens.add_transaction.AddTransactionScreen
import com.wealthpilot.app.presentation.screens.home.HomeScreen

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
                navController = navController
            )
        }

        composable<AddTransaction> {
            val context = LocalContext.current
            val repository = RepositoryProvider.provideTransactionRepository(context)

            AddTransactionScreen(
                navController = navController,
                repository = repository
            )
        }

        composable<Insights> {
        }

        composable<Goal> {
        }
    }
}