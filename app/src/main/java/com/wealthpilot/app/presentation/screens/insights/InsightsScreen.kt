package com.wealthpilot.app.presentation.screens.insights

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wealthpilot.app.data.repository.RepositoryProvider
import com.wealthpilot.app.presentation.screens.home.HomeViewModel
import com.wealthpilot.app.presentation.screens.home.HomeViewModelFactory

@Composable
fun InsightsScreen() {

    val context = LocalContext.current

    val viewModel: HomeViewModel = viewModel(
        factory = HomeViewModelFactory(
            RepositoryProvider.provideTransactionRepository(context)
        )
    )

    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text("Insights", style = MaterialTheme.typography.titleLarge)

        Card {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Total Income")
                Text("₹${state.totalIncome}")
            }
        }

        Card {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Total Expense")
                Text("₹${state.totalExpense}")
            }
        }

        Card {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Balance")
                Text("₹${state.balance}")
            }
        }
    }
}