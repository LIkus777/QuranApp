package com.zaur.features.surah.ui_state

import com.zaur.domain.models.audiofile.ChaptersAudioFile
import com.zaur.domain.models.audiofile.VerseAudioFile
import com.zaur.domain.models.recitations.Recitations

data class QuranAudioUIState(
    val isLoading: Boolean = false,
    val recitations: Recitations? = null,
    val chaptersAudioFile: ChaptersAudioFile? = null,
    val verseAudioFile: VerseAudioFile? = null,
    val errorMessage: String? = null,
    val isRefreshing: Boolean = false
)
