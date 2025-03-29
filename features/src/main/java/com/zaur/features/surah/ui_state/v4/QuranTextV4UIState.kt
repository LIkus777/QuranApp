package com.zaur.features.surah.ui_state.v4

import com.zaur.domain.apiV4.models.chapter.ChapterV4
import com.zaur.domain.apiV4.models.juz.JuzV4

data class QuranTextV4UIState(
    val isLoading: Boolean = false,
    val chapters: List<ChapterV4> = emptyList(),
    val currentChapter: ChapterV4? = null,
    val juz: List<JuzV4> = emptyList(),
    val errorMessage: String? = null,
    val isRefreshing: Boolean = false
)