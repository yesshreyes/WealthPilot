package com.wealthpilot.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.wealthpilot.app.presentation.MainScreen
import com.wealthpilot.app.ui.theme.WealthPilotTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            WealthPilotTheme {
                MainScreen()
            }
        }
    }
}