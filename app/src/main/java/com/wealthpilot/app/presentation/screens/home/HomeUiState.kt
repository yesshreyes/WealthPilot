package com.wealthpilot.app.presentation.screens.home

import com.wealthpilot.app.domain.model.Transaction
import com.wealthpilot.app.domain.model.TransactionType
import com.wealthpilot.app.presentation.screens.home.components.CategoryData

data class HomeUiState(
    val transactions: List<Transaction> = emptyList(),
    val totalIncome: Double = 0.0,
    val totalExpense: Double = 0.0,
    val balance: Double = 0.0,
    val isLoading: Boolean = false,
    val error: String? = null,
    val categoryData: List<CategoryData> = emptyList(),
    val weeklyTrend: Double = 0.0
)