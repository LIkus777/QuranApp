package com.zaur.features.surah.ui_state.aqc

import com.zaur.domain.al_quran_cloud.models.audiofile.Ayah
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudioFile

data class QuranAudioAqcUIState(
    val isLoading: Boolean = false,
    val chaptersAudioFile: ChapterAudioFile? = null,
    val verseAudioFile: Ayah? = null,
    val errorMessage: String? = null,
    val isRefreshing: Boolean = false,
)
