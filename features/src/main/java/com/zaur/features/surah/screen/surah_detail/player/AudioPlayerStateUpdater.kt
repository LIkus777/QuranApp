package com.zaur.features.surah.screen.surah_detail.player

import com.zaur.features.surah.manager.SurahDetailStateManager

/**
 * @author Zaur
 * @since 2025-05-12
 */

interface AudioPlayerStateUpdater {

    fun stop()
    fun markWholeChapterPlaying(isAudioPlaying: Boolean, playWholeChapter: Boolean)
    fun setPlayWholeChapter(playing: Boolean)
    fun setPlaying(playing: Boolean)
    fun updateCurrentAyah(numberInSurah: Int)
    fun setRestartAudio(restart: Boolean, isAudioPlaying: Boolean)
    fun setCurrentAyahAndSurah(surah: Int, ayah: Int)

    data class Base(
        private val surahDetailStateManager: SurahDetailStateManager,
    ) : AudioPlayerStateUpdater {

        private val state = surahDetailStateManager.surahDetailState()

        override fun setPlaying(playing: Boolean) {
            surahDetailStateManager.updateState(
                state.value.copy(
                    audioPlayerState = state.value.audioPlayerState().copy(isAudioPlaying = playing)
                )
            )
        }

        override fun setRestartAudio(restart: Boolean, isAudioPlaying: Boolean) {
            surahDetailStateManager.updateState(
                state.value.copy(
                    audioPlayerState = state.value.audioPlayerState().copy(
                        restartAudio = restart, isAudioPlaying = isAudioPlaying
                    )
                )
            )
        }

        override fun updateCurrentAyah(numberInSurah: Int) {
            surahDetailStateManager.updateState(
                state.value.copy(
                    textState = state.value.textState().copy(
                        currentAyah = numberInSurah
                    ), audioPlayerState = state.value.audioPlayerState().copy(
                        currentAyah = numberInSurah,
                    )
                )
            )
        }

        override fun setPlayWholeChapter(playing: Boolean) {
            surahDetailStateManager.updateState(
                state.value.copy(
                    audioPlayerState = state.value.audioPlayerState().copy(
                        playWholeChapter = playing
                    )
                )
            )
        }

        override fun markWholeChapterPlaying(isAudioPlaying: Boolean, playWholeChapter: Boolean) {
            surahDetailStateManager.updateState(
                state.value.copy(
                    audioPlayerState = state.value.audioPlayerState().copy(
                        isAudioPlaying = isAudioPlaying, playWholeChapter = playWholeChapter
                    )
                )
            )
        }

        override fun stop() {
            surahDetailStateManager.updateState(
                state.value.copy(
                    audioPlayerState = state.value.audioPlayerState()
                        .copy(isAudioPlaying = false, playWholeChapter = false)
                )
            )
        }

        override fun setCurrentAyahAndSurah(surah: Int, ayah: Int) {
            surahDetailStateManager.updateState(
                state.value.copy(
                    audioPlayerState = state.value.audioPlayerState().copy(
                        currentSurahNumber = surah, currentAyah = ayah
                    )
                )
            )
        }
    }
}