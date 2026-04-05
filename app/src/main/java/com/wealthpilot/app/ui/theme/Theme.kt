package com.wealthpilot.app.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Green80,
    secondary = Sage80,
    tertiary = Teal80,
    background = Color(0xFF1C1B1F),
    surface = Color(0xFF1C1B1F),
    onPrimary = Color(0xFF003300),
    onSecondary = Color(0xFF1B3300),
    onTertiary = Color(0xFF00332E),
    onBackground = Color(0xFFE0E3DF),
    onSurface = Color(0xFFE0E3DF)
)

private val LightColorScheme = lightColorScheme(
    primary = Green40,
    secondary = Sage40,
    tertiary = Teal40,
    background = Color(0xFFFCFDF7),
    surface = Color(0xFFFCFDF7),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1C1A),
    onSurface = Color(0xFF1C1C1A),
    error = Color(0xFFBA1A1A),
    onError = Color.White
)

@Composable
fun WealthPilotTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}