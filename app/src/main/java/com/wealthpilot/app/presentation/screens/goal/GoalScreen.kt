package com.wealthpilot.app.presentation.screens.goal

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {}
    )

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= 33) {
            launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    val state by viewModel.uiState.collectAsState()

    GoalContent(
        state = state,
        onGoalChange = { viewModel.setGoal(it) },
        onSetGoal = { }
    )
}