package com.wealthpilot.app.presentation.screens.settings

import android.content.Context
import android.os.Environment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wealthpilot.app.data.preferences.SettingsPreferences
import com.wealthpilot.app.data.preferences.ThemeMode
import com.wealthpilot.app.domain.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SettingsViewModel(
    private val repository: TransactionRepository,
    private val context: Context
) : ViewModel() {

    private val settingsPreferences = SettingsPreferences.getInstance(context)

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            settingsPreferences.themeMode.collect { mode ->
                _uiState.update { it.copy(currentTheme = mode) }
            }
        }
    }

    fun setThemeMode(mode: ThemeMode) {
        settingsPreferences.setThemeMode(mode)
    }

    fun clearLogs() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.deleteAllTransactions()
                _uiState.update { it.copy(logsCleared = true) }
            } catch (e: Exception) {
                // handle failure silently or add error state
            }
        }
    }

    fun resetLogsClearState() {
        _uiState.update { it.copy(logsCleared = false) }
    }

    fun resetExportState() {
        _uiState.update { it.copy(exportResult = null) }
    }

    fun exportData() {
        _uiState.update { it.copy(isExporting = true, exportResult = null) }

        viewModelScope.launch {
            try {
                val transactions = repository.getAllTransactions().first()
                val csvData = buildString {
                    append("ID,Amount,Category,Type,Date,Notes\n")
                    transactions.forEach { txn ->
                        val dateString = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date(txn.date))
                        append("${txn.id},${txn.amount},${txn.category},${txn.type.name},${dateString},${txn.notes?.replace(",", " ") ?: ""}\n")
                    }
                }

                withContext(Dispatchers.IO) {
                    val filename = "WealthPilot_Export_${System.currentTimeMillis()}.csv"
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                        val contentValues = android.content.ContentValues().apply {
                            put(android.provider.MediaStore.MediaColumns.DISPLAY_NAME, filename)
                            put(android.provider.MediaStore.MediaColumns.MIME_TYPE, "text/csv")
                            put(android.provider.MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
                        }
                        val uri = context.contentResolver.insert(android.provider.MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
                        if (uri != null) {
                            context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                                outputStream.write(csvData.toByteArray())
                            }
                        } else {
                            throw Exception("Failed to create MediaStore entry")
                        }
                    } else {
                        val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                        if (!downloadsDir.exists()) downloadsDir.mkdirs()
                        val file = File(downloadsDir, filename)
                        file.writeText(csvData)
                    }
                }

                _uiState.update { it.copy(isExporting = false, exportResult = ExportResult.SUCCESS) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isExporting = false, exportResult = ExportResult.ERROR) }
            }
        }
    }
}
