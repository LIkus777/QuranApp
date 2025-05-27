package com.zaur.presentation.ui

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

/**
 * @author Zaur
 * @since 22.05.2025
 */

@Composable
fun Modifier.rippleClickable(
    shape: Shape = RoundedCornerShape(50), // для круглого ripple
    onClick: () -> Unit,
): Modifier = this
    .clip(shape)
    .clickable(
        onClick = onClick
    )
    .padding(2.dp)

fun String.removeBasmala(): String {
    // Это разные варианты басмалы (разные касыры, сукуны, татвилы, орфография)
    val variants = listOf(
        "بِسْمِ ٱللَّهِ ٱلرَّحْمَٰنِ ٱلرَّحِيمِ",
        "بِسۡمِ ٱللَّهِ ٱلرَّحۡمَـٰنِ ٱلرَّحِیمِ",
        "بسم الله الرحمن الرحيم",
    )
    val cleaned = this.trimStart(' ', '،', '\n')
    for (variant in variants) {
        if (cleaned.startsWith(variant)) {
            return cleaned.removePrefix(variant).trimStart(' ', '،', '\n')
        }
    }
    return this
}

fun getNavBarHeightInPx(context: Context): Int {
    // Получение высоты навигационной панели через ресурсы
    val resourceId = context.resources.getIdentifier("navigation_bar_height", "dimen", "android")
    val navBarHeightInPx = if (resourceId > 0) {
        context.resources.getDimensionPixelSize(resourceId)
    } else {
        0
    }
    return navBarHeightInPx + 30
}