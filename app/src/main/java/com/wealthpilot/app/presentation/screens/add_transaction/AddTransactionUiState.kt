package com.wealthpilot.app.presentation.screens.add_transaction

import com.wealthpilot.app.domain.model.TransactionType

data class AddTransactionUiState(
    val amount: String = "",
    val category: String = "",
    val type: TransactionType = TransactionType.EXPENSE,
    val notes: String = "",
    val isValid: Boolean = false
)