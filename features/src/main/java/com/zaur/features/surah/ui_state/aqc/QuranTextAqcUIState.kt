package com.zaur.features.surah.ui_state.aqc

import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChaptersAqc
import com.zaur.domain.al_quran_cloud.models.chapter.ChapterAqc
import com.zaur.domain.al_quran_cloud.models.chapter.ChaptersAqc
import com.zaur.domain.apiV4.models.juz.JuzV4

data class QuranTextAqcUIState(
    val isLoading: Boolean = false,
    val chapters: ChaptersAqc? = null,
    val currentChapter: ChapterAqc? = null,
    val currentArabicText: ArabicChaptersAqc? = null,
    val errorMessage: String? = null,
    val isRefreshing: Boolean = false
)
