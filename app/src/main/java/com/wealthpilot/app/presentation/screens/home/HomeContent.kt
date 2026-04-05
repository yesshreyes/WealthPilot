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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HomeContent(
    state: HomeUiState,
    padding: PaddingValues,
    onViewTransactions: () -> Unit
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

        Button(
            onClick = onViewTransactions,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("View Transactions")
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
        onViewTransactions = {}
    )
}