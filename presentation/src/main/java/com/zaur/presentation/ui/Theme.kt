package com.zaur.presentation.ui

import androidx.compose.ui.graphics.Color

// Основные цвета
val PrimaryGreen = Color(0xFF097969) // Основной зелёный
val Gold = Color(0xFFCFAF5D) // Золотой акцент
val DeepBlue = Color(0xFF1A237E) // Глубокий синий
val LightGray = Color(0xFFF5F5F5) // Светло-серый фон
val DarkGray = Color(0xFF1B1B1B) // Тёмный фон
val White = Color(0xFFFFFFFF) // Белый
val Red = Color(0xFFD32F2F) // Красный (для ошибок)
val SoftGreen = Color(0xFF81C784) // Мягкий зелёный (успех)
val Orange = Color(0xFFFFA726) // Оранжевый (предупреждения)
val LightBlue = Color(0xFF42A5F5) // Голубой (выделения)

// Дополнительные оттенки
val DarkerGray = Color(0xFF121212) // Очень тёмный фон
val LightGold = Color(0xFFFFE082) // Светлый золотой (например, для градиента)
val DarkGold = Color(0xFFB8860B) // Тёмный золотой (например, для границ)
val TransparentBlack = Color(0x66000000) // Чёрный с прозрачностью (тени)
val TransparentWhite = Color(0x66FFFFFF) // Белый с прозрачностью (подсветка)
val DisabledGray = Color(0xFFE1E1E3) // Серый для отключённых элементов

// Светлая тема
val LightThemeColors = QuranColors(
    backgroundForBoxes = Gold, background = White,  // Светлый фон
    cardBackground = LightGray,  // Фон карточек
    boxBackground = Color(0xFFEAEAEA), // Фон блоков
    textPrimary = DeepBlue, // Основной текст
    textSecondary = DarkGray, // Второстепенный текст
    textOnCard = Color(0xFF212121), // Чёрный текст внутри карточек
    textOnButton = White, // Белый текст на кнопках
    buttonPrimary = PrimaryGreen, // Основные кнопки (главное действие)
    buttonSecondary = LightBlue, // Альтернативные кнопки
    buttonTertiary = Gold, // Кнопки низкой важности (например, текстовые ссылки)
    buttonDanger = Red, // Опасные кнопки (удаление, выход)
    buttonDisabled = DisabledGray, // Отключённые кнопки
    border = Gold, // Граница элементов
    highlight = LightBlue, // Выделения
    divider = Color(0xFFBDBDBD), // Разделители
    success = SoftGreen, // Успех
    warning = Orange, // Предупреждение
    error = Red, // Ошибка
    shadow = TransparentBlack // Тени
)

// Тёмная тема
val DarkThemeColors = QuranColors(
    backgroundForBoxes = DarkGold, background = DarkerGray, // Тёмный фон
    cardBackground = DarkGray, // Фон карточек
    boxBackground = Color(0xFF252525), // Фон блоков
    textPrimary = White, // Основной текст
    textSecondary = Color(0xFFB0BEC5), // Второстепенный текст
    textOnCard = Color(0xFFE0E0E0), // Светлый текст на карточках
    textOnButton = White, // Белый текст на кнопках
    buttonPrimary = Color(0xFF388E3C), // Основные кнопки (тёмно-зелёный)
    buttonSecondary = LightBlue, // Альтернативные кнопки
    buttonTertiary = LightGold, // Кнопки низкой важности
    buttonDanger = Red, // Опасные кнопки (удаление, выход)
    buttonDisabled = DisabledGray, // Отключённые кнопки
    border = Gold, // Граница элементов
    highlight = LightGold, // Выделения
    divider = Color(0xFF616161), // Разделители
    success = SoftGreen, // Успех
    warning = Orange, // Предупреждение
    error = Red, // Ошибка
    shadow = TransparentWhite // Светлая тень
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
    val buttonPrimary: Color, // Основные кнопки
    val buttonSecondary: Color, // Альтернативные кнопки
    val buttonTertiary: Color, // Кнопки с низким приоритетом (например, текстовые)
    val buttonDanger: Color, // Опасные кнопки (например, удаление)
    val buttonDisabled: Color, // Отключённые кнопки
    val border: Color,
    val highlight: Color,
    val divider: Color,
    val backgroundForBoxes: Color,
    val success: Color, // Новый цвет для успешных сообщений
    val warning: Color, // Новый цвет для предупреждений
    val error: Color, // Новый цвет для ошибок
    val shadow: Color // Новый цвет для теней
)
