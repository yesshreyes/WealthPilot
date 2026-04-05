package com.wealthpilot.app.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
data class AddTransaction(
    val transactionId: String? = null
)

@Serializable
object Transactions

@Serializable
object Insights

@Serializable
object Goal

@Serializable
object Settings