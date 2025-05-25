package com.zaur.features.surah.screen.surah_detail

import com.zaur.presentation.ui.ui_state.aqc.QuranAudioUIState
import com.zaur.presentation.ui.ui_state.aqc.QuranPageUIState
import com.zaur.presentation.ui.ui_state.aqc.QuranTextUIState
import com.zaur.presentation.ui.ui_state.aqc.QuranTranslationUIState
import com.zaur.presentation.ui.ui_state.aqc.SurahDetailScreenState
import com.zaur.presentation.ui.ui_state.offline.OfflineUIState

/**
 * @author Zaur
 * @since 15.05.2025
 */

interface SurahDetailUiData {
    fun offlineState(): OfflineUIState
    fun textState(): QuranTextUIState
    fun audioState(): QuranAudioUIState
    fun translateState(): QuranTranslationUIState
    fun surahDetailState(): SurahDetailScreenState
    fun pageState(): QuranPageUIState
    fun isDarkTheme(): Boolean
    fun isSurahMode(): Boolean

    data class Base(
        private val offlineState: OfflineUIState,
        private val textState: QuranTextUIState,
        private val audioState: QuranAudioUIState,
        private val translateState: QuranTranslationUIState,
        private val surahDetailState: SurahDetailScreenState,
        private val pageState: QuranPageUIState,
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