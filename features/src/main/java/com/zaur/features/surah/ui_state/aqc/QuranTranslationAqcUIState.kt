package com.zaur.features.surah.ui_state.aqc

import com.zaur.domain.al_quran_cloud.models.translate.TranslationAqc

data class QuranTranslationAqcUIState(
    val isLoading: Boolean = false,
    val translations: TranslationAqc? = null,
    val errorMessage: String? = null,
    val isRefreshing: Boolean = false,
)