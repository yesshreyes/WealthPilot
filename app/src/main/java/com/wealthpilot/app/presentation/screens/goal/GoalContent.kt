package com.wealthpilot.app.presentation.screens.goal

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wealthpilot.app.ui.theme.WealthPilotTheme

@Composable
fun GoalContent(
    state: GoalUiState,
    onGoalChange: (String) -> Unit,
    onSetGoal: () -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val remaining = (state.goalAmount.toDoubleOrNull() ?: 0.0) - state.spentAmount
    val progressPercent = (state.progress * 100).toInt()

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
                    Icons.Default.Flag,
                    null,
                    tint = colors.primary,
                    modifier = Modifier.padding(12.dp)
                )
            }
            Column {
                Text(
                    text = "Spending Goal",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = colors.onBackground
                )
                Text(
                    text = "Set your monthly limit",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.onBackground.copy(alpha = 0.6f)
                )
            }
        }

        // Input Card
        GlassCard(colors = colors) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = state.goalAmount,
                    onValueChange = onGoalChange,
                    label = { Text("Goal Amount (₹)") },
                    leadingIcon = {
                        Icon(Icons.Default.Savings, null, tint = colors.primary)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colors.primary,
                        focusedLabelColor = colors.primary
                    )
                )

                Button(
                    onClick = onSetGoal,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .shadow(8.dp, RoundedCornerShape(16.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colors.primary
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        "Set Goal",
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        // Progress Card (only show if goal is set)
        if (state.isGoalSet) {
            GlassCard(colors = colors) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    // Stats Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        StatItem(
                            label = "Spent",
                            value = "₹${state.spentAmount.toInt()}",
                            color = colors.error,
                            colors = colors
                        )
                        StatItem(
                            label = "Remaining",
                            value = "₹${remaining.coerceAtLeast(0.0).toInt()}",
                            color = colors.tertiary,
                            colors = colors
                        )
                        StatItem(
                            label = "Goal",
                            value = "₹${state.goalAmount}",
                            color = colors.primary,
                            colors = colors
                        )
                    }

                    // Progress Bar
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        LinearProgressIndicator(
                            progress = { state.progress.coerceIn(0f, 1f) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(12.dp),
                            color = when {
                                state.progress >= 1f -> colors.error
                                state.progress >= 0.75f -> colors.tertiary
                                else -> colors.primary
                            },
                            trackColor = colors.surfaceVariant.copy(alpha = 0.5f),
                            strokeCap = StrokeCap.Round,
                            gapSize = 0.dp
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "$progressPercent% used",
                                style = MaterialTheme.typography.bodyMedium,
                                color = colors.onSurface.copy(alpha = 0.7f)
                            )
                            Text(
                                text = if (remaining >= 0) "On track" else "Over budget",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium,
                                color = if (remaining >= 0) colors.primary else colors.error
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun StatItem(
    label: String,
    value: String,
    color: Color,
    colors: androidx.compose.material3.ColorScheme
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = colors.onSurface.copy(alpha = 0.6f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = color
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
fun GoalContentPreview() {
    WealthPilotTheme {
        GoalContent(
            state = GoalUiState(
                goalAmount = "10000",
                spentAmount = 4500.0,
                progress = 0.45f,
                isGoalSet = true
            ),
            onGoalChange = {},
            onSetGoal = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GoalContentEmptyPreview() {
    WealthPilotTheme {
        GoalContent(
            state = GoalUiState(
                goalAmount = "",
                spentAmount = 0.0,
                progress = 0f,
                isGoalSet = false
            ),
            onGoalChange = {},
            onSetGoal = {}
        )
    }
}