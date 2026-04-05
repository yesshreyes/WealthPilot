package com.wealthpilot.app.presentation.screens.settings

import com.wealthpilot.app.data.preferences.ThemeMode

data class SettingsUiState(
    val currentTheme: ThemeMode = ThemeMode.SYSTEM,
    val isExporting: Boolean = false,
    val exportResult: ExportResult? = null,
    val logsCleared: Boolean = false
)

enum class ExportResult {
    SUCCESS, ERROR
}
