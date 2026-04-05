package com.wealthpilot.app.presentation.screens.goal

import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wealthpilot.app.domain.model.Transaction
import com.wealthpilot.app.domain.model.TransactionType
import com.wealthpilot.app.domain.repository.TransactionRepository
import com.wealthpilot.app.utils.NotificationHelper
import kotlinx.coroutines.flow.*
import java.util.*

class GoalViewModel(
    private val repository: TransactionRepository,
    private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(GoalUiState())
    val uiState: StateFlow<GoalUiState> = _uiState.asStateFlow()

    private val prefs = context.getSharedPreferences("goal_prefs", Context.MODE_PRIVATE)

    private var goalValue: Double = 0.0

    private var hasNotified = false

    init {
        loadGoal()
        observeTransactions()
    }

    private fun loadGoal() {
        val saved = prefs.getFloat("goal", 0f).toDouble()
        goalValue = saved

        _uiState.update {
            it.copy(
                goalAmount = if (saved > 0) saved.toString() else "",
                isGoalSet = saved > 0
            )
        }
    }

    fun setGoal(amount: String) {
        goalValue = amount.toDoubleOrNull() ?: 0.0

        prefs.edit {
            putFloat("goal", goalValue.toFloat())
        }

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

                val progress = if (goalValue > 0) {
                    (expense / goalValue).toFloat().coerceIn(0f, 1f)
                } else 0f

                val isExceeded = goalValue > 0 && expense > goalValue

                if (isExceeded && !hasNotified) {
                    NotificationHelper.showGoalExceeded(context)
                    hasNotified = true
                }

                if (!isExceeded) {
                    hasNotified = false
                }

                _uiState.update {
                    it.copy(
                        spentAmount = expense,
                        progress = progress,
                        isGoalExceeded = isExceeded
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}