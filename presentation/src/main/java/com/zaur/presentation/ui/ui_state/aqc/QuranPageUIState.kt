package com.zaur.presentation.ui.ui_state.aqc

import com.zaur.domain.al_quran_cloud.models.page.QuranPage


/**
 * @author Zaur
 * @since 14.05.2025
 */

interface QuranPageUIState {

    fun uthmaniPage(): QuranPage
    fun translatedPage(): QuranPage

    data class Base(
        private val uthmaniPage: QuranPage = QuranPage.Empty,
        private val translatedPage: QuranPage = QuranPage.Empty,
    ) : QuranPageUIState {
        override fun uthmaniPage(): QuranPage = uthmaniPage
        override fun translatedPage(): QuranPage = translatedPage
    }

}