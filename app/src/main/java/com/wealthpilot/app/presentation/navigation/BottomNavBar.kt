package com.wealthpilot.app.presentation.navigation

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun BottomNavBar(navController: NavController) {

    val items = listOf(
        BottomNavItem.HomeItem,
        BottomNavItem.InsightsItem,
        BottomNavItem.GoalItem
    )

    NavigationBar {
        items.forEach { item ->

            NavigationBarItem(
                selected = false,
                onClick = {
                    navController.navigate(item.route)
                },
                icon = {
                    Icon(item.icon, contentDescription = item.label)
                },
                label = {
                    Text(item.label)
                }
            )
        }
    }
}