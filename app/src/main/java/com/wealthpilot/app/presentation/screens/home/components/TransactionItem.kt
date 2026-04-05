package com.wealthpilot.app.presentation.screens.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wealthpilot.app.domain.model.Transaction
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionItem(
    transaction: Transaction,
    onDelete: () -> Unit,
    onEdit: () -> Unit,
    onClick: () -> Unit
) {

    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if (it == SwipeToDismissBoxValue.EndToStart) {
                onDelete()
                true
            } else false
        }
    )

    val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {},
        content = {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick() }
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(text = transaction.category)

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(text = "₹${transaction.amount}")

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(text = formatter.format(Date(transaction.date)))

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(onClick = onEdit) {
                        Text("Edit")
                    }
                }
            }
        }
    )
}