package com.wealthpilot.app.presentation.screens.add_transaction

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wealthpilot.app.domain.model.TransactionType
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun AddTransactionContent(
    state: AddTransactionUiState,
    onAmountChange: (String) -> Unit,
    onCategoryChange: (String) -> Unit,
    onTypeChange: (TransactionType) -> Unit,
    onNotesChange: (String) -> Unit,
    onDateChange: (Long) -> Unit,
    onSave: () -> Unit
) {

    val context = LocalContext.current

    val formatter = remember {
        SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    }

    val calendar = Calendar.getInstance().apply {
        timeInMillis = state.date
    }

    val datePicker = DatePickerDialog(
        context,
        { _, year, month, day ->
            val cal = Calendar.getInstance()
            cal.set(year, month, day)
            onDateChange(cal.timeInMillis)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        OutlinedTextField(
            value = state.amount,
            onValueChange = onAmountChange,
            label = { Text("Amount") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.category,
            onValueChange = onCategoryChange,
            label = { Text("Category") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

            Button(
                onClick = { onTypeChange(TransactionType.EXPENSE) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (state.type == TransactionType.EXPENSE)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.surface
                )
            ) {
                Text(
                    text = "Expense",
                    color = if (state.type == TransactionType.EXPENSE)
                        MaterialTheme.colorScheme.onPrimary
                    else
                        MaterialTheme.colorScheme.onSurface
                )
            }

            Button(
                onClick = { onTypeChange(TransactionType.INCOME) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (state.type == TransactionType.INCOME)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.surface
                )
            ) {
                Text(
                    text = "Income",
                    color = if (state.type == TransactionType.INCOME)
                        MaterialTheme.colorScheme.onPrimary
                    else
                        MaterialTheme.colorScheme.onSurface
                )
            }
        }

        OutlinedTextField(
            value = formatter.format(java.util.Date(state.date)),
            onValueChange = {},
            label = { Text("Date") },
            readOnly = true,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { datePicker.show() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Select Date")
        }

        OutlinedTextField(
            value = state.notes,
            onValueChange = onNotesChange,
            label = { Text("Notes") },
            modifier = Modifier.fillMaxWidth()
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


@Preview(showBackground = true)
@Composable
fun AddTransactionContentPreview() {

    val state = AddTransactionUiState(
        amount = "500",
        category = "Food",
        type = TransactionType.EXPENSE,
        notes = "Lunch",
        isValid = true
    )

    AddTransactionContent(
        state = state,
        onAmountChange = {},
        onCategoryChange = {},
        onTypeChange = {},
        onNotesChange = {},
        onSave = {},
        onDateChange = {}
    )
}