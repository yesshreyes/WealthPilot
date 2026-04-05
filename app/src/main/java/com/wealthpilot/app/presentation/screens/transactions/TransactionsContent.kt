package com.wealthpilot.app.presentation.screens.transactions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wealthpilot.app.domain.model.Transaction
import com.wealthpilot.app.domain.model.TransactionType
import com.wealthpilot.app.presentation.screens.home.components.TransactionItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionsContent(
    state: TransactionsUiState,
    onDelete: (Transaction) -> Unit,
    onEdit: (Transaction) -> Unit,
    onSearchChange: (String) -> Unit,
    onTypeFilterChange: (TransactionType?) -> Unit,
    onNavigateBack: () -> Unit
) {
    var selectedTransaction by remember { mutableStateOf<Transaction?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Transactions") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = state.searchQuery,
                onValueChange = onSearchChange,
                label = { Text("Search notes") },
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
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
                            onEdit = { onEdit(transaction) },
                            onClick = {
                                selectedTransaction = transaction
                            }
                        )
                    }
                }
            }

            selectedTransaction?.let { txn ->
                val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

                AlertDialog(
                    onDismissRequest = { selectedTransaction = null },
                    confirmButton = {
                        Button(onClick = {
                            selectedTransaction = null
                            onEdit(txn)
                        }) {
                            Text("Edit")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { selectedTransaction = null }) {
                            Text("Close")
                        }
                    },
                    title = {
                        Text("Transaction Details")
                    },
                    text = {
                        Column {
                            Text("Amount: ₹${txn.amount}")
                            Text("Category: ${txn.category}")
                            Text("Date: ${formatter.format(Date(txn.date))}")
                            if (!txn.notes.isNullOrEmpty()) {
                                Text("Notes: ${txn.notes}")
                            }
                        }
                    }
                )
            }
        }
    }
}
