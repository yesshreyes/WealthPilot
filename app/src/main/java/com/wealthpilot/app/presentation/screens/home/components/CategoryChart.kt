package com.wealthpilot.app.presentation.screens.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.wealthpilot.app.domain.model.CategoryData

@Composable
fun CategoryChart(data: List<CategoryData>) {
    val colors = MaterialTheme.colorScheme
    val totalAmount = data.sumOf { it.amount }

    // Nice gradient border
    val borderBrush = Brush.linearGradient(
        colors = listOf(
            colors.tertiary.copy(alpha = 0.4f),
            colors.primary.copy(alpha = 0.2f),
            colors.secondary.copy(alpha = 0.3f)
        )
    )

    if (totalAmount <= 0.0 || data.isEmpty()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(24.dp),
                    ambientColor = colors.tertiary.copy(alpha = 0.2f),
                    spotColor = Color.Black.copy(alpha = 0.08f)
                ),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = colors.surface
            ),
            border = BorderStroke(1.5.dp, borderBrush),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    "Spending Breakdown",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    "No spending data available.",
                    color = colors.onSurface.copy(alpha = 0.5f)
                )
            }
        }
        return
    }

    val chartColors = listOf(
        colors.primary,
        colors.tertiary,
        colors.secondary,
        colors.primary.copy(alpha = 0.8f),
        colors.tertiary.copy(alpha = 0.8f),
        colors.secondary.copy(alpha = 0.8f)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(24.dp),
                ambientColor = colors.primary.copy(alpha = 0.2f),
                spotColor = Color.Black.copy(alpha = 0.08f)
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = colors.surface
        ),
        border = BorderStroke(1.5.dp, borderBrush),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Categories",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Start)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    val strokeWidth = 40f
                    val chartSize = Size(size.width - strokeWidth, (size.height * 2) - strokeWidth)

                    drawArc(
                        color = colors.surfaceVariant.copy(alpha = 0.3f),
                        startAngle = 180f,
                        sweepAngle = 180f,
                        useCenter = false,
                        topLeft = Offset(strokeWidth / 2, strokeWidth / 2),
                        size = chartSize,
                        style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                    )

                    var currentStartAngle = 180f
                    data.sortedByDescending { it.amount }.forEachIndexed { index, item ->
                        val sweepAngle = ((item.amount / totalAmount) * 180f).toFloat()
                        val arcColor = chartColors[index % chartColors.size]

                        drawArc(
                            color = arcColor,
                            startAngle = currentStartAngle,
                            sweepAngle = sweepAngle.coerceAtLeast(2f),
                            useCenter = false,
                            topLeft = Offset(strokeWidth / 2, strokeWidth / 2),
                            size = chartSize,
                            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                        )
                        currentStartAngle += sweepAngle
                    }
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Text(
                        text = "Total",
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.onSurface.copy(alpha = 0.5f)
                    )
                    Text(
                        text = "₹${totalAmount.toInt()}",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = colors.primary
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                data.sortedByDescending { it.amount }.forEachIndexed { index, item ->
                    val percentage = ((item.amount / totalAmount) * 100).toInt()

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .background(chartColors[index % chartColors.size], CircleShape)
                            )
                            Text(
                                text = item.category,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "$percentage%",
                                style = MaterialTheme.typography.bodySmall,
                                color = colors.onSurface.copy(alpha = 0.6f)
                            )
                            Text(
                                text = "₹${item.amount.toInt()}",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}