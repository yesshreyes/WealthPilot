package com.wealthpilot.app.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.wealthpilot.app.ui.theme.WealthPilotTheme

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.HomeItem,
        BottomNavItem.InsightsItem,
        BottomNavItem.GoalItem
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val colors = MaterialTheme.colorScheme

    NavigationBar(
        modifier = Modifier
            .height(100.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                spotColor = Color.Black.copy(alpha = 0.1f)
            ),
        containerColor = colors.surface.copy(alpha = 0.95f),
        tonalElevation = 0.dp
    ) {
        items.forEach { item ->

            val routeClassName = item.route::class.simpleName
            val selected = currentRoute?.endsWith(routeClassName ?: "") == true ||
                    currentRoute == routeClassName

            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (!selected) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    if (selected) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(
                                    brush = Brush.linearGradient(
                                        colors = listOf(
                                            colors.primary.copy(alpha = 0.2f),
                                            colors.tertiary.copy(alpha = 0.1f)
                                        )
                                    ),
                                    shape = RoundedCornerShape(12.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    } else {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            modifier = Modifier.size(24.dp),
                            tint = colors.onSurface.copy(alpha = 0.6f)
                        )
                    }
                },
                label = {
                    Text(
                        text = item.label,
                        fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colors.primary,
                    selectedTextColor = colors.primary,
                    unselectedIconColor = colors.onSurface.copy(alpha = 0.6f),
                    unselectedTextColor = colors.onSurface.copy(alpha = 0.6f),
                    indicatorColor = Color.Transparent
                ),
                alwaysShowLabel = true
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavBarPreview() {
    WealthPilotTheme {
        val navController = rememberNavController()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.BottomCenter
        ) {
            BottomNavBar(navController = navController)
        }
    }
}