package com.zaur.features.surah.viewmodel

import android.util.Log
import com.zaur.core.BaseViewModel
import com.zaur.features.surah.screen.SurahDetailStateManager
import com.zaur.features.surah.ui_state.aqc.SurahDetailScreenState
import kotlinx.coroutines.flow.StateFlow

interface SurahDetailViewModel : SurahDetailStateManager {

    fun toggleAudioPlaying()
    fun surahDetailUiState(): StateFlow<SurahDetailScreenState>
    fun updateState(transform: SurahDetailScreenState.() -> SurahDetailScreenState)

    class Base(
        private val stateManager: SurahDetailStateManager
    ) : BaseViewModel(), SurahDetailViewModel {

        override fun surahDetailUiState(): StateFlow<SurahDetailScreenState> = stateManager.getState()

        override fun updateState(transform: SurahDetailScreenState.() -> SurahDetailScreenState) {
            val newState = stateManager.getState().value.transform()
            stateManager.updateState(newState)
            Log.i("TAGGG", "SurahDetailViewModel updateState: newState = $newState")
        }

        override fun toggleAudioPlaying() {
            updateState { copy(audioPlayerState = audioPlayerState.copy(isAudioPlaying = !audioPlayerState.isAudioPlaying)) }
        }

        override fun updateState(state: SurahDetailScreenState) {
            stateManager.updateState(state)
        }

        override fun getState(): StateFlow<SurahDetailScreenState> = stateManager.getState()

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