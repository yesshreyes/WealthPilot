package com.wealthpilot.app.presentation.screens.home.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun WeeklyTrendIndicator(trend: Double) {

    val text = when {
        trend > 0 -> "↑ Spending increased"
        trend < 0 -> "↓ Spending decreased"
        else -> "No change"
    }

    val color = when {
        trend > 0 -> MaterialTheme.colorScheme.error
        trend < 0 -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.onSurface
    }

    Text(
        text = text,
        color = color,
        style = MaterialTheme.typography.bodyMedium
    )
}