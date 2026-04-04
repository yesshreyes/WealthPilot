package com.wealthpilot.app.presentation.screens.home

import com.wealthpilot.app.domain.model.Transaction

data class HomeUiState(
    val transactions: List<Transaction> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)