package com.wealthpilot.app.presentation.screens.add_transaction

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.wealthpilot.app.domain.repository.TransactionRepository

@Composable
fun AddTransactionScreen(
    navController: NavController,
    repository: TransactionRepository,
    transactionId: String?
) {

    val viewModel = remember {
        AddTransactionViewModel(repository)
    }

    val state by viewModel.uiState.collectAsState()

    AddTransactionContent(
        state = state,
        onAmountChange = viewModel::onAmountChange,
        onCategoryChange = viewModel::onCategoryChange,
        onTypeChange = viewModel::onTypeChange,
        onNotesChange = viewModel::onNotesChange,
        onSave = {
            viewModel.saveTransaction(transactionId) {
                navController.popBackStack()
            }
        }
    )
}