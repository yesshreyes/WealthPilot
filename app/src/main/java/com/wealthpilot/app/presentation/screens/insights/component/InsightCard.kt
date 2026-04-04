package com.wealthpilot.app.presentation.screens.insights.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun InsightCard(
    title: String,
    value: String
) {
    Card {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}