package com.zaur.features.surah.ui_state.aqc

import com.zaur.domain.al_quran_cloud.models.translate.TranslationsChapterAqc


data class QuranTranslationAqcUIState(
    val isLoading: Boolean = false,
    val translations: TranslationsChapterAqc? = null,
    val errorMessage: String? = null,
    val isRefreshing: Boolean = false
)