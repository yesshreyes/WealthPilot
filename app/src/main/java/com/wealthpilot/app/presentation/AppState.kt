package com.wealthpilot.app.presentation

data class AppState(
    val isLoading: Boolean = false,
    val error: String? = null
)