package com.zaur.features.surah.ui_state.v4

import com.zaur.domain.apiV4.models.tajweed.VerseUthmanTajweedV4

data class QuranUthmanTajweedUIState(
    val isLoading: Boolean = false,
    val tajweeds: List<VerseUthmanTajweedV4> = emptyList(),
    val errorMessage: String? = null,
    val isRefreshing: Boolean = false
)
