package com.wealthpilot.app.presentation.screens.add_transaction

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wealthpilot.app.domain.model.TransactionType
import com.wealthpilot.app.ui.theme.WealthPilotTheme
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
    val colors = MaterialTheme.colorScheme

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
            .background(colors.background)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = colors.primary.copy(alpha = 0.15f),
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    Icons.Default.Add,
                    null,
                    tint = colors.primary,
                    modifier = Modifier.padding(12.dp)
                )
            }
            Column {
                Text(
                    text = "Add Transaction",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = colors.onBackground
                )
                Text(
                    text = "Record your expense or income",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.onBackground.copy(alpha = 0.6f)
                )
            }
        }

        // Type Selector
        GlassCard(colors = colors) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Transaction Type",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.onSurface
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    TypeButton(
                        text = "Expense",
                        selected = state.type == TransactionType.EXPENSE,
                        colors = colors,
                        modifier = Modifier.weight(1f),
                        onClick = { onTypeChange(TransactionType.EXPENSE) }
                    )
                    TypeButton(
                        text = "Income",
                        selected = state.type == TransactionType.INCOME,
                        colors = colors,
                        modifier = Modifier.weight(1f),
                        onClick = { onTypeChange(TransactionType.INCOME) }
                    )
                }
            }
        }

        // Details Card
        GlassCard(colors = colors) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Details",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.onSurface
                )

                // Amount Field
                OutlinedTextField(
                    value = state.amount,
                    onValueChange = onAmountChange,
                    label = { Text("Amount (₹)") },
                    leadingIcon = {
                        Icon(Icons.Default.Payments, null, tint = colors.primary)
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colors.primary,
                        focusedLabelColor = colors.primary
                    )
                )

                // Category Field
                OutlinedTextField(
                    value = state.category,
                    onValueChange = onCategoryChange,
                    label = { Text("Category") },
                    leadingIcon = {
                        Icon(Icons.Default.Category, null, tint = colors.tertiary)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colors.tertiary,
                        focusedLabelColor = colors.tertiary
                    )
                )

                // Date Field
                OutlinedTextField(
                    value = formatter.format(java.util.Date(state.date)),
                    onValueChange = {},
                    label = { Text("Date") },
                    leadingIcon = {
                        Icon(Icons.Default.CalendarToday, null, tint = colors.secondary)
                    },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colors.secondary,
                        focusedLabelColor = colors.secondary
                    )
                )

                Button(
                    onClick = { datePicker.show() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colors.secondary.copy(alpha = 0.15f),
                        contentColor = colors.secondary
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.CalendarToday, null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.size(8.dp))
                    Text("Change Date")
                }

                // Notes Field
                OutlinedTextField(
                    value = state.notes,
                    onValueChange = onNotesChange,
                    label = { Text("Notes (Optional)") },
                    leadingIcon = {
                        Icon(Icons.Default.Description, null, tint = colors.onSurface.copy(alpha = 0.5f))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    minLines = 3,
                    maxLines = 5
                )
            }
        }

        // Save Button
        Button(
            onClick = onSave,
            enabled = state.isValid,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .shadow(
                    elevation = if (state.isValid) 8.dp else 0.dp,
                    shape = RoundedCornerShape(16.dp)
                ),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (state.type == TransactionType.EXPENSE) colors.error else colors.primary,
                disabledContainerColor = colors.onSurface.copy(alpha = 0.1f)
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "Save ${if (state.type == TransactionType.EXPENSE) "Expense" else "Income"}",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun TypeButton(
    text: String,
    selected: Boolean,
    colors: androidx.compose.material3.ColorScheme,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val isExpense = text == "Expense"
    val activeColor = if (isExpense) colors.error else colors.primary

    Button(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) activeColor else colors.surfaceVariant.copy(alpha = 0.5f),
            contentColor = if (selected) Color.White else colors.onSurface.copy(alpha = 0.7f)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = text,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}

@Composable
private fun GlassCard(
    colors: androidx.compose.material3.ColorScheme,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(24.dp),
                ambientColor = colors.primary.copy(alpha = 0.15f),
                spotColor = colors.primary.copy(alpha = 0.1f)
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = colors.surface.copy(alpha = 0.6f)
        )
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun AddTransactionContentPreview() {
    WealthPilotTheme {
        AddTransactionContent(
            state = AddTransactionUiState(
                amount = "500",
                category = "Food",
                type = TransactionType.EXPENSE,
                notes = "Lunch with colleagues",
                isValid = true
            ),
            onAmountChange = {},
            onCategoryChange = {},
            onTypeChange = {},
            onNotesChange = {},
            onSave = {},
            onDateChange = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddTransactionIncomePreview() {
    WealthPilotTheme {
        AddTransactionContent(
            state = AddTransactionUiState(
                amount = "25000",
                category = "Salary",
                type = TransactionType.INCOME,
                notes = "Monthly salary",
                isValid = true
            ),
            onAmountChange = {},
            onCategoryChange = {},
            onTypeChange = {},
            onNotesChange = {},
            onSave = {},
            onDateChange = {}
        )
    }
}