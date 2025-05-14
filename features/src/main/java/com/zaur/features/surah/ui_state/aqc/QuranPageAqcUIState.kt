package com.zaur.features.surah.ui_state.aqc

import com.zaur.domain.al_quran_cloud.models.page.QuranPageAqc


/**
 * @author Zaur
 * @since 14.05.2025
 */

interface QuranPageAqcUIState {

    fun uthmaniPage(): QuranPageAqc
    fun translatedPage(): QuranPageAqc

    data class Base(
        private val uthmaniPage: QuranPageAqc,
        private val translatedPage: QuranPageAqc,
    ) : QuranPageAqcUIState {
        override fun uthmaniPage(): QuranPageAqc = uthmaniPage
        override fun translatedPage(): QuranPageAqc = translatedPage
    }

}