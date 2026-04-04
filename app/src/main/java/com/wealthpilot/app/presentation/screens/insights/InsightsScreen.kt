package com.wealthpilot.app.presentation.screens.insights

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wealthpilot.app.data.repository.RepositoryProvider
import com.wealthpilot.app.presentation.screens.home.HomeViewModelFactory

@Composable
fun InsightsScreen() {

    val context = LocalContext.current

    val viewModel: InsightsViewModel = viewModel(
        factory = InsightsViewModelFactory(
            RepositoryProvider.provideTransactionRepository(context)
        )
    )

    val state by viewModel.uiState.collectAsState()

    InsightsContent(state = state)
}