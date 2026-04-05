package com.wealthpilot.app.presentation.screens.settings

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.wealthpilot.app.data.preferences.ThemeMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsContent(
    state: SettingsUiState,
    onThemeSelected: (ThemeMode) -> Unit,
    onExportData: () -> Unit,
    onClearLogs: () -> Unit,
    onResetExportState: () -> Unit,
    onResetLogsState: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    var showClearLogsDialog by remember { mutableStateOf(false) }
    var showExportDialog by remember { mutableStateOf(false) }

    LaunchedEffect(state.exportResult) {
        if (state.exportResult == ExportResult.SUCCESS) {
            Toast.makeText(context, "Data exported to Downloads", Toast.LENGTH_SHORT).show()
            onResetExportState()
        } else if (state.exportResult == ExportResult.ERROR) {
            Toast.makeText(context, "Export failed", Toast.LENGTH_SHORT).show()
            onResetExportState()
        }
    }

    LaunchedEffect(state.logsCleared) {
        if (state.logsCleared) {
            Toast.makeText(context, "All logs cleared", Toast.LENGTH_SHORT).show()
            onResetLogsState()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "App Theme",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                ThemeMode.values().forEach { mode ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = state.currentTheme == mode,
                            onClick = { onThemeSelected(mode) }
                        )
                        Text(
                            text = mode.name.lowercase().replaceFirstChar { it.uppercase() },
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Data Management",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Button(
                    onClick = { showExportDialog = true },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !state.isExporting
                ) {
                    if (state.isExporting) {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(end = 8.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Text("Exporting...")
                    } else {
                        Text("Export to CSV")
                    }
                }

                OutlinedButton(
                    onClick = { showClearLogsDialog = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Clear All Logs")
                }
            }
            if (showClearLogsDialog) {
                androidx.compose.material3.AlertDialog(
                    onDismissRequest = { showClearLogsDialog = false },
                    title = { Text("Confirm Action") },
                    text = { Text("Are you sure you want to clear all logs? This cannot be undone.") },
                    confirmButton = {
                        Button(
                            onClick = {
                                showClearLogsDialog = false
                                onClearLogs()
                            }
                        ) {
                            Text("Yes, Clear")
                        }
                    },
                    dismissButton = {
                        OutlinedButton(
                            onClick = { showClearLogsDialog = false }
                        ) {
                            Text("Cancel")
                        }
                    }
                )
            }
            if (showExportDialog) {
                androidx.compose.material3.AlertDialog(
                    onDismissRequest = { showExportDialog = false },
                    title = { Text("Export Data") },
                    text = { Text("Do you want to export your data as a CSV file?") },
                    confirmButton = {
                        Button(
                            onClick = {
                                showExportDialog = false
                                onExportData()
                            }
                        ) {
                            Text("Export")
                        }
                    },
                    dismissButton = {
                        OutlinedButton(
                            onClick = { showExportDialog = false }
                        ) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }
    }
}
