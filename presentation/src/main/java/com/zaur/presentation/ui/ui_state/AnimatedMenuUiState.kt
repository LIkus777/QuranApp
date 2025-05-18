package com.zaur.presentation.ui.ui_state

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.zaur.presentation.ui.ChapterBottomBarComponent
import com.zaur.presentation.ui.ChapterTopBarComponent
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
        isPlaying: Boolean,
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
            isPlaying: Boolean,
            surahName: String,
            isBarsVisible: Boolean,
            colors: QuranColors,
            onMenuClick: () -> Unit,
            onClickSettings: () -> Unit,
            onClickReciter: (Boolean) -> Unit,
            onClickPlay: () -> Unit,
        ) {
            AnimatedVisibility(
                visible = isBarsVisible, enter = fadeIn(), exit = fadeOut()
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
                    ChapterTopBarComponent(
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
                        isPlaying = isPlaying,
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