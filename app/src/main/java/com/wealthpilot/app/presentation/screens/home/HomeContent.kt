package com.wealthpilot.app.presentation.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wealthpilot.app.domain.model.CategoryData
import com.wealthpilot.app.domain.model.Transaction
import com.wealthpilot.app.domain.model.TransactionType
import com.wealthpilot.app.presentation.screens.home.components.CategoryChart
import com.wealthpilot.app.presentation.screens.home.components.SummaryCard
import com.wealthpilot.app.ui.theme.WealthPilotTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    state: HomeUiState,
    onViewTransactions: () -> Unit,
    onNavigateSettings: () -> Unit,
    onAddTransaction: () -> Unit = {}
) {
    val colors = MaterialTheme.colorScheme

    // Nice gradient border for button
    val buttonBorderBrush = Brush.linearGradient(
        colors = listOf(
            colors.primary.copy(alpha = 0.5f),
            colors.tertiary.copy(alpha = 0.3f)
        )
    )

    Scaffold(
        containerColor = colors.background,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = colors.primary.copy(alpha = 0.15f),
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                Icons.Default.AccountBalance,
                                null,
                                tint = colors.primary,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                        Text(
                            "WealthPilot",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                actions = {
                    Surface(
                        shape = CircleShape,
                        color = colors.surface,
                        tonalElevation = 4.dp,
                        shadowElevation = 4.dp,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        IconButton(onClick = onNavigateSettings) {
                            Icon(
                                Icons.Default.Settings,
                                contentDescription = "Settings",
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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddTransaction,
                containerColor = colors.primary,
                contentColor = colors.onPrimary,
                shape = CircleShape,
                elevation = androidx.compose.material3.FloatingActionButtonDefaults.elevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 12.dp
                ),
                modifier = Modifier.shadow(
                    elevation = 12.dp,
                    shape = CircleShape,
                    spotColor = colors.primary.copy(alpha = 0.4f)
                )
            ) {
                Icon(Icons.Default.Add, "Add Transaction")
            }
        },
    ) { innerPadding ->
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colors.background)
                    .padding(innerPadding),
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
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Column {
                Text(
                    text = "Overview",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = colors.onBackground
                )
                Text(
                    text = "Track your financial health",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.onBackground.copy(alpha = 0.6f)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Unified Dashboard Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 16.dp,
                        shape = RoundedCornerShape(32.dp),
                        ambientColor = colors.primary.copy(alpha = 0.2f),
                        spotColor = colors.primary.copy(alpha = 0.15f)
                    ),
                shape = RoundedCornerShape(32.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colors.surface.copy(alpha = 0.8f)
                ),
                border = BorderStroke(
                    width = 1.5.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            colors.primary.copy(alpha = 0.3f),
                            colors.tertiary.copy(alpha = 0.05f)
                        )
                    )
                )
            ) {
                Column(
                    modifier = Modifier.padding(28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Total Balance",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium,
                        color = colors.onSurface.copy(alpha = 0.6f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "₹${state.balance.toInt()}",
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.Bold,
                        color = colors.onSurface
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Income column
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = colors.primary.copy(alpha = 0.15f),
                                modifier = Modifier.size(46.dp)
                            ) {
                                Icon(
                                    Icons.Default.ArrowDownward,
                                    null,
                                    tint = colors.primary,
                                    modifier = Modifier.padding(10.dp)
                                )
                            }
                            Column {
                                Text(
                                    "Income",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = colors.onSurface.copy(alpha = 0.5f)
                                )
                                Text(
                                    "₹${state.totalIncome.toInt()}",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = colors.primary
                                )
                            }
                        }

                        // Divider
                        Box(
                            modifier = Modifier
                                .height(46.dp)
                                .width(1.dp)
                                .background(colors.onSurface.copy(alpha = 0.1f))
                        )

                        // Expense column
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    "Expense",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = colors.onSurface.copy(alpha = 0.5f)
                                )
                                Text(
                                    "₹${state.totalExpense.toInt()}",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = colors.error
                                )
                            }
                            Surface(
                                shape = CircleShape,
                                color = colors.error.copy(alpha = 0.15f),
                                modifier = Modifier.size(46.dp)
                            ) {
                                Icon(
                                    Icons.Default.ArrowUpward,
                                    null,
                                    tint = colors.error,
                                    modifier = Modifier.padding(10.dp)
                                )
                            }
                        }
                    }
                }
            }

            CategoryChart(state.categoryData)

            // Nice elevated button card
            Card(
                onClick = onViewTransactions,
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(16.dp),
                        ambientColor = colors.primary.copy(alpha = 0.15f),
                        spotColor = Color.Black.copy(alpha = 0.1f)
                    ),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colors.surface
                ),
                border = BorderStroke(1.5.dp, buttonBorderBrush),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 18.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "View All Transactions",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = colors.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeContentPreview() {
    WealthPilotTheme {
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
                CategoryData("Transport", 300.0),
                CategoryData("Utilities", 800.0)
            ),
            isLoading = false
        )

        HomeContent(
            state = state,
            onViewTransactions = {},
            onNavigateSettings = {}
        )
    }
}