package com.wealthpilot.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.wealthpilot.app.presentation.MainScreen
import com.wealthpilot.app.ui.theme.WealthPilotTheme
import com.wealthpilot.app.data.preferences.SettingsPreferences
import com.wealthpilot.app.data.preferences.ThemeMode

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            val settingsPreferences = SettingsPreferences.getInstance(this)

            val themeMode by settingsPreferences.themeMode.collectAsState(
                initial = ThemeMode.SYSTEM
            )

            val isDarkTheme = when (themeMode) {
                ThemeMode.LIGHT -> false
                ThemeMode.DARK -> true
                ThemeMode.SYSTEM -> isSystemInDarkTheme()
            }

            WealthPilotTheme(darkTheme = isDarkTheme) {
                MainScreen()
            }
        }
    }
}