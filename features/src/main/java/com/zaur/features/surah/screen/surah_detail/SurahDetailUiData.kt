package com.zaur.features.surah.screen.surah_detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.zaur.features.surah.ui_state.aqc.QuranAudioAqcUIState
import com.zaur.features.surah.ui_state.aqc.QuranPageAqcUIState
import com.zaur.features.surah.ui_state.aqc.QuranTextAqcUIState
import com.zaur.features.surah.ui_state.aqc.QuranTranslationAqcUIState
import com.zaur.features.surah.ui_state.aqc.SurahDetailScreenState
import com.zaur.features.surah.ui_state.offline.OfflineUIState
import com.zaur.features.surah.viewmodel.OfflineViewModel
import com.zaur.features.surah.viewmodel.QuranAudioViewModel
import com.zaur.features.surah.viewmodel.QuranPageViewModel
import com.zaur.features.surah.viewmodel.QuranTextViewModel
import com.zaur.features.surah.viewmodel.QuranTranslationViewModel
import com.zaur.features.surah.viewmodel.ScreenContentViewModel
import com.zaur.features.surah.viewmodel.SurahChooseViewModel
import com.zaur.features.surah.viewmodel.SurahDetailViewModel
import com.zaur.features.surah.viewmodel.ThemeViewModel

/**
 * @author Zaur
 * @since 15.05.2025
 */

interface SurahDetailUiData {
    fun offlineState(): OfflineUIState
    fun textState(): QuranTextAqcUIState
    fun audioState(): QuranAudioAqcUIState
    fun translateState(): QuranTranslationAqcUIState
    fun surahDetailState(): SurahDetailScreenState
    fun pageState(): QuranPageAqcUIState
    fun isDarkTheme(): Boolean
    fun isSurahMode(): Boolean

    data class Base(
        private val offlineState: OfflineUIState,
        private val textState: QuranTextAqcUIState,
        private val audioState: QuranAudioAqcUIState,
        private val translateState: QuranTranslationAqcUIState,
        private val surahDetailState: SurahDetailScreenState,
        private val pageState: QuranPageAqcUIState,
        private val isDarkTheme: Boolean,
        private val isSurahMode: Boolean,
    ) : SurahDetailUiData {
        override fun offlineState() = offlineState
        override fun textState() = textState
        override fun audioState() = audioState
        override fun translateState() = translateState
        override fun surahDetailState() = surahDetailState
        override fun pageState() = pageState
        override fun isDarkTheme() = isDarkTheme
        override fun isSurahMode() = isSurahMode
    }
}

interface SurahDetailDependencies {
    fun themeViewModel(): ThemeViewModel
    fun offlineViewModel(): OfflineViewModel
    fun surahChooseViewModel(): SurahChooseViewModel
    fun surahDetailViewModel(): SurahDetailViewModel
    fun quranTextViewModel(): QuranTextViewModel
    fun quranAudioViewModel(): QuranAudioViewModel
    fun quranTranslationViewModel(): QuranTranslationViewModel
    fun screenContentViewModel(): ScreenContentViewModel
    fun quranPageViewModel(): QuranPageViewModel
    fun controller(): NavHostController

    data class Base(
        private val themeViewModel: ThemeViewModel,
        private val offlineViewModel: OfflineViewModel,
        private val surahChooseViewModel: SurahChooseViewModel,
        private val surahDetailViewModel: SurahDetailViewModel,
        private val quranTextViewModel: QuranTextViewModel,
        private val quranAudioViewModel: QuranAudioViewModel,
        private val quranTranslationViewModel: QuranTranslationViewModel,
        private val screenContentViewModel: ScreenContentViewModel,
        private val quranPageViewModel: QuranPageViewModel,
        private val controller: NavHostController,
    ) : SurahDetailDependencies {
        override fun themeViewModel() = themeViewModel
        override fun offlineViewModel() = offlineViewModel
        override fun surahChooseViewModel() = surahChooseViewModel
        override fun surahDetailViewModel() = surahDetailViewModel
        override fun quranTextViewModel() = quranTextViewModel
        override fun quranAudioViewModel() = quranAudioViewModel
        override fun quranTranslationViewModel() = quranTranslationViewModel
        override fun screenContentViewModel() = screenContentViewModel
        override fun quranPageViewModel() = quranPageViewModel
        override fun controller() = controller
    }
}

@Composable
fun rememberSurahDetailUiData(
    deps: SurahDetailDependencies,
): SurahDetailUiData {
    val offlineState by deps.offlineViewModel().offlineState().collectAsState()
    val textState by deps.quranTextViewModel().textState().collectAsState()
    val audioState by deps.quranAudioViewModel().audioState().collectAsState()
    val translateState by deps.quranTranslationViewModel().translationState().collectAsState()
    val surahDetailState by deps.surahDetailViewModel().surahDetailState().collectAsState()
    val pageState by deps.quranPageViewModel().pageState().collectAsState()
    val isDarkTheme = deps.themeViewModel().getIsDarkTheme()
    val isSurahMode = surahDetailState.uiPreferencesState().showSurahMode()

    return SurahDetailUiData.Base(
        offlineState,
        textState,
        audioState,
        translateState,
        surahDetailState,
        pageState,
        isDarkTheme,
        isSurahMode,
    )
}