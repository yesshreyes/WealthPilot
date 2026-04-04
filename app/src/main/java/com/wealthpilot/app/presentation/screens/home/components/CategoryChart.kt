package com.wealthpilot.app.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.wealthpilot.app.presentation.screens.home.components.CategoryData

@Composable
fun CategoryChart(data: List<CategoryData>) {

    val max = data.maxOfOrNull { it.amount } ?: 1.0

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Text(
            text = "Spending Breakdown",
            style = MaterialTheme.typography.titleMedium
        )

        data.forEach { item ->

            val ratio = (item.amount / max).toFloat()

            Column {
                Text("${item.category} - ₹${item.amount}")

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(12.dp)
                        .background(Color.LightGray)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(ratio)
                            .height(12.dp)
                            .background(MaterialTheme.colorScheme.primary)
                    )
                }
            }
        }
    }
}