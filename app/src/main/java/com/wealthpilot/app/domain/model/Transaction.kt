package com.wealthpilot.app.domain.model

import java.util.UUID

data class Transaction(
    val id: String = UUID.randomUUID().toString(),
    val amount: Double,
    val category: String,
    val type: TransactionType,
    val date: Long,
    val notes: String? = null
)

enum class TransactionType {
    INCOME,
    EXPENSE
}