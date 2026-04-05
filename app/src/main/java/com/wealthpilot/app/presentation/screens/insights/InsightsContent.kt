package com.wealthpilot.app.presentation.screens.insights

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wealthpilot.app.presentation.screens.insights.component.InsightCard

@Composable
fun InsightsContent(
    state: InsightsUiState
) {

    when {
        state.isLoading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        }

        else -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(androidx.compose.foundation.rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Text(
                    text = "Insights",
                    style = MaterialTheme.typography.titleLarge
                )

                InsightCard(
                    title = "Highest Spending",
                    value = "${state.highestCategory} - ₹${state.highestAmount}"
                )

                InsightCard(
                    title = "Monthly Total",
                    value = "₹${state.monthlyTotal}"
                )

                InsightCard(
                    title = "Most Frequent Category",
                    value = state.frequentCategory
                )

                if (state.categoryBreakdown.isNotEmpty()) {
                    Text(
                        text = "Category Breakdown",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                    )

                    androidx.compose.material3.Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        tonalElevation = 2.dp
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Category",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = "Amount",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }

                            Divider(
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f)
                            )

                            state.categoryBreakdown.forEachIndexed { index, item ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = item.category,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Text(
                                        text = "₹${item.amount.toLong()}",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                                if (index < state.categoryBreakdown.lastIndex) {
                                    Divider(
                                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f),
                                        modifier = Modifier.padding(horizontal = 16.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InsightsContentPreview() {
    InsightsContent(
        state = InsightsUiState(
            isLoading = false,
            highestCategory = "Food",
            highestAmount = 3200.0,
            monthlyTotal = 12000.0,
            frequentCategory = "Groceries",
            categoryBreakdown = listOf(
                com.wealthpilot.app.domain.model.CategoryData("Food", 3200.0),
                com.wealthpilot.app.domain.model.CategoryData("Transport", 1500.0),
                com.wealthpilot.app.domain.model.CategoryData("Utilities", 800.0)
            )
        )
    )
}