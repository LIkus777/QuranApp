package com.zaur.features.surah.ui_state

import com.zaur.domain.models.tajweed.VerseUthmanTajweed
import com.zaur.domain.models.translate.SingleTranslations

data class QuranUthmanTajweedUIState(
    val isLoading: Boolean = false,
    val data: List<VerseUthmanTajweed> = emptyList(),
    val errorMessage: String? = null,
    val isRefreshing: Boolean = false
)
