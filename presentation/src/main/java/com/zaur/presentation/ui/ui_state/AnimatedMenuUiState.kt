package com.zaur.presentation.ui.ui_state

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.zaur.presentation.ui.ChapterBottomBarComponent
import com.zaur.presentation.ui.HideSystemUI
import com.zaur.presentation.ui.QuranColors
import com.zaur.presentation.ui.SearchOverlay
import com.zaur.presentation.ui.SurahDetailTopBarComponent

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
        @OptIn(ExperimentalMaterial3Api::class)
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
            // Состояние видимости оверлея поиска
            var searchVisible by remember { mutableStateOf(false) }

            HideSystemUI(!isBarsVisible)

            // Основной контейнер
            Box(modifier = Modifier.fillMaxSize()) {
                // Скрываем/показываем верхний AppBar
                AnimatedVisibility(
                    visible = isBarsVisible, enter = fadeIn(), exit = fadeOut()
                ) {
                    SurahDetailTopBarComponent(
                        surahName = surahName,
                        colors = colors,
                        onMenuClick = onMenuClick,
                        onSearchClick = { searchVisible = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopCenter)
                    )
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

                // Search Overlay
                AnimatedVisibility(
                    visible = searchVisible,
                    enter = expandVertically(
                        // разворачивается из 1px сверху
                        expandFrom = Alignment.Top,
                        initialHeight = { 1 }) + fadeIn(animationSpec = tween(500)),
                    exit = shrinkVertically(
                        // сворачивается в 1px к верху
                        shrinkTowards = Alignment.Top,
                        targetHeight = { 1 }) + fadeOut(animationSpec = tween(500))
                ) {
                    SearchOverlay(
                        colors = colors, onDismiss = { searchVisible = false })
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