package com.wealthpilot.app.presentation.screens.goal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wealthpilot.app.domain.model.TransactionType
import com.wealthpilot.app.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.*
import java.util.*

class GoalViewModel(
    private val repository: TransactionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(GoalUiState())
    val uiState: StateFlow<GoalUiState> = _uiState.asStateFlow()

    private var goalValue: Double = 0.0

    init {
        observeTransactions()
    }

    fun setGoal(amount: String) {
        goalValue = amount.toDoubleOrNull() ?: 0.0

        _uiState.update {
            it.copy(
                goalAmount = amount,
                isGoalSet = goalValue > 0
            )
        }
    }

    private fun observeTransactions() {
        repository.getAllTransactions()
            .onEach { transactions ->

                val monthlySavings = calculateSavings(transactions)

                val progress = if (goalValue > 0) {
                    (monthlySavings / goalValue).toFloat().coerceIn(0f, 1f)
                } else 0f

                _uiState.update {
                    it.copy(
                        savedAmount = monthlySavings,
                        progress = progress
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun calculateSavings(transactions: List<com.wealthpilot.app.domain.model.Transaction>): Double {

        val calendar = Calendar.getInstance()
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

        val monthly = transactions.filter {
            val cal = Calendar.getInstance()
            cal.timeInMillis = it.date
            cal.get(Calendar.MONTH) == month &&
                    cal.get(Calendar.YEAR) == year
        }

        val income = monthly
            .filter { it.type == TransactionType.INCOME }
            .sumOf { it.amount }

        val expense = monthly
            .filter { it.type == TransactionType.EXPENSE }
            .sumOf { it.amount }

        return income - expense
    }
}