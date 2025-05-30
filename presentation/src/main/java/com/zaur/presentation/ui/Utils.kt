package com.zaur.presentation.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

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
    return navBarHeightInPx
}

@SuppressLint("ContextCastToActivity")
@Composable
fun HideSystemUI(hide: Boolean) {
    val activity = (LocalContext.current as? Activity) ?: return
    val window = activity.window
    val controller = remember(window) {
        WindowInsetsControllerCompat(window, window.decorView)
    }

    SideEffect {
        // управляем, подстраиваем ли мы контент под системные бары
        WindowCompat.setDecorFitsSystemWindows(window, !hide)

        if (hide) {
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            controller.hide(WindowInsetsCompat.Type.systemBars())
        } else {
            controller.show(WindowInsetsCompat.Type.systemBars())
        }
    }
}