package com.wealthpilot.app.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Flag
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: Any,
    val label: String,
    val icon: ImageVector
) {
    object HomeItem : BottomNavItem(Home, "Home", Icons.Default.Home)
    object InsightsItem : BottomNavItem(Insights, "Insights", Icons.Default.BarChart)
    object GoalItem : BottomNavItem(Goal, "Goals", Icons.Default.Flag)
}