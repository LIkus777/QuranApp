package com.zaur.features.surah.viewmodel

import android.util.Log
import com.zaur.core.BaseViewModel
import com.zaur.features.surah.manager.SurahDetailStateManager
import com.zaur.presentation.ui.ui_state.aqc.SurahDetailScreenState
import kotlinx.coroutines.flow.StateFlow

/**
 * @author Zaur
 * @since 2025-05-12
 */

interface SurahDetailViewModel {

    fun updateState(state: SurahDetailScreenState.Base)
    fun surahDetailState(): StateFlow<SurahDetailScreenState>

    fun setSurahName(name: String)
    fun setSurahNumber(surahNumber: Int)
    fun showReciterDialog(show: Boolean)
    fun showPlayerBottomSheet(show: Boolean)
    fun showTextBottomSheet(show: Boolean)
    fun showSurahMode(show: Boolean)
    fun showPageMode(show: Boolean)
    fun showSettingsBottomSheet(show: Boolean)
    fun showArabic(show: Boolean)
    fun showRussian(show: Boolean)
    fun fontSizeArabic(fontSize: Float)
    fun fontSizeRussian(fontSize: Float)
    fun selectedReciter(reciter: String, reciterName: String)
    fun setAyahInAudio(ayahInSurah: Int)
    fun setAyahInText(ayah: Int)
    fun setOfflineMode(isOffline: Boolean)

    fun clear()

    class Base(
        private val stateManager: SurahDetailStateManager,
    ) : BaseViewModel(), SurahDetailViewModel {

        override fun updateState(state: SurahDetailScreenState.Base) {
            stateManager.updateState(state)
        }

        override fun surahDetailState(): StateFlow<SurahDetailScreenState> {
            val state = stateManager.surahDetailState()
            Log.w("TAG", "SurahDetailViewModel: state $state", )
            return state
        }

        override fun setSurahName(name: String) {
            stateManager.setSurahName(name)
        }

        override fun setSurahNumber(surahNumber: Int) {
            stateManager.setSurahNumber(surahNumber)
        }

        override fun showReciterDialog(show: Boolean) {
            stateManager.showReciterDialog(show)
        }

        override fun showPlayerBottomSheet(show: Boolean) {
            stateManager.showPlayerBottomSheet(show)
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

        override fun selectedReciter(reciter: String, reciterName: String) {
            stateManager.selectedReciter(reciter, reciterName)
        }

        override fun setAyahInAudio(ayahInSurah: Int) {
            stateManager.setAyahInAudio(ayahInSurah)
        }

        override fun setAyahInText(ayah: Int) {
            stateManager.setAyahInText(ayah)
        }

        override fun setOfflineMode(isOffline: Boolean) {
            stateManager.setOfflineMode(isOffline)
        }

        override fun clear() {
            stateManager.clear()
        }
    }
}