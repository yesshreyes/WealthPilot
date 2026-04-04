package com.wealthpilot.app.data.mapper

import com.wealthpilot.app.data.local.TransactionEntity
import com.wealthpilot.app.domain.model.Transaction

fun TransactionEntity.toDomain(): Transaction {
    return Transaction(
        id = id,
        amount = amount,
        category = category,
        type = type,
        date = date,
        notes = notes
    )
}

fun Transaction.toEntity(): TransactionEntity {
    return TransactionEntity(
        id = id,
        amount = amount,
        category = category,
        type = type,
        date = date,
        notes = notes
    )
}