package com.zaur.features.surah.ui_state

import com.zaur.domain.models.chapter.Chapter
import com.zaur.domain.models.translate.SingleTranslations

data class QuranTranslationUIState(
    val isLoading: Boolean = false,
    val data: SingleTranslations? = null,
    val errorMessage: String? = null,
    val isRefreshing: Boolean = false
)
