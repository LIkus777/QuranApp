package com.zaur.features.surah.viewmodel

import com.zaur.core.BaseViewModel
import com.zaur.features.surah.screen.SurahDetailStateManager
import com.zaur.features.surah.ui_state.aqc.SurahDetailScreenState
import kotlinx.coroutines.flow.StateFlow

interface SurahDetailViewModel {

    fun updateState(state: SurahDetailScreenState)
    fun getState(): StateFlow<SurahDetailScreenState>

    fun setSurahNumber(surahNumber: Int)
    fun showReciterDialog(show: Boolean)
    fun showTextBottomSheet(show: Boolean)
    fun showSettingsBottomSheet(show: Boolean)
    fun showArabic(show: Boolean)
    fun showRussian(show: Boolean)
    fun fontSizeArabic(fontSize: Float)
    fun fontSizeRussian(fontSize: Float)
    fun selectedReciter(reciter: String)

    class Base(
        private val stateManager: SurahDetailStateManager
    ) : BaseViewModel(), SurahDetailViewModel {

        override fun updateState(state: SurahDetailScreenState) {
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
    }
}