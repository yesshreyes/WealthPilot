# 💸 WealthPilot

WealthPilot is a modern, beautifully designed personal finance tracker built for Android. It enables users to effortlessly log, track, and analyze their expenses and incomes to maintain control over their financial health.

## ✨ Features

- **Unified Dashboard**: A sleek overview showing total balance, monthly income, and expenses all in one cohesive card.
- **Record Transactions**: Easily log income and expenses with detailed fields for amount, category, date, and optional notes.
- **Advanced Insights**: Visualize your spending habits through comprehensive breakdowns, identifying your highest spending areas and most frequent transaction categories.
- **Spending Goals**: Set up dynamic monthly spending goals to visually track your budget and ensure you are keeping "on track".
- **Transaction Management**: Search, filter natively (Income vs Expense), edit, and swipe-to-delete functionalities for full control over your financial log.
- **Seamless Configuration**: Full settings suite offering robust data capabilities like **Exporting to CSV**, wiping local data, and toggling application-wide theme modes.

## 🛠️ Technology Stack

WealthPilot leverages modern Android development best practices and the latest libraries:

- **Language**: [Kotlin](https://kotlinlang.org/)
- **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose) with Material 3 & Custom Glassmorphism Styling.
- **Architecture**: MVVM (Model-View-ViewModel) with Clean Architecture principles.
- **Local Database**: [Room Database](https://developer.android.com/training/data-storage/room) for persistent, robust offline data storage.
- **Asynchronous Programming**: Coroutines and Flows for reactive, non-blocking UI state management.
- **Navigation**: Jetpack Navigation Compose (`androidx.navigation.compose`).

## 🎨 UI Highlight: Glassmorphism
The application interface avoids flat generic colors, instead treating elements with custom multi-layer shadow elevations, gradient borders, and semi-transparent surfaces to produce a striking "glass-like" aesthetic that adapts flawlessly across day and night.

## 🚀 Getting Started

### Prerequisites
- [Android Studio](https://developer.android.com/studio) (latest stable version recommended)
- SDK 24 (Min) to SDK 36 (Target)

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/yesshreyes/WealthPilot
   ```
2. Open the project in Android Studio.
3. Sync the Gradle files to resolve all dependencies.
4. Build and Run the application on an emulator or a physical device.
