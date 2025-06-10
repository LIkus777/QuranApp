package com.zaur.features.surah.screen.surah_detail

import androidx.navigation.NavHostController
import com.zaur.features.surah.manager.TranslatorManager
import com.zaur.features.surah.viewmodel.OfflineViewModel
import com.zaur.features.surah.viewmodel.SurahPlayerViewModel
import com.zaur.features.surah.viewmodel.QuranPageViewModel
import com.zaur.features.surah.viewmodel.QuranTextViewModel
import com.zaur.features.surah.viewmodel.QuranTranslationViewModel
import com.zaur.features.surah.viewmodel.ScreenContentViewModel
import com.zaur.features.surah.viewmodel.SurahChooseViewModel
import com.zaur.features.surah.viewmodel.SurahDetailViewModel
import com.zaur.features.surah.viewmodel.ThemeViewModel

/**
 * @author Zaur
 * @since 25.05.2025
 */

interface SurahDetailDependencies {
    fun translatorManager(): TranslatorManager
    fun themeViewModel(): ThemeViewModel
    fun offlineViewModel(): OfflineViewModel
    fun surahChooseViewModel(): SurahChooseViewModel
    fun surahDetailViewModel(): SurahDetailViewModel
    fun quranTextViewModel(): QuranTextViewModel
    fun surahPlayerViewModel(): SurahPlayerViewModel
    fun quranTranslationViewModel(): QuranTranslationViewModel
    fun screenContentViewModel(): ScreenContentViewModel
    fun quranPageViewModel(): QuranPageViewModel
    fun controller(): NavHostController

    data class Base(
        private val translatorManager: TranslatorManager,
        private val themeViewModel: ThemeViewModel,
        private val offlineViewModel: OfflineViewModel,
        private val surahChooseViewModel: SurahChooseViewModel,
        private val surahDetailViewModel: SurahDetailViewModel,
        private val quranTextViewModel: QuranTextViewModel,
        private val surahPlayerViewModel: SurahPlayerViewModel,
        private val quranTranslationViewModel: QuranTranslationViewModel,
        private val screenContentViewModel: ScreenContentViewModel,
        private val quranPageViewModel: QuranPageViewModel,
        private val controller: NavHostController,
    ) : SurahDetailDependencies {
        override fun translatorManager(): TranslatorManager = translatorManager
        override fun themeViewModel() = themeViewModel
        override fun offlineViewModel() = offlineViewModel
        override fun surahChooseViewModel() = surahChooseViewModel
        override fun surahDetailViewModel() = surahDetailViewModel
        override fun quranTextViewModel() = quranTextViewModel
        override fun surahPlayerViewModel() = surahPlayerViewModel
        override fun quranTranslationViewModel() = quranTranslationViewModel
        override fun screenContentViewModel() = screenContentViewModel
        override fun quranPageViewModel() = quranPageViewModel
        override fun controller() = controller
    }
}
