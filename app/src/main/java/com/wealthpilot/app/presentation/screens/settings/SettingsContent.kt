package com.wealthpilot.app.presentation.screens.settings

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.SettingsBrightness
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wealthpilot.app.data.preferences.ThemeMode
import com.wealthpilot.app.ui.theme.WealthPilotTheme

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

    val colors = MaterialTheme.colorScheme

    LaunchedEffect(state.exportResult, state.logsCleared) {
        when (state.exportResult) {
            ExportResult.SUCCESS -> {
                Toast.makeText(context, "Data exported", Toast.LENGTH_SHORT).show()
                onResetExportState()
            }
            ExportResult.ERROR -> {
                Toast.makeText(context, "Export failed", Toast.LENGTH_SHORT).show()
                onResetExportState()
            }
            else -> {}
        }
        if (state.logsCleared) {
            Toast.makeText(context, "Logs cleared", Toast.LENGTH_SHORT).show()
            onResetLogsState()
        }
    }

    Scaffold(
        containerColor = colors.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Settings",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, null, tint = colors.primary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = colors.onBackground
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Theme Section
            GlassCard(colors = colors) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = colors.primary.copy(alpha = 0.15f),
                            modifier = Modifier.size(44.dp)
                        ) {
                            Icon(
                                Icons.Default.SettingsBrightness,
                                null,
                                tint = colors.primary,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                        Text(
                            "Theme",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = colors.onSurface
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    ThemeMode.entries.forEach { mode ->
                        ThemeOption(
                            mode = mode,
                            selected = state.currentTheme == mode,
                            colors = colors,
                            onClick = { onThemeSelected(mode) }
                        )
                    }
                }
            }

            // Data Section
            GlassCard(colors = colors) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = colors.tertiary.copy(alpha = 0.15f),
                            modifier = Modifier.size(44.dp)
                        ) {
                            Icon(
                                Icons.Default.CloudDownload,
                                null,
                                tint = colors.tertiary,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                        Text(
                            "Data",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = colors.onSurface
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Export Button
                    Button(
                        onClick = { showExportDialog = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .shadow(8.dp, RoundedCornerShape(16.dp)),
                        enabled = !state.isExporting,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colors.primary
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        if (state.isExporting) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = colors.onPrimary,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Icon(Icons.Default.CloudDownload, null, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.size(8.dp))
                            Text("Export to CSV", fontWeight = FontWeight.Medium)
                        }
                    }

                    // Clear Button
                    OutlinedButton(
                        onClick = { showClearLogsDialog = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = colors.error
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Icon(Icons.Default.Delete, null, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.size(8.dp))
                        Text("Clear All Logs", fontWeight = FontWeight.Medium)
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }

    // Dialogs
    if (showClearLogsDialog) {
        AlertDialog(
            onDismissRequest = { showClearLogsDialog = false },
            containerColor = colors.surface,
            title = { Text("Clear All Logs?") },
            text = { Text("This cannot be undone.", color = colors.onSurface.copy(alpha = 0.7f)) },
            confirmButton = {
                Button(
                    onClick = { showClearLogsDialog = false; onClearLogs() },
                    colors = ButtonDefaults.buttonColors(containerColor = colors.error)
                ) {
                    Text("Clear")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showClearLogsDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    if (showExportDialog) {
        AlertDialog(
            onDismissRequest = { showExportDialog = false },
            containerColor = colors.surface,
            title = { Text("Export Data") },
            text = { Text("Export as CSV to Downloads?", color = colors.onSurface.copy(alpha = 0.7f)) },
            confirmButton = {
                Button(
                    onClick = { showExportDialog = false; onExportData() },
                    colors = ButtonDefaults.buttonColors(containerColor = colors.primary)
                ) {
                    Text("Export")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showExportDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun ThemeOption(
    mode: ThemeMode,
    selected: Boolean,
    colors: androidx.compose.material3.ColorScheme,
    onClick: () -> Unit
) {
    val label = mode.name.lowercase().replaceFirstChar { it.uppercase() }

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = if (selected) colors.primary.copy(alpha = 0.12f) else Color.Transparent,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                color = if (selected) colors.primary else colors.onSurface.copy(alpha = 0.8f),
                fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal
            )
            if (selected) {
                Icon(
                    Icons.Default.Check,
                    null,
                    tint = colors.primary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
private fun GlassCard(
    colors: androidx.compose.material3.ColorScheme,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(24.dp),
                ambientColor = colors.primary.copy(alpha = 0.15f),
                spotColor = colors.primary.copy(alpha = 0.1f)
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = colors.surface.copy(alpha = 0.6f)
        )
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsContentPreview() {

    val dummyState = SettingsUiState(
        currentTheme = ThemeMode.SYSTEM,
        isExporting = false,
        logsCleared = false
    )

    WealthPilotTheme {
        SettingsContent(
            state = dummyState,
            onThemeSelected = {},
            onExportData = {},
            onClearLogs = {},
            onResetExportState = {},
            onResetLogsState = {},
            onNavigateBack = {}
        )
    }
}