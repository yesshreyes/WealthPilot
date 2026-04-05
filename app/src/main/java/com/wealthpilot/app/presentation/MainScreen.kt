package com.wealthpilot.app.presentation

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.wealthpilot.app.presentation.navigation.BottomNavBar
import com.wealthpilot.app.presentation.navigation.WealthPilotNavGraph
import com.wealthpilot.app.presentation.navigation.AddTransaction

@Composable
fun MainScreen() {

    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavBar(navController)
        },
//        floatingActionButton = {
//            FloatingActionButton(
//                onClick = {
//                    navController.navigate(AddTransaction(null))
//                }
//            ) {
//                Text("+")
//            }
//        }
    ) { padding ->

        WealthPilotNavGraph(
            navController = navController,
            padding = padding
        )
    }
}