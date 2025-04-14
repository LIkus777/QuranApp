package com.zaur.features.surah.screen

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import com.zaur.features.surah.ui_state.aqc.SurahDetailScreenState

interface PlayerScreen {
    @Composable
    fun Screen(
        clear: () -> Unit,
        onPlayWholeClicked: () -> Unit,
        scrollToAudioElement: () -> Unit = {}
    )

    class Base(
        private val surahDetailStateData: SurahDetailScreenState
    ) : PlayerScreen {
        @Composable
        override fun Screen(
            clear: () -> Unit,
            onPlayWholeClicked: () -> Unit,
            scrollToAudioElement: () -> Unit
        ) {
            LaunchedEffect(
                surahDetailStateData.audioPlayerState.restartAudio, surahDetailStateData.audioPlayerState.isAudioPlaying
            ) {
                if (surahDetailStateData.audioPlayerState.isAudioPlaying) {
                    Log.i("TAGG", "LaunchedEffect surahDetailStateData $surahDetailStateData")
                    onPlayWholeClicked() //todo понять что кликнул аят или всю суру
                    scrollToAudioElement()
                }
            }

            DisposableEffect(Unit) {
                onDispose {
                    clear()
                }
            }
        }
    }
}