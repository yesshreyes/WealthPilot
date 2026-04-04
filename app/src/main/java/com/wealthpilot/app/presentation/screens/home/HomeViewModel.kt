package com.wealthpilot.app.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wealthpilot.app.domain.model.Transaction
import com.wealthpilot.app.domain.model.TransactionType
import com.wealthpilot.app.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: TransactionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(isLoading = true))
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        repository.getAllTransactions()
            .onEach { transactions ->

                val income = transactions
                    .filter { it.type == TransactionType.INCOME }
                    .sumOf { it.amount }

                val expense = transactions
                    .filter { it.type == TransactionType.EXPENSE }
                    .sumOf { it.amount }

                _uiState.update {
                    it.copy(
                        transactions = transactions,
                        totalIncome = income,
                        totalExpense = expense,
                        balance = income - expense,
                        isLoading = false
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch {
            repository.deleteTransaction(transaction)
        }
    }
}