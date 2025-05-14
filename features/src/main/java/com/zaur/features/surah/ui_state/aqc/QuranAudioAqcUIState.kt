package com.zaur.features.surah.ui_state.aqc

import com.zaur.domain.al_quran_cloud.models.audiofile.CacheAudio
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudioFile
import com.zaur.domain.al_quran_cloud.models.audiofile.VerseAudioAqc

/**
* @author Zaur
* @since 2025-05-12
*/

interface QuranAudioAqcUIState {
    fun chaptersAudioFile(): ChapterAudioFile
    fun cacheAudios(): List<CacheAudio.Base>
    fun verseAudioFile(): VerseAudioAqc

    data class Base(
        private val chaptersAudioFile: ChapterAudioFile = ChapterAudioFile.Empty,
        private val cacheAudios: List<CacheAudio.Base> = emptyList(),
        private val verseAudioFile: VerseAudioAqc = VerseAudioAqc.Empty,
    ) : QuranAudioAqcUIState {
        override fun chaptersAudioFile() = chaptersAudioFile
        override fun cacheAudios() = cacheAudios
        override fun verseAudioFile() = verseAudioFile
    }
}