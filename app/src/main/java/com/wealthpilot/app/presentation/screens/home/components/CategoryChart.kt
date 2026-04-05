package com.wealthpilot.app.presentation.screens.home.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.wealthpilot.app.domain.model.CategoryData

@Composable
fun CategoryChart(data: List<CategoryData>) {
    val totalAmount = data.sumOf { it.amount }

    if (totalAmount <= 0.0 || data.isEmpty()) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Spending Breakdown", style = MaterialTheme.typography.titleMedium)
            Text("No spending data available.", color = Color.Gray)
        }
        return
    }

    val colorPalette = listOf(
        Color(0xFFE91E63), // Pink
        Color(0xFF9C27B0), // Purple
        Color(0xFF3F51B5), // Indigo
        Color(0xFF03A9F4), // Light Blue
        Color(0xFF009688), // Teal
        Color(0xFF4CAF50), // Green
        Color(0xFFFF9800), // Orange
        Color(0xFFFF5722)  // Deep Orange
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Categories Breakdown",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.align(Alignment.Start)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Canvas(
                modifier = Modifier
                    .size(240.dp)
                    .padding(16.dp)
            ) {
                val strokeWidth = 50f
                var currentStartAngle = 180f

                drawArc(
                    color = Color.LightGray.copy(alpha = 0.3f),
                    startAngle = 180f,
                    sweepAngle = 180f,
                    useCenter = false,
                    topLeft = Offset(strokeWidth / 2, strokeWidth / 2),
                    size = Size(size.width - strokeWidth, size.height - strokeWidth),
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
                )

                data.sortedByDescending { it.amount }.forEachIndexed { index, item ->
                    val sweepAngle = ((item.amount / totalAmount) * 180f).toFloat()
                    val arcColor = colorPalette[index % colorPalette.size]

                    val effectiveSweep = if (sweepAngle < 2f) 2f else sweepAngle

                    drawArc(
                        color = arcColor,
                        startAngle = currentStartAngle,
                        sweepAngle = effectiveSweep,
                        useCenter = false,
                        topLeft = Offset(strokeWidth / 2, strokeWidth / 2),
                        size = Size(size.width - strokeWidth, size.height - strokeWidth),
                        style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
                    )
                    currentStartAngle += effectiveSweep
                }
            }
            
            Text(
                text = "₹${totalAmount.toInt()}",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            data.sortedByDescending { it.amount }.forEachIndexed { index, item ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .background(colorPalette[index % colorPalette.size], CircleShape)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(item.category)
                    }
                    Text("₹${item.amount}")
                }
            }
        }
    }
}