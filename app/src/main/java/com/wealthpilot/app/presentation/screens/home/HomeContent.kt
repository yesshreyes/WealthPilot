package com.wealthpilot.app.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wealthpilot.app.domain.model.Transaction
import com.wealthpilot.app.domain.model.TransactionType
import com.wealthpilot.app.presentation.screens.home.components.CategoryData
import com.wealthpilot.app.presentation.screens.home.components.SummaryCard
import com.wealthpilot.app.presentation.screens.home.components.TransactionItem
import com.wealthpilot.app.presentation.screens.home.components.WeeklyTrendIndicator

@Composable
fun HomeContent(
    state: HomeUiState,
    padding: PaddingValues,
    onDelete: (Transaction) -> Unit,
    onEdit: (Transaction) -> Unit,
    onSearchChange: (String) -> Unit,
    onTypeFilterChange: (TransactionType?) -> Unit
) {

    if (state.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
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

        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = onSearchChange,
            label = { Text("Search notes") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Button(onClick = { onTypeFilterChange(null) }) {
                Text("All")
            }

            Button(onClick = { onTypeFilterChange(TransactionType.INCOME) }) {
                Text("Income")
            }

            Button(onClick = { onTypeFilterChange(TransactionType.EXPENSE) }) {
                Text("Expense")
            }
        }

        if (state.filteredTransactions.isEmpty()) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No results found",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

        } else {

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.filteredTransactions) { transaction ->
                    TransactionItem(
                        transaction = transaction,
                        onDelete = { onDelete(transaction) },
                        onEdit = { onEdit(transaction) }
                    )
                }
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
        onDelete = {},
        onEdit = {},
        onSearchChange = {},
        onTypeFilterChange = {}
    )
}