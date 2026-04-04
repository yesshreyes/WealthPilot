package com.wealthpilot.app.presentation.screens.insights

data class InsightsUiState(
    val highestCategory: String = "",
    val highestAmount: Double = 0.0,
    val monthlyTotal: Double = 0.0,
    val frequentCategory: String = "",
    val isLoading: Boolean = false
)