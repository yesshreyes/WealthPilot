package com.wealthpilot.app.presentation.screens.goal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GoalContent(
    state: GoalUiState,
    onGoalChange: (String) -> Unit,
    onSetGoal: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Monthly Spending Goal",
            style = MaterialTheme.typography.titleLarge
        )

        OutlinedTextField(
            value = state.goalAmount,
            onValueChange = onGoalChange,
            label = { Text("Enter Goal Amount") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = onSetGoal,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Set Goal")
        }

        if (state.isGoalSet) {

            Text("Spent: ₹${state.spentAmount}")

            val remaining = (state.goalAmount.toDoubleOrNull() ?: 0.0) - state.spentAmount

            Text("Remaining: ₹${remaining.coerceAtLeast(0.0)}")

            LinearProgressIndicator(
                progress = state.progress,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "${(state.progress * 100).toInt()}% completed",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}