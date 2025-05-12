package com.zaur.features.surah.viewmodel

import com.zaur.core.BaseViewModel
import com.zaur.domain.al_quran_cloud.use_case.OfflineUseCase
import com.zaur.features.surah.screen.surah_detail.SurahDetailStateManager
import com.zaur.features.surah.ui_state.aqc.SurahDetailScreenState
import kotlinx.coroutines.flow.StateFlow

/**
* @author Zaur
* @since 2025-05-12
*/

interface SurahDetailViewModel {

    fun updateState(state: SurahDetailScreenState.Base)
    fun getState(): StateFlow<SurahDetailScreenState>

    fun setSurahNumber(surahNumber: Int)
    fun showReciterDialog(show: Boolean)
    fun showTextBottomSheet(show: Boolean)
    fun showSurahMode(show: Boolean)
    fun showPageMode(show: Boolean)
    fun showSettingsBottomSheet(show: Boolean)
    fun showArabic(show: Boolean)
    fun showRussian(show: Boolean)
    fun fontSizeArabic(fontSize: Float)
    fun fontSizeRussian(fontSize: Float)
    fun selectedReciter(reciter: String)
    fun setAyahInSurahNumber(ayahInSurah: Int)
    fun setOfflineMode(isOffline: Boolean)

    fun clear()

    class Base(
        private val stateManager: SurahDetailStateManager,
    ) : BaseViewModel(), SurahDetailViewModel {

        override fun updateState(state: SurahDetailScreenState.Base) {
            stateManager.updateState(state)
        }

        override fun getState(): StateFlow<SurahDetailScreenState> = stateManager.getState()

        override fun setSurahNumber(surahNumber: Int) {
            stateManager.setSurahNumber(surahNumber)
        }

        override fun showReciterDialog(show: Boolean) {
            stateManager.showReciterDialog(show)
        }

        override fun showTextBottomSheet(show: Boolean) {
            stateManager.showTextBottomSheet(show)
        }

        override fun showSurahMode(show: Boolean) {
            stateManager.showSurahMode(show)
            stateManager.showPageMode(!show)
        }

        override fun showPageMode(show: Boolean) {
            stateManager.showPageMode(show)
            stateManager.showSurahMode(!show)
        }

        override fun showSettingsBottomSheet(show: Boolean) {
            stateManager.showSettingsBottomSheet(show)
        }

        override fun showArabic(show: Boolean) {
            stateManager.showArabic(show)
        }

        override fun showRussian(show: Boolean) {
            stateManager.showRussian(show)
        }

        override fun fontSizeArabic(fontSize: Float) {
            stateManager.fontSizeArabic(fontSize)
        }

        override fun fontSizeRussian(fontSize: Float) {
            stateManager.fontSizeRussian(fontSize)
        }

        override fun selectedReciter(reciter: String) {
            stateManager.selectedReciter(reciter)
        }

        override fun setAyahInSurahNumber(ayahInSurah: Int) {
            stateManager.setAyahInSurahNumber(ayahInSurah)
        }

        override fun setOfflineMode(isOffline: Boolean) {
            stateManager.setOfflineMode(isOffline)
        }

        override fun clear() {
            stateManager.clear()
        }
    }
}