package com.zaur.features.surah.screen

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.zaur.features.surah.ui_state.aqc.SurahDetailState

interface PlayerScreen {
    @Composable
    fun Screen(
        audioUrl: String = "",
        setIsPlaying: (Boolean) -> Unit,
        scrollToAudioElement: () -> Unit = {},
        player: Player? = null
    )

    class Base(
        private val surahDetailStateData: SurahDetailState
    ) : PlayerScreen {
        @Composable
        override fun Screen(
            audioUrl: String, setIsPlaying: (Boolean) -> Unit, scrollToAudioElement: () -> Unit, player: Player?
        ) {

            // LaunchedEffect для обработки состояния воспроизведения
            LaunchedEffect(
                audioUrl, surahDetailStateData.restartAudio, surahDetailStateData.runAudio
            ) {
                if (surahDetailStateData.runAudio) {
                    Log.i("TAGG", "LaunchedEffect audioUrl $audioUrl")
                    Log.i("TAGG", "LaunchedEffect surahDetailStateData $surahDetailStateData")
                    player?.player(audioUrl)
                    // Если плеер уже существует, не создаем новый
                    setIsPlaying(true)
                    // Прокручиваем к элементу после начала воспроизведения
                    scrollToAudioElement()
                }
            }
        }
    }
}