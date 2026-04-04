package com.wealthpilot.app.presentation.screens.home

import android.R.attr.padding
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.wealthpilot.app.data.repository.RepositoryProvider
import com.wealthpilot.app.presentation.navigation.AddTransaction

@Composable
fun HomeScreen(
    navController: NavController,
    padding: PaddingValues
) {
    val context = LocalContext.current

    val viewModel: HomeViewModel = viewModel(
        factory = HomeViewModelFactory(
            RepositoryProvider.provideTransactionRepository(context)
        )
    )

    val state by viewModel.uiState.collectAsState()

    HomeContent(
        state = state,
        padding = padding,
        onDelete = {
            viewModel.deleteTransaction(it)
        },
        onEdit = {
            navController.navigate(AddTransaction(it.id))
        },
        onSearchChange = {
            viewModel.onSearchChange(it)
        },
        onTypeFilterChange = {
            viewModel.onTypeFilterChange(it)
        }
    )
}