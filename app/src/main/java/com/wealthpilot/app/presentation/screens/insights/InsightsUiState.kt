package com.wealthpilot.app.presentation.screens.insights

import com.wealthpilot.app.domain.model.CategoryData

data class InsightsUiState(
    val highestCategory: String = "",
    val highestAmount: Double = 0.0,
    val monthlyTotal: Double = 0.0,
    val frequentCategory: String = "",
    val isLoading: Boolean = false,
    val categoryBreakdown: List<CategoryData> = emptyList()
)