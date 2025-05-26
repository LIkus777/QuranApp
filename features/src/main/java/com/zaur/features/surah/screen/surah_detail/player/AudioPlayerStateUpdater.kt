package com.zaur.features.surah.screen.surah_detail.player

import com.zaur.features.surah.manager.SurahDetailStateManager
import com.zaur.presentation.ui.ui_state.aqc.AudioPlayerState
import com.zaur.presentation.ui.ui_state.aqc.TextState

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

        private val state get() = surahDetailStateManager.surahDetailState().value

        internal inline fun updateAudioPlayerState(block: AudioPlayerState.Base.() -> AudioPlayerState.Base) {
            val currentAudioState = state.audioPlayerState()
            surahDetailStateManager.updateState(
                state.copy(audioPlayerState = currentAudioState.block())
            )
        }

        internal inline fun updateTextAndAudioState(
            textBlock: TextState.Base.() -> TextState.Base,
            audioBlock: AudioPlayerState.Base.() -> AudioPlayerState.Base,
        ) {
            val currentTextState = state.textState()
            val currentAudioState = state.audioPlayerState()

            surahDetailStateManager.updateState(
                state.copy(
                    textState = currentTextState.textBlock(),
                    audioPlayerState = currentAudioState.audioBlock()
                )
            )
        }

        override fun setPlaying(playing: Boolean) = updateAudioPlayerState {
            copy(isAudioPlaying = playing)
        }

        override fun setRestartAudio(restart: Boolean, isAudioPlaying: Boolean) =
            updateAudioPlayerState {
                copy(restartAudio = restart, isAudioPlaying = isAudioPlaying)
            }

        override fun updateCurrentAyah(numberInSurah: Int) = updateTextAndAudioState(
            textBlock = { copy(currentAyah = numberInSurah) },
            audioBlock = { copy(currentAyah = numberInSurah) })

        override fun setPlayWholeChapter(playing: Boolean) = updateAudioPlayerState {
            copy(playWholeChapter = playing)
        }

        override fun markWholeChapterPlaying(isAudioPlaying: Boolean, playWholeChapter: Boolean) =
            updateAudioPlayerState {
                copy(isAudioPlaying = isAudioPlaying, playWholeChapter = playWholeChapter)
            }

        override fun stop() = updateAudioPlayerState {
            copy(isAudioPlaying = false, playWholeChapter = false)
        }

        override fun setCurrentAyahAndSurah(surah: Int, ayah: Int) {
            surahDetailStateManager.updateAyahAndSurah(ayah, surah)
        }
    }
}