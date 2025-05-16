package com.zaur.presentation.ui.ui_state

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.zaur.domain.al_quran_cloud.models.audiofile.VerseAudioAqc


/**
 * @author Zaur
 * @since 13.05.2025
 */

interface AudioPlayerState {

    @Composable
    fun Render(
    ) = Unit

    @Composable
    fun Render(currentAyah: Int) = Unit

    data class AnimateToCurrentAyah(
        private val listState: LazyListState
    ) : AudioPlayerState {
        @Composable
        override fun Render(currentAyah: Int) {
            LaunchedEffect(currentAyah) {
                if (currentAyah >= 0) {
                    listState.animateScrollToItem(currentAyah)
                }
            }
        }
    }

    object RestartAudio : AudioPlayerState {
        @Composable
        override fun Render() {
            //todo
        }
    }

}