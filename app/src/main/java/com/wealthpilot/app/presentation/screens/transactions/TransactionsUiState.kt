package com.wealthpilot.app.presentation.screens.transactions

import com.wealthpilot.app.domain.model.Transaction
import com.wealthpilot.app.domain.model.TransactionType

data class TransactionsUiState(
    val transactions: List<Transaction> = emptyList(),
    val filteredTransactions: List<Transaction> = emptyList(),
    val searchQuery: String = "",
    val selectedType: TransactionType? = null,
    val selectedCategory: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
