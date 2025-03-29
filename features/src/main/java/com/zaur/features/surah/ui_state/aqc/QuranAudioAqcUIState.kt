package com.zaur.features.surah.ui_state.aqc

import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudiosFileAqc
import com.zaur.domain.al_quran_cloud.models.audiofile.VersesAudioFileAqc

data class QuranAudioAqcUIState(
    val isLoading: Boolean = false,
    val chaptersAudioFile: ChapterAudiosFileAqc? = null,
    val verseAudioFile: VersesAudioFileAqc? = null,
    val errorMessage: String? = null,
    val isRefreshing: Boolean = false
)
