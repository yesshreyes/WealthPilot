package com.wealthpilot.app.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wealthpilot.app.domain.model.Transaction
import com.wealthpilot.app.domain.model.TransactionType
import com.wealthpilot.app.domain.repository.TransactionRepository
import com.wealthpilot.app.presentation.screens.home.components.CategoryData
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: TransactionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(isLoading = true))
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        observeTransactions()
    }

    private fun observeTransactions() {
        repository.getAllTransactions()
            .onEach { transactions ->
                val summary = calculateSummary(transactions)
                val breakdown = getCategoryBreakdown(transactions)
                val trend = calculateWeeklyTrend(transactions)

                _uiState.update {
                    it.copy(
                        transactions = transactions,
                        filteredTransactions = transactions,
                        totalIncome = summary.income,
                        totalExpense = summary.expense,
                        balance = summary.balance,
                        categoryData = breakdown,
                        weeklyTrend = trend,
                        isLoading = false
                    )
                }
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

    private fun calculateSummary(transactions: List<Transaction>): Summary {

        val income = transactions
            .filter { it.type == TransactionType.INCOME }
            .sumOf { it.amount }

        val expense = transactions
            .filter { it.type == TransactionType.EXPENSE }
            .sumOf { it.amount }

        return Summary(
            income = income,
            expense = expense,
            balance = income - expense
        )
    }

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch {
            repository.deleteTransaction(transaction)
        }
    }

    private fun getCategoryBreakdown(transactions: List<Transaction>): List<CategoryData> {
        return transactions
            .filter { it.type.name == "EXPENSE" }
            .groupBy { it.category }
            .map { (category, list) ->
                CategoryData(
                    category = category,
                    amount = list.sumOf { it.amount }
                )
            }
    }

    private fun calculateWeeklyTrend(transactions: List<Transaction>): Double {

        val now = System.currentTimeMillis()
        val oneWeek = 7 * 24 * 60 * 60 * 1000L

        val lastWeek = transactions.filter {
            it.date in (now - oneWeek)..now
        }

        val previousWeek = transactions.filter {
            it.date in (now - 2 * oneWeek)..(now - oneWeek)
        }

        val lastWeekExpense = lastWeek
            .filter { it.type.name == "EXPENSE" }
            .sumOf { it.amount }

        val prevWeekExpense = previousWeek
            .filter { it.type.name == "EXPENSE" }
            .sumOf { it.amount }

        return lastWeekExpense - prevWeekExpense
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

    fun onCategoryFilterChange(category: String?) {
        _uiState.update {
            it.copy(selectedCategory = category)
        }
        applyFilters()
    }

    private fun applyFilters() {

        val state = _uiState.value
        val list = state.transactions

        val filtered = list.filter { transaction ->

            val matchesSearch = transaction.notes
                ?.contains(state.searchQuery, ignoreCase = true)
                ?: true

            val matchesType = state.selectedType?.let {
                transaction.type == it
            } ?: true

            val matchesCategory = state.selectedCategory?.let {
                transaction.category == it
            } ?: true

            matchesSearch && matchesType && matchesCategory
        }

        _uiState.update {
            it.copy(filteredTransactions = filtered)
        }
    }
}

data class Summary(
    val income: Double,
    val expense: Double,
    val balance: Double
)