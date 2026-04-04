package com.wealthpilot.app.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
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

@Composable
fun HomeContent(
    state: HomeUiState,
    onAddClick: () -> Unit,
    onDelete: (Transaction) -> Unit,
    onEdit: (Transaction) -> Unit
) {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Text("+")
            }
        }
    ) { padding ->

        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.transactions.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No transactions yet",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        SummaryCard(
                            title = "Balance",
                            amount = state.balance
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            Box(modifier = Modifier.weight(1f)) {
                                SummaryCard(
                                    title = "Income",
                                    amount = state.totalIncome
                                )
                            }

                            Box(modifier = Modifier.weight(1f)) {
                                SummaryCard(
                                    title = "Expense",
                                    amount = state.totalExpense
                                )
                            }
                        }
                    }
                    CategoryChart(state.categoryData)
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(state.transactions) { transaction ->
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
        onAddClick = {},
        onDelete = {},
        onEdit = {}
    )
}