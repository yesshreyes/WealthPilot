package com.wealthpilot.app.presentation.screens.insights

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wealthpilot.app.domain.model.Transaction
import com.wealthpilot.app.domain.model.TransactionType
import com.wealthpilot.app.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.*
import java.util.*

class InsightsViewModel(
    private val repository: TransactionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(InsightsUiState(isLoading = true))
    val uiState: StateFlow<InsightsUiState> = _uiState.asStateFlow()

    init {
        observeTransactions()
    }

    private fun observeTransactions() {
        repository.getAllTransactions()
            .onEach { transactions ->
                val expenses = transactions.filter {
                    it.type == TransactionType.EXPENSE
                }

                val highest = getHighestCategory(expenses)
                val monthly = getMonthlyTotal(expenses)
                val frequent = getFrequentCategory(expenses)

                _uiState.update {
                    it.copy(
                        highestCategory = highest.first,
                        highestAmount = highest.second,
                        monthlyTotal = monthly,
                        frequentCategory = frequent,
                        isLoading = false
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun getHighestCategory(transactions: List<Transaction>): Pair<String, Double> {
        val grouped = transactions.groupBy { it.category }
            .mapValues { entry -> entry.value.sumOf { it.amount } }

        val max = grouped.maxByOrNull { it.value }

        return (max?.key to (max?.value ?: 0.0)) as Pair<String, Double>
    }

    private fun getMonthlyTotal(transactions: List<Transaction>): Double {
        val calendar = Calendar.getInstance()
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentYear = calendar.get(Calendar.YEAR)

        return transactions.filter {
            val cal = Calendar.getInstance()
            cal.timeInMillis = it.date
            cal.get(Calendar.MONTH) == currentMonth &&
                    cal.get(Calendar.YEAR) == currentYear
        }.sumOf { it.amount }
    }

    private fun getFrequentCategory(transactions: List<Transaction>): String {
        val grouped = transactions.groupBy { it.category }
        return grouped.maxByOrNull { it.value.size }?.key ?: ""
    }
}