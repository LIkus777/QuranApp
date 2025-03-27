package com.zaur.features.surah.ui_state

import com.zaur.domain.models.chapter.Chapter
import com.zaur.domain.models.translate.SingleTranslations
import com.zaur.domain.models.translate.Translation

data class QuranTranslationUIState(
    val isLoading: Boolean = false,
    val singleTranslation: SingleTranslations? = null,
    val translations: List<Translation> = emptyList(),
    val errorMessage: String? = null,
    val isRefreshing: Boolean = false
)
