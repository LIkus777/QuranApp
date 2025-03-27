package com.zaur.features.surah.ui_state

import com.zaur.domain.models.chapter.Chapter
import com.zaur.domain.models.juz.Juz

data class QuranTextUIState(
    val isLoading: Boolean = false,
    val chapters: List<Chapter> = emptyList(),
    val currentChapter: Chapter? = null,
    val juz: List<Juz> = emptyList(),
    val errorMessage: String? = null,
    val isRefreshing: Boolean = false
)