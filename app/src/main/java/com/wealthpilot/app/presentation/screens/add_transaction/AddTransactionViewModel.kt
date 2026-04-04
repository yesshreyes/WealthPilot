package com.wealthpilot.app.presentation.screens.add_transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wealthpilot.app.domain.model.Transaction
import com.wealthpilot.app.domain.model.TransactionType
import com.wealthpilot.app.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddTransactionViewModel(
    private val repository: TransactionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddTransactionUiState())
    val uiState: StateFlow<AddTransactionUiState> = _uiState

    fun onAmountChange(value: String) {
        _uiState.update {
            it.copy(
                amount = value,
                isValid = validate(it.copy(amount = value))
            )
        }
    }

    fun onCategoryChange(value: String) {
        _uiState.update {
            it.copy(
                category = value,
                isValid = validate(it.copy(category = value))
            )
        }
    }

    fun onTypeChange(type: TransactionType) {
        _uiState.update {
            it.copy(type = type)
        }
    }

    fun onNotesChange(value: String) {
        _uiState.update {
            it.copy(notes = value)
        }
    }

    private fun validate(state: AddTransactionUiState): Boolean {
        return state.amount.isNotBlank() && state.category.isNotBlank()
    }

    fun saveTransaction(onSaved: () -> Unit) {
        val state = _uiState.value

        if (!state.isValid) return

        viewModelScope.launch {
            repository.insertTransaction(
                Transaction(
                    amount = state.amount.toDouble(),
                    category = state.category,
                    type = state.type,
                    date = System.currentTimeMillis(),
                    notes = state.notes
                )
            )
            onSaved()
        }
    }
}