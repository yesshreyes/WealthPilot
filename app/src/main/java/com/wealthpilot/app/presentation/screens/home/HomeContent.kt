package com.wealthpilot.app.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wealthpilot.app.domain.model.Transaction
import com.wealthpilot.app.domain.model.TransactionType
import com.wealthpilot.app.presentation.screens.home.components.SummaryCard
import com.wealthpilot.app.presentation.screens.home.components.WeeklyTrendIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import com.wealthpilot.app.domain.model.CategoryData
import com.wealthpilot.app.presentation.screens.home.components.CategoryChart

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    state: HomeUiState,
    padding: PaddingValues,
    onViewTransactions: () -> Unit,
    onNavigateSettings: () -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("WealthPilot") },
                actions = {
                    IconButton(onClick = onNavigateSettings) {
                        Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        },
        modifier = Modifier.padding(padding)
    ) { innerPadding ->
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            SummaryCard("Balance", state.balance)

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(modifier = Modifier.weight(1f)) {
                SummaryCard("Income", state.totalIncome)
            }
            Box(modifier = Modifier.weight(1f)) {
                SummaryCard("Expense", state.totalExpense)
            }
        }

        WeeklyTrendIndicator(state.weeklyTrend)

            CategoryChart(state.categoryData)

        Button(
            onClick = onViewTransactions,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("View Transactions")
        }
    }
}
}

@Preview(showBackground = true)
@Composable
fun HomeContentPreview() {

    val sampleTransactions = listOf(
        Transaction(
            amount = 500.0,
            category = "Food",
            type = TransactionType.EXPENSE,
            date = System.currentTimeMillis(),
            notes = "Lunch"
        ),
        Transaction(
            amount = 2000.0,
            category = "Salary",
            type = TransactionType.INCOME,
            date = System.currentTimeMillis(),
            notes = "Monthly"
        ),
        Transaction(
            amount = 300.0,
            category = "Transport",
            type = TransactionType.EXPENSE,
            date = System.currentTimeMillis(),
            notes = ""
        )
    )

    val state = HomeUiState(
        transactions = sampleTransactions,
        totalIncome = 2000.0,
        totalExpense = 800.0,
        balance = 1200.0,
        categoryData = listOf(
            CategoryData("Food", 500.0),
            CategoryData("Transport", 300.0)
        ),
        isLoading = false
    )

    HomeContent(
        state = state,
        padding = PaddingValues(0.dp),
        onViewTransactions = {},
        onNavigateSettings = {}
    )
}