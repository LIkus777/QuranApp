package com.zaur.presentation.ui.ui_state.aqc

import com.zaur.domain.al_quran_cloud.models.translate.TranslationAqc

/**
* @author Zaur
* @since 2025-05-12
*/

interface QuranTranslationUIState {

    fun isLoading(): Boolean
    fun translations(): TranslationAqc
    fun isRefreshing(): Boolean

    data class Base(
        private val isLoading: Boolean = false,
        private val translations: TranslationAqc = TranslationAqc.Empty,
        private val isRefreshing: Boolean = false
    ) : QuranTranslationUIState {
        override fun isLoading() = isLoading
        override fun translations() = translations
        override fun isRefreshing() = isRefreshing
    }
}