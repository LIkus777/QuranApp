package com.zaur.features.surah.ui_state.aqc

import com.zaur.domain.al_quran_cloud.models.translate.TranslationAqc

interface QuranTranslationAqcUIState {

    fun isLoading(): Boolean
    fun translations(): TranslationAqc
    fun isRefreshing(): Boolean

    data class Base(
        private val isLoading: Boolean = false,
        private val translations: TranslationAqc = TranslationAqc.Empty,
        private val isRefreshing: Boolean = false
    ) : QuranTranslationAqcUIState {
        override fun isLoading() = isLoading
        override fun translations() = translations
        override fun isRefreshing() = isRefreshing
    }
}