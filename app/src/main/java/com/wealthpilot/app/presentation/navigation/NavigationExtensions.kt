package com.wealthpilot.app.presentation.navigation

import androidx.navigation.NavController

fun NavController.navigateToAddTransaction() {
    navigate(AddTransaction)
}

fun NavController.navigateToInsights() {
    navigate(Insights)
}

fun NavController.navigateToGoal() {
    navigate(Goal)
}