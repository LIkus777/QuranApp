package com.zaur.features.surah.screen.surah_detail.player

import com.zaur.features.surah.manager.SurahDetailStateManager
import com.zaur.features.surah.manager.SurahPlayerStateManager
import com.zaur.presentation.ui.ui_state.SurahPlayerState
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
        private val surahPlayerStateManager: SurahPlayerStateManager,
    ) : AudioPlayerStateUpdater {

        private val detailState get() = surahDetailStateManager.surahDetailState().value
        private val playerState get() = surahPlayerStateManager.surahPlayerState().value

        internal inline fun updateAudioPlayerState(block: SurahPlayerState.Base.() -> SurahPlayerState.Base) =
            surahPlayerStateManager.updateState(
                playerState.block()
            )

        internal inline fun updateTextAndAudioState(
            textBlock: TextState.Base.() -> TextState.Base,
            audioBlock: SurahPlayerState.Base.() -> SurahPlayerState.Base,
        ) {
            val currentTextState = detailState.textState()
            val currentAudioState = playerState

            surahDetailStateManager.updateState(
                detailState.copy(
                    textState = currentTextState.textBlock(),
                )
            )

            surahPlayerStateManager.updateState(
                currentAudioState.audioBlock()
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