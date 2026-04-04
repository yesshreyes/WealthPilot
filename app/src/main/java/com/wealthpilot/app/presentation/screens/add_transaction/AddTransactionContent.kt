package com.wealthpilot.app.presentation.screens.add_transaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wealthpilot.app.domain.model.TransactionType

@Composable
fun AddTransactionContent(
    state: AddTransactionUiState,
    onAmountChange: (String) -> Unit,
    onCategoryChange: (String) -> Unit,
    onTypeChange: (TransactionType) -> Unit,
    onNotesChange: (String) -> Unit,
    onSave: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        OutlinedTextField(
            value = state.amount,
            onValueChange = onAmountChange,
            label = { Text("Amount") }
        )

        OutlinedTextField(
            value = state.category,
            onValueChange = onCategoryChange,
            label = { Text("Category") }
        )

        Row {
            Button(onClick = { onTypeChange(TransactionType.EXPENSE) }) {
                Text("Expense")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { onTypeChange(TransactionType.INCOME) }) {
                Text("Income")
            }
        }

        OutlinedTextField(
            value = state.notes,
            onValueChange = onNotesChange,
            label = { Text("Notes") }
        )

        Button(
            onClick = onSave,
            enabled = state.isValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Transaction")
        }
    }
}