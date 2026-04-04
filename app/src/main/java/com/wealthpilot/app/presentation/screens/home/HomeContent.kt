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
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wealthpilot.app.domain.model.Transaction

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
                    Text("No transactions yet")
                }
            }

            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp)
                ) {

                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        Card {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("Balance")
                                Text("₹${state.balance}")
                            }
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                            Card(modifier = Modifier.weight(1f)) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text("Income")
                                    Text("₹${state.totalIncome}")
                                }
                            }

                            Card(modifier = Modifier.weight(1f)) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text("Expense")
                                    Text("₹${state.totalExpense}")
                                }
                            }
                        }
                    }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(top = 12.dp),
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