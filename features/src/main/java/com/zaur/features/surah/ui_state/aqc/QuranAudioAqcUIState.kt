package com.zaur.features.surah.ui_state.aqc

import com.zaur.domain.al_quran_cloud.models.audiofile.CacheAudio
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudioFile
import com.zaur.domain.al_quran_cloud.models.audiofile.VerseAudioAqc

interface QuranAudioAqcUIState {
    fun isLoading(): Boolean
    fun chaptersAudioFile(): ChapterAudioFile
    fun cacheAudios(): List<CacheAudio.Base>
    fun verseAudioFile(): VerseAudioAqc
    fun isRefreshing(): Boolean

    data class Base(
        private val isLoading: Boolean = false,
        private val chaptersAudioFile: ChapterAudioFile = ChapterAudioFile.Empty,
        private val cacheAudios: List<CacheAudio.Base> = emptyList(),
        private val verseAudioFile: VerseAudioAqc = VerseAudioAqc.Empty,
        private val isRefreshing: Boolean = false
    ) : QuranAudioAqcUIState {
        override fun isLoading() = isLoading
        override fun chaptersAudioFile() = chaptersAudioFile
        override fun cacheAudios() = cacheAudios
        override fun verseAudioFile() = verseAudioFile
        override fun isRefreshing() = isRefreshing
    }
}