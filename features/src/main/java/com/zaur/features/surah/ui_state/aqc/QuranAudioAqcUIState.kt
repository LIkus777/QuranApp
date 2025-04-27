package com.zaur.features.surah.ui_state.aqc

import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudioFile
import com.zaur.domain.al_quran_cloud.models.audiofile.VerseAudioAqc

data class QuranAudioAqcUIState(
    val isLoading: Boolean = false,
    val chaptersAudioFile: ChapterAudioFile? = null,
    val verseAudioFile: VerseAudioAqc? = null,
    val errorMessage: String? = null,
    val isRefreshing: Boolean = false,
)