package com.zaur.features.surah.viewmodel

import android.util.Log
import com.zaur.core.BaseViewModel
import com.zaur.features.surah.manager.SurahDetailStateManager
import com.zaur.features.surah.observables.AnimatedMenuObservable
import com.zaur.features.surah.observables.SurahModeObservable
import com.zaur.features.surah.ui_state.AnimatedMenuUiState
import com.zaur.features.surah.ui_state.AyaListItem
import com.zaur.features.surah.ui_state.SurahDetailUiState
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
            fetchSurahMode() //fixme
        }

        override fun surahMode(): StateFlow<SurahDetailUiState> = surahModeObservable.surahMode()

        override fun animatedMenu(): StateFlow<AnimatedMenuUiState> =
            animatedMenuObservable.animatedMenu()

        private val surahDetailState = surahDetailStateManager.getState()

        override fun fetchAyaListItem(isLoading: Boolean, chapterNumber: Int): AyaListItem {
            return if (isLoading) {
                AyaListItem.Loading
            } else {
                AyaListItem.AyahListItem
            }
        }

        override fun fetchSurahMode() { //todo сделать сохранение и вынимание в prefs и пофиксить креш
            Log.i("TAG", "fetchSurahMode: value - ${surahDetailState.value.uiPreferencesState}")
            val isSurahMode =
                surahDetailState.value.uiPreferencesState.showSurahMode() == true
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