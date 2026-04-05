package com.wealthpilot.app.presentation.screens.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.wealthpilot.app.data.repository.RepositoryProvider

@Composable
fun SettingsScreen(
    navController: NavController
) {
    val context = LocalContext.current

    val viewModel: SettingsViewModel = viewModel(
        factory = SettingsViewModelFactory(
            RepositoryProvider.provideTransactionRepository(context),
            context
        )
    )

    val state by viewModel.uiState.collectAsState()

    SettingsContent(
        state = state,
        onThemeSelected = viewModel::setThemeMode,
        onExportData = viewModel::exportData,
        onClearLogs = viewModel::clearLogs,
        onResetExportState = viewModel::resetExportState,
        onResetLogsState = viewModel::resetLogsClearState,
        onNavigateBack = { navController.popBackStack() }
    )
}
