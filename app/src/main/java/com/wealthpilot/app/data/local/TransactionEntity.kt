package com.wealthpilot.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wealthpilot.app.domain.model.TransactionType

@Entity(tableName = "transactions")
data class TransactionEntity(

    @PrimaryKey
    val id: String,

    val amount: Double,
    val category: String,
    val type: TransactionType,
    val date: Long,
    val notes: String?
)