package com.zaur.presentation.ui

import androidx.compose.ui.graphics.Color

// Основные цвета
val PrimaryGreen = Color(0xFF008672) // Глубокий зелёный (улучшен)
val Gold = Color(0xFFCFAF5D) // Золотой акцент
val DeepBlue = Color(0xFF283593) // Улучшенный глубокий синий
val LightGray = Color(0xFFF5F5F5) // Светло-серый фон
val DarkGray = Color(0xFF1C1C1C) // Тёмный фон
val White = Color(0xFFFFFFFF) // Белый
val Red = Color(0xFFD84315) // Красный (ошибки, улучшен)
val SoftGreen = Color(0xFF66BB6A) // Улучшенный мягкий зелёный (успех)
val Orange = Color(0xFFFFA726) // Оранжевый (предупреждения)
val LightBlue = Color(0xFF42A5F5) // Голубой (выделения)

// Дополнительные оттенки
val DarkerGray = Color(0xFF121212) // Очень тёмный фон
val LightGold = Color(0xFFFFD700) // Светлый золотой (ярче)
val DarkGold = Color(0xFFB8860B) // Тёмный золотой
val TransparentBlack = Color(0x66000000) // Чёрный с прозрачностью (тени)
val TransparentWhite = Color(0x66FFFFFF) // Белый с прозрачностью (подсветка)
val DisabledGray = Color(0xFFE1E1E3) // Серый для отключённых элементов
val CurrentGray = Color(0xCBE8E8E8) // Серый для отключённых элементов

// Цвета для AppBar и StatusBar
val AppBarColor = PrimaryGreen // Основной цвет AppBar
val StatusBarColor = DarkGray // Цвет StatusBar
val IconColor = White // Цвет иконок

// Светлая тема
val LightThemeColors = QuranColors(
    background = White,
    cardBackground = LightGray,
    boxBackground = Color(0xFFEAEAEA),
    textPrimary = DeepBlue,
    textSecondary = DarkGray,
    textOnCard = Color(0xFF212121),
    textOnButton = White,
    buttonPrimary = PrimaryGreen,
    buttonSecondary = LightBlue,
    buttonTertiary = Gold,
    buttonDanger = Red,
    buttonDisabled = DisabledGray,
    border = Gold,
    highlight = LightBlue,
    divider = Color(0xFFBDBDBD),
    backgroundForBoxes = Gold,
    success = SoftGreen,
    warning = Orange,
    error = Red,
    shadow = TransparentBlack,
    appBarColor = AppBarColor,
    statusBarColor = StatusBarColor,
    iconColor = IconColor,
    currentCard = CurrentGray,
)

// Тёмная тема
val DarkThemeColors = QuranColors(
    background = DarkerGray,
    cardBackground = DarkGray,
    boxBackground = Color(0xFF252525),
    textPrimary = White,
    textSecondary = Color(0xFFB0BEC5),
    textOnCard = Color(0xFFE0E0E0),
    textOnButton = White,
    buttonPrimary = Color(0xFF388E3C),
    buttonSecondary = LightBlue,
    buttonTertiary = LightGold,
    buttonDanger = Red,
    buttonDisabled = DisabledGray,
    currentCard = CurrentGray,
    border = Gold,
    highlight = LightGold,
    divider = Color(0xFF616161),
    backgroundForBoxes = DarkGold,
    success = SoftGreen,
    warning = Orange,
    error = Red,
    shadow = TransparentWhite,
    appBarColor = AppBarColor,
    statusBarColor = StatusBarColor,
    iconColor = IconColor
)

// Расширенная структура цветов
data class QuranColors(
    val background: Color,
    val cardBackground: Color,
    val boxBackground: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textOnCard: Color,
    val textOnButton: Color,
    val buttonPrimary: Color,
    val buttonSecondary: Color,
    val buttonTertiary: Color,
    val buttonDanger: Color,
    val buttonDisabled: Color,
    val border: Color,
    val highlight: Color,
    val divider: Color,
    val backgroundForBoxes: Color,
    val success: Color,
    val warning: Color,
    val error: Color,
    val shadow: Color,
    val appBarColor: Color, // Цвет для AppBar
    val statusBarColor: Color, // Цвет для StatusBar
    val iconColor: Color,
    val currentCard: Color
)