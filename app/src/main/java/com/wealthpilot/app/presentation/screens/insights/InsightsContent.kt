package com.wealthpilot.app.presentation.screens.insights

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wealthpilot.app.domain.model.CategoryData
import com.wealthpilot.app.presentation.screens.insights.component.InsightCard
import com.wealthpilot.app.ui.theme.WealthPilotTheme

@Composable
fun InsightsContent(
    state: InsightsUiState
) {
    val colors = MaterialTheme.colorScheme

    when {
        state.isLoading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colors.background),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = colors.primary)
            }
        }

        else -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colors.background)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = colors.primary.copy(alpha = 0.15f),
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            Icons.Default.Insights,
                            null,
                            tint = colors.primary,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                    Column {
                        Text(
                            text = "Insights",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = colors.onBackground
                        )
                        Text(
                            text = "Your spending analysis",
                            style = MaterialTheme.typography.bodyMedium,
                            color = colors.onBackground.copy(alpha = 0.6f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Key Stats Cards
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    InsightCard(
                        title = "Highest Spending",
                        value = "₹${state.highestAmount.toInt()}",
                        subtitle = state.highestCategory,
                        icon = Icons.Default.BarChart,
                        modifier = Modifier.weight(1f),
                        colors = colors
                    )
                    InsightCard(
                        title = "Monthly Total",
                        value = "₹${state.monthlyTotal.toInt()}",
                        subtitle = "This month",
                        icon = Icons.Default.Insights,
                        modifier = Modifier.weight(1f),
                        colors = colors
                    )
                }

                InsightCard(
                    title = "Most Frequent",
                    value = state.frequentCategory,
                    subtitle = "Top category",
                    icon = Icons.Default.BarChart,
                    colors = colors,
                    fullWidth = true
                )

                // Category Breakdown
                if (state.categoryBreakdown.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Category Breakdown",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = colors.onBackground,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    GlassCard(colors = colors) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            // Header Row
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp, vertical = 16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Category",
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.Medium,
                                    color = colors.primary
                                )
                                Text(
                                    text = "Amount",
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.Medium,
                                    color = colors.primary
                                )
                            }

                            Divider(
                                color = colors.onSurface.copy(alpha = 0.1f),
                                modifier = Modifier.padding(horizontal = 20.dp)
                            )

                            // Category Rows with progress bars
                            val totalAmount = state.categoryBreakdown.sumOf { it.amount }.coerceAtLeast(1.0)

                            state.categoryBreakdown.forEachIndexed { index, item ->
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 20.dp, vertical = 16.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = item.category,
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = colors.onSurface
                                        )
                                        Text(
                                            text = "₹${item.amount.toInt()}",
                                            style = MaterialTheme.typography.bodyLarge,
                                            fontWeight = FontWeight.SemiBold,
                                            color = colors.onSurface
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))

                                    LinearProgressIndicator(
                                        progress = { (item.amount / totalAmount).toFloat().coerceIn(0f, 1f) },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(6.dp),
                                        color = when (index % 3) {
                                            0 -> colors.primary
                                            1 -> colors.tertiary
                                            else -> colors.secondary
                                        },
                                        trackColor = colors.surfaceVariant.copy(alpha = 0.3f),
                                        strokeCap = StrokeCap.Round,
                                        gapSize = 0.dp
                                    )
                                }

                                if (index < state.categoryBreakdown.lastIndex) {
                                    Divider(
                                        color = colors.onSurface.copy(alpha = 0.05f),
                                        modifier = Modifier.padding(horizontal = 20.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
private fun GlassCard(
    colors: ColorScheme,
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
fun InsightsContentPreview() {
    WealthPilotTheme {
        InsightsContent(
            state = InsightsUiState(
                isLoading = false,
                highestCategory = "Food",
                highestAmount = 3200.0,
                monthlyTotal = 12000.0,
                frequentCategory = "Groceries",
                categoryBreakdown = listOf(
                    CategoryData("Food", 3200.0),
                    CategoryData("Transport", 1500.0),
                    CategoryData("Utilities", 800.0),
                    CategoryData("Entertainment", 2200.0)
                )
            )
        )
    }
}