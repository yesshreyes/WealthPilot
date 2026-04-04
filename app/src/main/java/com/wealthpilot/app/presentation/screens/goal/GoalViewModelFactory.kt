package com.wealthpilot.app.presentation.screens.goal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wealthpilot.app.domain.repository.TransactionRepository

class GoalViewModelFactory(
    private val repository: TransactionRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GoalViewModel(repository) as T
    }
}