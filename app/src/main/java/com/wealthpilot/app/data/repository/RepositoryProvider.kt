package com.wealthpilot.app.data.repository

import android.content.Context
import com.wealthpilot.app.data.local.DatabaseProvider

object RepositoryProvider {

    fun provideTransactionRepository(context: Context): TransactionRepositoryImpl {
        val dao = DatabaseProvider.getDatabase(context).transactionDao()
        return TransactionRepositoryImpl(dao)
    }
}