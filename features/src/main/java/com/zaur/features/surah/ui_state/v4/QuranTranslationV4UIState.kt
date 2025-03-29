package com.zaur.features.surah.ui_state.v4

import com.zaur.domain.apiV4.models.translate.SingleTranslationsV4
import com.zaur.domain.apiV4.models.translate.TranslationV4

data class QuranTranslationV4UIState(
    val isLoading: Boolean = false,
    val singleTranslation: SingleTranslationsV4? = null,
    val translations: List<TranslationV4> = emptyList(),
    val errorMessage: String? = null,
    val isRefreshing: Boolean = false
)
