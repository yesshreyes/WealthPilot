package com.wealthpilot.app.presentation.screens.insights

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wealthpilot.app.domain.repository.TransactionRepository

class InsightsViewModelFactory(
    private val repository: TransactionRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return InsightsViewModel(repository) as T
    }
}