package com.wealthpilot.app.presentation.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wealthpilot.app.domain.model.Transaction

@Composable
fun TransactionItem(
    transaction: Transaction,
    onDelete: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = transaction.category,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "₹${transaction.amount}",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = transaction.type.name
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = onDelete) {
                Text("Delete")
            }
        }
    }
}