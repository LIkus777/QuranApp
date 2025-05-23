package com.zaur.features.surah.viewmodel

import com.zaur.core.BaseViewModel
import com.zaur.features.surah.manager.SurahDetailStateManager
import com.zaur.features.surah.observables.AnimatedMenuObservable
import com.zaur.features.surah.observables.SurahModeObservable
import com.zaur.presentation.ui.ui_state.AnimatedMenuUiState
import com.zaur.presentation.ui.ui_state.AyaListItem
import com.zaur.presentation.ui.ui_state.SurahDetailUiState
import kotlinx.coroutines.flow.StateFlow

/**
 * @author Zaur
 * @since 13.05.2025
 */

interface ScreenContentViewModel {

    fun surahMode(): StateFlow<SurahDetailUiState>
    fun animatedMenu(): StateFlow<AnimatedMenuUiState>

    fun fetchAyaListItem(isLoading: Boolean, chapterNumber: Int): AyaListItem

    fun fetchSurahMode()

    class Base(
        private val surahDetailStateManager: SurahDetailStateManager,
        private val surahModeObservable: SurahModeObservable.Mutable,
        private val animatedMenuObservable: AnimatedMenuObservable.Mutable,
    ) : BaseViewModel(), ScreenContentViewModel {

        init {
            fetchSurahMode()
        }

        override fun surahMode(): StateFlow<SurahDetailUiState> = surahModeObservable.surahMode()

        override fun animatedMenu(): StateFlow<AnimatedMenuUiState> =
            animatedMenuObservable.animatedMenu()

        override fun fetchAyaListItem(isLoading: Boolean, chapterNumber: Int): AyaListItem {
            return if (isLoading) {
                AyaListItem.Loading
            } else {
                AyaListItem.AyahListItem
            }
        }

        override fun fetchSurahMode() { //todo сделать сохранение и вынимание в prefs и пофиксить креш
            val isSurahMode =
                surahDetailStateManager.surahDetailState().value.uiPreferencesState().showSurahMode() == true
            if (isSurahMode) {
                surahModeObservable.update(
                    SurahDetailUiState.SurahModeState
                )
            } else {
                surahModeObservable.update(
                    SurahDetailUiState.PageModeState
                )
            }
        }
    }

}