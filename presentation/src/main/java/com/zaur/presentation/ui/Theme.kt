package com.zaur.presentation.ui

import androidx.compose.ui.graphics.Color

val PrimaryGreen = Color(0xFF097969) // Основной зелёный
val Gold = Color(0xFFCFAF5D) // Золотой акцент
val DeepBlue = Color(0xFF1A237E) // Глубокий синий
val LightGray = Color(0xFFF5F5F5) // Светло-серый фон
val DarkGray = Color(0xFF1B1B1B) // Темный фон
val White = Color(0xFFFFFFFF) // Белый
val Red = Color(0xFFD32F2F) // Красный (для ошибок)

val LightThemeColors = QuranColors(
    background = Color(0xFFFFFFFF),      // Светлый фон (почти белый)
    cardBackground = Color(0xFFE8E8E8),  // Серый фон карточек (темнее фона)
    boxBackground = Color(0xFFEAEAEA),   // Чуть светлее карточек
    textPrimary = Color(0xFF1A237E),     // Тёмно-синий основной текст
    textSecondary = Color(0xFF757575),   // Серый второстепенный текст
    textOnCard = Color(0xFF212121),      // Чёрный текст внутри карточек
    buttonBackground = Color(0xFF4CAF50),// Зелёный цвет кнопок
    buttonText = Color.White,            // Белый текст на кнопках
    border = Color(0xFFFFD700),          // Золотая рамка
    highlight = Color(0xFF2196F3),       // Голубой акцент (например, ссылки)
    divider = Color(0xFFBDBDBD)          // Серый разделитель
)

val DarkThemeColors = QuranColors(
    background = Color(0xFF121212),      // Тёмный фон
    cardBackground = Color(0xFF1C1C1C),  // Тёмно-серый фон карточек (темнее фона)
    boxBackground = Color(0xFF252525),   // Чуть светлее карточек
    textPrimary = Color(0xFFFFFFFF),     // Белый основной текст
    textSecondary = Color(0xFFB0BEC5),   // Светло-серый второстепенный текст
    textOnCard = Color(0xFFE0E0E0),      // Светлый текст в карточках
    buttonBackground = Color(0xFF388E3C),// Тёмно-зелёный цвет кнопок
    buttonText = Color.White,            // Белый текст на кнопках
    border = Color(0xFFFFC107),          // Золотая рамка
    highlight = Color(0xFF64B5F6),       // Голубой акцент (например, ссылки)
    divider = Color(0xFF616161)          // Тёмно-серый разделитель
)

data class QuranColors(
    val background: Color,
    val cardBackground: Color,
    val boxBackground: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textOnCard: Color,
    val buttonBackground: Color,
    val buttonText: Color,
    val border: Color,
    val highlight: Color,
    val divider: Color
)

