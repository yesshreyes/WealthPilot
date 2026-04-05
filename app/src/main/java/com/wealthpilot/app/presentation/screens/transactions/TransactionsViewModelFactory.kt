package com.wealthpilot.app.presentation.screens.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wealthpilot.app.domain.repository.TransactionRepository

class TransactionsViewModelFactory(
    private val repository: TransactionRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TransactionsViewModel(repository) as T
    }
}
