package com.wealthpilot.app.presentation.screens.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wealthpilot.app.domain.model.Transaction
import com.wealthpilot.app.domain.model.TransactionType
import com.wealthpilot.app.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TransactionsViewModel(
    private val repository: TransactionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TransactionsUiState(isLoading = true))
    val uiState: StateFlow<TransactionsUiState> = _uiState.asStateFlow()

    init {
        observeTransactions()
    }

    private fun observeTransactions() {
        repository.getAllTransactions()
            .onEach { transactions ->
                _uiState.update {
                    it.copy(
                        transactions = transactions,
                        isLoading = false
                    )
                }
                applyFilters()
            }
            .catch { e ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message
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

    fun onSearchChange(query: String) {
        _uiState.update {
            it.copy(searchQuery = query)
        }
        applyFilters()
    }

    fun onTypeFilterChange(type: TransactionType?) {
        _uiState.update {
            it.copy(selectedType = type)
        }
        applyFilters()
    }

    private fun applyFilters() {
        val state = _uiState.value
        val list = state.transactions

        val filtered = list.filter { transaction ->

            val query = state.searchQuery.trim()
            val matchesSearch = if (query.isEmpty()) {
                true
            } else {
                val matchesNote = transaction.notes?.contains(query, ignoreCase = true) ?: false
                val matchesCategory = transaction.category.contains(query, ignoreCase = true)
                val matchesAmount = transaction.amount.toString().contains(query)
                matchesNote || matchesCategory || matchesAmount
            }

            val matchesType = state.selectedType?.let {
                transaction.type == it
            } ?: true

            val matchesCategoryFilter = state.selectedCategory?.let {
                transaction.category == it
            } ?: true

            matchesSearch && matchesType && matchesCategoryFilter
        }

        _uiState.update {
            it.copy(filteredTransactions = filtered)
        }
    }
}
