package com.wealthpilot.app.presentation.screens.insights

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wealthpilot.app.domain.model.CategoryData
import com.wealthpilot.app.domain.model.Transaction
import com.wealthpilot.app.domain.model.TransactionType
import com.wealthpilot.app.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.*
import java.util.*

class InsightsViewModel(
    private val repository: TransactionRepository
) : ViewModel() {

    val uiState: StateFlow<InsightsUiState> = repository.getAllTransactions()
        .map { allTransactions ->
            val calendar = Calendar.getInstance()
            val currentMonth = calendar.get(Calendar.MONTH)
            val currentYear = calendar.get(Calendar.YEAR)

            val currentMonthTransactions = allTransactions.filter {
                val cal = Calendar.getInstance()
                cal.timeInMillis = it.date
                cal.get(Calendar.MONTH) == currentMonth &&
                        cal.get(Calendar.YEAR) == currentYear
            }

            val expenses = currentMonthTransactions.filter {
                it.type == TransactionType.EXPENSE
            }
            val highest = getHighestCategory(expenses)
            val monthly = getMonthlyTotal(expenses)
            val frequent = getFrequentCategory(expenses)
            
            val categoryDataList = expenses.groupBy { it.category }
                .map { (category, txns) ->
                    CategoryData(category = category, amount = txns.sumOf { it.amount })
                }.sortedByDescending { it.amount }

            InsightsUiState(
                highestCategory = highest.first,
                highestAmount = highest.second,
                monthlyTotal = monthly,
                frequentCategory = frequent,
                isLoading = false,
                categoryBreakdown = categoryDataList
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = InsightsUiState(isLoading = true)
        )

    private fun getHighestCategory(transactions: List<Transaction>): Pair<String, Double> {
        val grouped = transactions.groupBy { it.category }
            .mapValues { entry -> entry.value.sumOf { it.amount } }

        val max = grouped.maxByOrNull { it.value }

        return if (max != null) {
            max.key to max.value
        } else {
            "" to 0.0
        }
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