package com.wealthpilot.app.presentation.screens.transactions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.wealthpilot.app.data.repository.RepositoryProvider
import com.wealthpilot.app.presentation.navigation.AddTransaction

@Composable
fun TransactionsScreen(
    navController: NavController
) {
    val context = LocalContext.current

    val viewModel: TransactionsViewModel = viewModel(
        factory = TransactionsViewModelFactory(
            RepositoryProvider.provideTransactionRepository(context)
        )
    )

    val state by viewModel.uiState.collectAsState()

    TransactionsContent(
        state = state,
        onDelete = viewModel::deleteTransaction,
        onEdit = { transaction ->
            navController.navigate(AddTransaction(transactionId = transaction.id))
        },
        onSearchChange = viewModel::onSearchChange,
        onTypeFilterChange = viewModel::onTypeFilterChange,
        onNavigateBack = { navController.popBackStack() }
    )
}
