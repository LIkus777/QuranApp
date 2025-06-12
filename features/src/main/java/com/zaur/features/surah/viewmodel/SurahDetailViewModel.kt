package com.zaur.features.surah.viewmodel

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

    fun setTextSurahName(name: String)


    fun setTextSurahNumber(surahNumber: Int)
    fun showRepeatDialog(show: Boolean)
    fun showReciterDialog(show: Boolean)
    fun showTranslatorDialog(show: Boolean)
    fun showTranscriptionDialog(show: Boolean)
    fun showPlayerBottomSheet(show: Boolean)
    fun showTextBottomSheet(show: Boolean)
    fun showSurahMode(show: Boolean)
    fun showPageMode(show: Boolean)
    fun showSettingsBottomSheet(show: Boolean)
    fun showArabic(show: Boolean)
    fun showTranscription(show: Boolean)
    fun showRussian(show: Boolean)
    fun fontSizeArabic(fontSize: Float)
    fun fontSizeRussian(fontSize: Float)
    fun selectedReciter(reciter: String, reciterName: String)
    fun selectedTranslator(translator: String, translatorName: String)

    fun setAyahInText(ayah: Int)

    fun clear()

    class Base(
        private val stateManager: SurahDetailStateManager,
    ) : BaseViewModel(), SurahDetailViewModel {

        override fun updateState(state: SurahDetailScreenState.Base) {
            stateManager.updateState(state)
        }

        override fun surahDetailState(): StateFlow<SurahDetailScreenState> =
            stateManager.surahDetailState()

        override fun setTextSurahName(name: String) {
            stateManager.setTextSurahName(name)
        }

        override fun setTextSurahNumber(surahNumber: Int) {
            stateManager.setTextSurahNumber(surahNumber)
        }

        override fun showRepeatDialog(show: Boolean) {
            stateManager.showRepeatDialog(show)
        }

        override fun showReciterDialog(show: Boolean) {
            stateManager.showReciterDialog(show)
        }

        override fun showTranslatorDialog(show: Boolean) {
            stateManager.showTranslatorDialog(show)
        }

        override fun showTranscriptionDialog(show: Boolean) {
            stateManager.showTranscriptionDialog(show)
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

        override fun showTranscription(show: Boolean) {
            stateManager.showTranscription(show)
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

        override fun selectedTranslator(translator: String, translatorName: String) {
            stateManager.selectedTranslator(translator, translatorName)
        }

        override fun setAyahInText(ayah: Int) {
            stateManager.setAyahInText(ayah)
        }


        override fun clear() {
            stateManager.clear()
        }
    }
}