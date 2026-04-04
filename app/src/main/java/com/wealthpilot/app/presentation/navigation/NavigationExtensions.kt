package com.wealthpilot.app.presentation.navigation

import androidx.navigation.NavController

fun NavController.navigateToAddTransaction(transactionId: String? = null) {
    navigate(AddTransaction(transactionId))
}

fun NavController.navigateToInsights() {
    navigate(Insights)
}

fun NavController.navigateToGoal() {
    navigate(Goal)
}