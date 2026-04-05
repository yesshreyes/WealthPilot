package com.wealthpilot.app.presentation.screens.goal

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wealthpilot.app.domain.repository.TransactionRepository

class GoalViewModelFactory(
    private val repository: TransactionRepository,
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GoalViewModel(repository, context) as T
    }
}