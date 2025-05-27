package com.zaur.presentation.ui

import androidx.compose.ui.graphics.Color

/**
 * @author Zaur
 * @since 2025-05-12
 */

// Основные цвета
val PrimaryGreen = Color(0xFF2E7D68) // Умиротворяющий зелёный
val Gold = Color(0xFFC9A74B) // Мягкий золотой
val DeepBlue = Color(0xFF2A3C73) // Приглушённый синий
val LightGray = Color(0xFFF2F2F2)
val DarkGray = Color(0xFF1F1F1F)
val DarkGray75 = Color(0xBF1F1F1F)
val DarkGray45 = Color(0x721F1F1F)
val White = Color(0xFFFFFFFF)
val Black = Color(0xFF000000)
val Red = Color(0xFFD84315)
val SoftGreen = Color(0xFF66BB6A)
val Orange = Color(0xFFFFA726)
val LightBlue = Color(0xFF4FA3E0) // Менее кислотный голубой

// Дополнительные оттенки
val DarkerGray = Color(0xFF3B3B3B)
val LightGold = Color(0xFFFFE082) // Мягкий светлый золотой
val DarkGold = Color(0xFF8C6D1F)
val TransparentBlack = Color(0x66000000)
val TransparentWhite = Color(0x66FFFFFF)
val DisabledGray = Color(0xFFD0D0D2)
val CurrentGray = Color(0xCCE5E5E5)
val CurrentBlack = Color(0xFF393939)
val BalancedGold = Color(0xFFF2C94C) // приятный тёплый золотой без кислотности

val AyahColorLight = Color(0xFF717171) // Темно-серый для светлой темы
val AyahColorDark = Color(0xFF7F898E) // Светло-серый с голубоватым оттенком для тёмной темы
val AyahBorderLight = Color(0xFF909090) // Темно-серый для светлой темы
val AyahBorderDark = Color(0xFF6B7478) // Светло-серый с голубоватым оттенком для тёмной темы

// Цвета для AppBar и StatusBar
val AppBarColor = PrimaryGreen
val StatusBarColor = DarkGray
val IconColorWhite = White
val IconColorBlack = Black

// Светлая тема
val LightThemeColors = QuranColors(
    shadowForActiveBars = DarkGray75,
    shadowForDialogs = DarkGray45,
    background = White,
    cardBackground = LightGray,
    boxBackground = Color(0xFFE8E8E8),
    textPrimary = DeepBlue,
    textSecondary = Color(0xFF4A4A4A),
    textOnCard = Color(0xFF1F1F1F),
    textOnButton = White,
    buttonPrimary = PrimaryGreen,
    buttonSecondary = LightBlue,
    buttonTertiary = Gold,
    buttonDanger = Red,
    buttonDisabled = DisabledGray,
    border = Gold,
    highlight = LightBlue,
    divider = Color(0xFFCCCCCC),
    backgroundForBoxes = Gold,
    success = SoftGreen,
    warning = Orange,
    error = Red,
    shadow = TransparentBlack,
    appBarColor = AppBarColor,
    statusBarColor = StatusBarColor,
    iconColorForBottom = IconColorWhite,
    iconColorForTop = IconColorBlack,
    currentCard = CurrentGray,
    ayahBorder = AyahBorderLight,
    ayahColor = AyahColorLight,
    ayahTextPrimary = White,
    progressCircle = BalancedGold
)

// Тёмная тема
val DarkThemeColors = QuranColors(
    shadowForActiveBars = DarkGray75,
    shadowForDialogs = DarkGray45,
    background = DarkerGray,
    cardBackground = DarkGray,
    boxBackground = Color(0xFF2A2A2A),
    textPrimary = White,
    textSecondary = Color(0xFFB0BEC5),
    textOnCard = Color(0xFFE0E0E0),
    textOnButton = White,
    buttonPrimary = Color(0xFF2E7D32),
    buttonSecondary = LightBlue,
    buttonTertiary = LightGold,
    buttonDanger = Red,
    buttonDisabled = DisabledGray,
    currentCard = CurrentBlack,
    border = LightGold,
    highlight = LightBlue,
    divider = Color(0xFF505050),
    backgroundForBoxes = DarkGold,
    success = SoftGreen,
    warning = Orange,
    error = Red,
    shadow = TransparentWhite,
    appBarColor = AppBarColor,
    statusBarColor = StatusBarColor,
    iconColorForBottom = IconColorBlack,
    iconColorForTop = IconColorWhite,
    ayahBorder = AyahBorderDark,
    ayahColor = AyahColorDark,
    ayahTextPrimary = White,
    progressCircle = BalancedGold,
)

// Расширенная структура цветов
data class QuranColors(
    val progressCircle: Color,
    val shadowForActiveBars: Color,
    val shadowForDialogs: Color,
    val background: Color,
    val cardBackground: Color,
    val boxBackground: Color,
    val ayahColor: Color,
    val ayahBorder: Color,
    val textPrimary: Color,
    val ayahTextPrimary: Color,
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
    val iconColorForBottom: Color,
    val iconColorForTop: Color,
    val currentCard: Color,
)