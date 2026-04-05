package com.wealthpilot.app.presentation.screens.goal

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wealthpilot.app.data.repository.RepositoryProvider

@Composable
fun GoalScreen() {

    val context = LocalContext.current

    val viewModel: GoalViewModel = viewModel(
        factory = GoalViewModelFactory(
            RepositoryProvider.provideTransactionRepository(context),
            context
        )
    )

    val state by viewModel.uiState.collectAsState()

    GoalContent(
        state = state,
        onGoalChange = { viewModel.setGoal(it) },
        onSetGoal = { }
    )
}