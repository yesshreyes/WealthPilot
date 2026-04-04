package com.wealthpilot.app.presentation

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.wealthpilot.app.presentation.navigation.BottomNavBar
import com.wealthpilot.app.presentation.navigation.WealthPilotNavGraph

@Composable
fun MainScreen() {

    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavBar(navController)
        }
    ) { padding ->

        WealthPilotNavGraph(
            navController = navController,
            padding = padding
        )
    }
}