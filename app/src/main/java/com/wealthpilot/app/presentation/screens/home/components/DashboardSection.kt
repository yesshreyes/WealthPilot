package com.wealthpilot.app.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wealthpilot.app.presentation.screens.home.components.SummaryCard

@Composable
fun DashboardSection(
    balance: Double,
    income: Double,
    expense: Double
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        SummaryCard(
            title = "Balance",
            amount = balance
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                SummaryCard("Income", income)
            }
            Box(modifier = Modifier.weight(1f)) {
                SummaryCard("Expense", expense)
            }
        }
    }
}