package com.wealthpilot.app.presentation.screens.goal

data class GoalUiState(
    val goalAmount: String = "",
    val spentAmount: Double = 0.0,
    val progress: Float = 0f,
    val isGoalSet: Boolean = false,
    val isGoalExceeded: Boolean = false
)