package com.wealthpilot.app.presentation.screens.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SummaryCard(
    title: String,
    amount: Double,
    isIncome: Boolean = false,
    isExpense: Boolean = false,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme

    val amountColor = when {
        isIncome -> colors.primary
        isExpense -> colors.error
        else -> colors.onSurface
    }

    // Gradient border effect
    val borderBrush = Brush.linearGradient(
        colors = listOf(
            colors.primary.copy(alpha = 0.3f),
            colors.tertiary.copy(alpha = 0.1f)
        )
    )

    Card(
        modifier = modifier
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = colors.primary.copy(alpha = 0.15f),
                spotColor = Color.Black.copy(alpha = 0.1f)
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = colors.surface
        ),
        border = BorderStroke(
            width = 1.dp,
            brush = borderBrush
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp,
            pressedElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = colors.onSurface.copy(alpha = 0.6f)
            )
            Text(
                text = "₹${amount.toInt()}",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = amountColor
            )
        }
    }
}