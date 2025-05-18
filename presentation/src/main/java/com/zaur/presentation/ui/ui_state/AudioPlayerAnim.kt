package com.zaur.presentation.ui.ui_state

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect


/**
 * @author Zaur
 * @since 13.05.2025
 */

interface AudioPlayerAnim {

    @Composable
    fun Render(currentAyah: Int) = Unit

    data class AnimateToCurrentAyah(
        private val listState: LazyListState,
    ) : AudioPlayerAnim {
        @Composable
        override fun Render(currentAyah: Int) {
            LaunchedEffect(currentAyah) {
                if (currentAyah >= 0) {
                    listState.animateScrollToItem(currentAyah)
                }
            }
        }
    }

}