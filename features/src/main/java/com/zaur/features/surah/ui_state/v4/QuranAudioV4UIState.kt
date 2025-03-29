package com.zaur.features.surah.ui_state.v4

import com.zaur.domain.apiV4.models.audiofile.ChapterAudioFileV4
import com.zaur.domain.apiV4.models.audiofile.VerseAudioFileV4
import com.zaur.domain.apiV4.models.recitations.RecitationsV4

data class QuranAudioV4UIState(
    val isLoading: Boolean = false,
    val recitations: List<RecitationsV4> = emptyList(),
    val chaptersAudioFile: ChapterAudioFileV4? = null,
    val verseAudioFile: VerseAudioFileV4? = null,
    val errorMessage: String? = null,
    val isRefreshing: Boolean = false
)