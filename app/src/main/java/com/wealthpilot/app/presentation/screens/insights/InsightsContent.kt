package com.wealthpilot.app.presentation.screens.insights

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
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
                    .padding(16.dp),
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
            frequentCategory = "Groceries"
        )
    )
}