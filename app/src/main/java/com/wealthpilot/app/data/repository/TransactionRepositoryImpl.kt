package com.wealthpilot.app.data.repository

import com.wealthpilot.app.data.local.TransactionDao
import com.wealthpilot.app.data.mapper.toDomain
import com.wealthpilot.app.data.mapper.toEntity
import com.wealthpilot.app.domain.model.Transaction
import com.wealthpilot.app.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TransactionRepositoryImpl(
    private val dao: TransactionDao
) : TransactionRepository {

    override fun getAllTransactions(): Flow<List<Transaction>> {
        return dao.getAllTransactions()
            .map { list -> list.map { it.toDomain() } }
    }

    override suspend fun insertTransaction(transaction: Transaction) {
        dao.insert(transaction.toEntity())
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        dao.update(transaction.toEntity())
    }

    override suspend fun deleteTransaction(transaction: Transaction) {
        dao.delete(transaction.toEntity())
    }
}