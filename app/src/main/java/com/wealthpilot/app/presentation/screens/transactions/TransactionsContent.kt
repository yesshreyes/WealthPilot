package com.wealthpilot.app.presentation.screens.transactions

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wealthpilot.app.domain.model.Transaction
import com.wealthpilot.app.domain.model.TransactionType
import com.wealthpilot.app.ui.theme.WealthPilotTheme
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
    var currentFilter by remember { mutableStateOf<TransactionType?>(null) }

    val colors = MaterialTheme.colorScheme

    // Filter button states
    val allSelected = currentFilter == null
    val incomeSelected = currentFilter == TransactionType.INCOME
    val expenseSelected = currentFilter == TransactionType.EXPENSE

    Scaffold(
        containerColor = colors.background,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            "Transactions",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                navigationIcon = {
                    Surface(
                        shape = CircleShape,
                        color = colors.surface,
                        tonalElevation = 4.dp,
                        shadowElevation = 4.dp,
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        IconButton(onClick = onNavigateBack) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = colors.onSurface
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = colors.onBackground
                )
            )
        }
    ) { paddingValues ->
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colors.background)
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = colors.primary)
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.background)
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Search Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .shadow(
                        elevation = 6.dp,
                        shape = RoundedCornerShape(20.dp),
                        ambientColor = colors.primary.copy(alpha = 0.1f),
                        spotColor = Color.Black.copy(alpha = 0.06f)
                    ),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colors.surface
                ),
                border = BorderStroke(
                    1.dp,
                    Brush.linearGradient(
                        listOf(
                            colors.outline.copy(alpha = 0.3f),
                            colors.outline.copy(alpha = 0.1f)
                        )
                    )
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                OutlinedTextField(
                    value = state.searchQuery,
                    onValueChange = onSearchChange,
                    label = { Text("Search by notes or category") },
                    leadingIcon = {
                        Icon(Icons.Default.Search, null, tint = colors.primary)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colors.primary,
                        focusedLabelColor = colors.primary,
                        unfocusedBorderColor = colors.outline.copy(alpha = 0.3f)
                    ),
                    singleLine = true
                )
            }

            // Filter Chips Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 6.dp,
                        shape = RoundedCornerShape(20.dp),
                        ambientColor = colors.primary.copy(alpha = 0.1f),
                        spotColor = Color.Black.copy(alpha = 0.06f)
                    ),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colors.surface
                ),
                border = BorderStroke(
                    1.dp,
                    Brush.linearGradient(
                        listOf(
                            colors.outline.copy(alpha = 0.3f),
                            colors.outline.copy(alpha = 0.1f)
                        )
                    )
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    FilterChip(
                        text = "All",
                        selected = allSelected,
                        colors = colors,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            currentFilter = null
                            onTypeFilterChange(null)
                        }
                    )
                    FilterChip(
                        text = "Income",
                        selected = incomeSelected,
                        colors = colors,
                        isIncome = true,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            currentFilter = TransactionType.INCOME
                            onTypeFilterChange(TransactionType.INCOME)
                        }
                    )
                    FilterChip(
                        text = "Expense",
                        selected = expenseSelected,
                        colors = colors,
                        isExpense = true,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            currentFilter = TransactionType.EXPENSE
                            onTypeFilterChange(TransactionType.EXPENSE)
                        }
                    )
                }
            }

            // Results count
            Text(
                text = "${state.filteredTransactions.size} transactions found",
                style = MaterialTheme.typography.bodyMedium,
                color = colors.onBackground.copy(alpha = 0.6f),
                modifier = Modifier.padding(horizontal = 4.dp)
            )

            if (state.filteredTransactions.isEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .shadow(
                            elevation = 4.dp,
                            shape = RoundedCornerShape(24.dp),
                            ambientColor = colors.primary.copy(alpha = 0.08f),
                            spotColor = Color.Black.copy(alpha = 0.05f)
                        ),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = colors.surface
                    ),
                    border = BorderStroke(
                        1.dp,
                        Brush.linearGradient(
                            listOf(
                                colors.outline.copy(alpha = 0.2f),
                                colors.outline.copy(alpha = 0.05f)
                            )
                        )
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = colors.onSurface.copy(alpha = 0.1f),
                                modifier = Modifier.size(64.dp)
                            ) {
                                Icon(
                                    Icons.Default.Search,
                                    null,
                                    tint = colors.onSurface.copy(alpha = 0.4f),
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                            Text(
                                text = "No transactions found",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Medium,
                                color = colors.onSurface
                            )
                            Text(
                                text = "Try adjusting your search or filters",
                                style = MaterialTheme.typography.bodyMedium,
                                color = colors.onSurface.copy(alpha = 0.6f)
                            )
                        }
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(
                        items = state.filteredTransactions,
                        key = { it.id ?: it.hashCode() }
                    ) { transaction ->
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

            // Detail Dialog
            selectedTransaction?.let { txn ->
                val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                val isExpense = txn.type == TransactionType.EXPENSE
                val amountColor = if (isExpense) colors.error else colors.primary
                val sign = if (isExpense) "-" else "+"

                AlertDialog(
                    onDismissRequest = { selectedTransaction = null },
                    containerColor = colors.surface,
                    shape = RoundedCornerShape(24.dp),
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = amountColor.copy(alpha = 0.15f),
                                modifier = Modifier.size(44.dp)
                            ) {
                                Text(
                                    txn.category.take(1).uppercase(),
                                    modifier = Modifier.padding(12.dp),
                                    color = amountColor,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Column {
                                Text(
                                    "Transaction Details",
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    txn.category,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = colors.onSurface.copy(alpha = 0.6f)
                                )
                            }
                        }
                    },
                    text = {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = amountColor.copy(alpha = 0.1f)
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        "Amount",
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = colors.onSurface.copy(alpha = 0.7f)
                                    )
                                    Text(
                                        "$sign₹${txn.amount.toInt()}",
                                        style = MaterialTheme.typography.headlineSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = amountColor
                                    )
                                }
                            }

                            DetailRow(label = "Type", value = txn.type.name)
                            DetailRow(label = "Date", value = formatter.format(Date(txn.date)))
                            if (!txn.notes.isNullOrEmpty()) {
                                DetailRow(label = "Notes", value = txn.notes)
                            }
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                selectedTransaction = null
                                onEdit(txn)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colors.primary
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Edit")
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = { selectedTransaction = null },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colors.surfaceVariant
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Close")
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun FilterChip(
    text: String,
    selected: Boolean,
    colors: androidx.compose.material3.ColorScheme,
    isIncome: Boolean = false,
    isExpense: Boolean = false,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val backgroundColor = when {
        selected && isIncome -> colors.primary
        selected && isExpense -> colors.error
        selected -> colors.primary
        else -> colors.surfaceVariant.copy(alpha = 0.5f)
    }

    val contentColor = when {
        selected -> Color.White
        isIncome -> colors.primary
        isExpense -> colors.error
        else -> colors.onSurface.copy(alpha = 0.7f)
    }

    Button(
        onClick = onClick,
        modifier = modifier.height(44.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = if (selected) ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 2.dp
        ) else ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp
        )
    ) {
        Text(
            text = text,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    val colors = MaterialTheme.colorScheme

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = colors.onSurface.copy(alpha = 0.6f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            color = colors.onSurface
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionsContentPreview() {

    val dummyTransactions = listOf(
        Transaction(
            id = "1",
            amount = 500.0,
            category = "Food",
            date = System.currentTimeMillis(),
            notes = "Lunch",
            type = TransactionType.EXPENSE
        ),
        Transaction(
            id = "2",
            amount = 2000.0,
            category = "Salary",
            date = System.currentTimeMillis(),
            notes = "Monthly salary",
            type = TransactionType.INCOME
        )
    )

    val dummyState = TransactionsUiState(
        isLoading = false,
        searchQuery = "",
        filteredTransactions = dummyTransactions
    )

    WealthPilotTheme() {
        TransactionsContent(
            state = dummyState,
            onDelete = {},
            onEdit = {},
            onSearchChange = {},
            onTypeFilterChange = {},
            onNavigateBack = {}
        )
    }
}