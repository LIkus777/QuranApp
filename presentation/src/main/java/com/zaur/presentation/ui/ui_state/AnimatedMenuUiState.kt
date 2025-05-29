package com.zaur.presentation.ui.ui_state

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.zaur.presentation.ui.ChapterBottomBarComponent
import com.zaur.presentation.ui.SurahDetailTopBarComponent
import com.zaur.presentation.ui.QuranColors


/**
 * @author Zaur
 * @since 13.05.2025
 */

interface AnimatedMenuUiState {

    @Composable
    fun Render() = Unit

    @Composable
    fun Render(
        surahName: String,
        isBarsVisible: Boolean,
        colors: QuranColors,
        onMenuClick: () -> Unit,
        onClickSettings: () -> Unit,
        onClickReciter: (Boolean) -> Unit,
        onClickPlay: () -> Unit,
    ) = Unit

    object Animate : AnimatedMenuUiState {
        @Composable
        override fun Render(
            surahName: String,
            isBarsVisible: Boolean,
            colors: QuranColors,
            onMenuClick: () -> Unit,
            onClickSettings: () -> Unit,
            onClickReciter: (Boolean) -> Unit,
            onClickPlay: () -> Unit,
        ) {
            HideSystemUI(!isBarsVisible)
            AnimatedVisibility(
                visible = isBarsVisible, enter = fadeIn(), exit = fadeOut()
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
                    SurahDetailTopBarComponent(
                        surahName = surahName,
                        onMenuClick = onMenuClick,
                        colors = colors,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopCenter)
                    )
                }
            }

            // BottomBar поверх контента
            AnimatedVisibility(
                visible = isBarsVisible, enter = fadeIn(), exit = fadeOut()
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter
                ) {
                    ChapterBottomBarComponent(
                        colors = colors,
                        onClickSettings = onClickSettings,
                        onClickReciter = onClickReciter,
                        onClickPlay = onClickPlay,
                    )
                }
            }
        }
    }

    object Empty : AnimatedMenuUiState {
        @Composable
        override fun Render() {

        }
    }

}

@SuppressLint("ContextCastToActivity")
@Composable
fun HideSystemUI(hide: Boolean) {
    val activity = LocalContext.current as Activity
    val window = activity.window
    val controller = remember(window) {
        WindowInsetsControllerCompat(window, window.decorView)
    }

    DisposableEffect(hide) {
        // Если нужно скрывать
        if (hide) {
            WindowCompat.setDecorFitsSystemWindows(window, false)
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            controller.hide(WindowInsetsCompat.Type.systemBars())
        } else {
            // Показываем системные бары
            WindowCompat.setDecorFitsSystemWindows(window, true)
            controller.show(WindowInsetsCompat.Type.systemBars())
        }

        // onDispose можно оставить пустым, или вернуть состояние по умолчанию
        onDispose { /* ничего не делаем */ }
    }
}