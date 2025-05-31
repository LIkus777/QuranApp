package com.zaur.presentation.ui.ui_state


/**
 * @author Zaur
 * @since 31.05.2025
 */

interface SurahPlayerState {

    fun surahName(): String
    fun currentAyah(): Int
    fun currentSurahNumber(): Int
    fun isAudioPlaying(): Boolean
    fun playWholeChapter(): Boolean
    fun restartAudio(): Boolean
    fun isOfflineMode(): Boolean
    fun position(): Long
    fun duration(): Long

    data class Base(
        private val surahName: String = "",
        private val currentAyah: Int = 0,
        private val currentSurahNumber: Int = 0,
        private val isAudioPlaying: Boolean = false,
        private val playWholeChapter: Boolean = true,
        private val restartAudio: Boolean = false,
        private val isOfflineMode: Boolean = false,
        private val position: Long = 0L,
        private val duration: Long = 0L,
    ) : SurahPlayerState {
        override fun surahName(): String = surahName
        override fun currentAyah() = currentAyah
        override fun currentSurahNumber() = currentSurahNumber
        override fun isAudioPlaying() = isAudioPlaying
        override fun playWholeChapter() = playWholeChapter
        override fun restartAudio() = restartAudio
        override fun isOfflineMode() = isOfflineMode
        override fun position(): Long = position
        override fun duration(): Long = duration
    }

    companion object {
        val Empty = Base()
    }
}
