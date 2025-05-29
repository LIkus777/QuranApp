package com.zaur.presentation.ui.ui_state.aqc

import com.zaur.domain.al_quran_cloud.models.audiofile.CacheAudio
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudioFile
import com.zaur.domain.al_quran_cloud.models.audiofile.VerseAudio

/**
 * @author Zaur
 * @since 2025-05-12
 */

interface SurahPlayerUIState {

    fun chaptersAudioFile(): ChapterAudioFile
    fun cacheAudios(): List<CacheAudio.Base>
    fun verseAudioFile(): VerseAudio

    data class Base(
        private val chaptersAudioFile: ChapterAudioFile = ChapterAudioFile.Empty,
        private val cacheAudios: List<CacheAudio.Base> = emptyList(),
        private val verseAudioFile: VerseAudio = VerseAudio.Empty,
    ) : SurahPlayerUIState {
        override fun chaptersAudioFile() = chaptersAudioFile
        override fun cacheAudios() = cacheAudios
        override fun verseAudioFile() = verseAudioFile
    }

}