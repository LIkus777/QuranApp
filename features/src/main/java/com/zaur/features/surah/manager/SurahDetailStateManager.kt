package com.zaur.features.surah.manager

import com.zaur.features.surah.base.Observable
import com.zaur.features.surah.observables.SurahDetailStateObservable
import com.zaur.presentation.ui.ui_state.aqc.SurahDetailScreenState
import kotlinx.coroutines.flow.StateFlow

/**
 * @author Zaur
 * @since 2025-05-12
 */

interface SurahDetailStateManager : SurahDetailStateObservable.Read {

    fun updateState(state: SurahDetailScreenState.Base)

    fun setTextSurahName(name: String)


    fun setTextSurahNumber(surahNumber: Int)
    fun showTranslatorDialog(show: Boolean)
    fun showTranscriptionDialog(show: Boolean)
    fun showRepeatDialog(show: Boolean)
    fun showReciterDialog(show: Boolean)
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

    fun updateAyahAndSurah(ayah: Int, surah: Int)

    fun clear()

    class Base(
        private val initial: SurahDetailScreenState.Base = SurahDetailScreenState.Base(),
        private val observable: SurahDetailStateObservable.Mutable = SurahDetailStateObservable.Base(
            initial
        ),
    ) : Observable.Abstract<SurahDetailScreenState.Base>(initial), SurahDetailStateManager {

        internal inline fun update(block: SurahDetailScreenState.Base.() -> SurahDetailScreenState.Base) {
            val current = observable.surahDetailState().value
            observable.update(current.block())
        }

        override fun surahDetailState(): StateFlow<SurahDetailScreenState.Base> =
            observable.surahDetailState()

        override fun updateState(state: SurahDetailScreenState.Base) = with(state) {
            observable.update(
                observable.surahDetailState().value.copy(
                    textState = textState(),
                    reciterState = reciterState(),
                    uiPreferencesState = uiPreferencesState(),
                    bottomSheetState = bottomSheetState()
                )
            )
        }

        override fun setTextSurahName(name: String) = update {
            copy(textState = textState().copy(surahName = name))
        }

        override fun setTextSurahNumber(surahNumber: Int) = update {
            copy(textState = textState().copy(currentSurahNumber = surahNumber))
        }

        override fun showTranslatorDialog(show: Boolean) = update {
            copy(bottomSheetState = bottomSheetState().copy(showTranslatorDialog = show))
        }

        override fun showTranscriptionDialog(show: Boolean) = update {
            copy(bottomSheetState = bottomSheetState().copy(showTranscriptionSheet = show))
        }

        override fun showRepeatDialog(show: Boolean) = update {
            copy(bottomSheetState = bottomSheetState().copy(showRepeatDialog = show))
        }

        override fun showTextBottomSheet(show: Boolean) = update {
            copy(bottomSheetState = bottomSheetState().copy(showTextBottomSheet = show))
        }

        override fun showSurahMode(show: Boolean) = update {
            copy(uiPreferencesState = uiPreferencesState().copy(showSurahMode = show))
        }

        override fun showPageMode(show: Boolean) = update {
            copy(uiPreferencesState = uiPreferencesState().copy(showPageMode = show))
        }

        override fun showSettingsBottomSheet(show: Boolean) = update {
            copy(bottomSheetState = bottomSheetState().copy(showSettingsBottomSheet = show))
        }

        override fun showArabic(show: Boolean) = update {
            copy(uiPreferencesState = uiPreferencesState().copy(showArabic = show))
        }

        override fun showTranscription(show: Boolean) = update {
            copy(uiPreferencesState = uiPreferencesState().copy(showTranscription = show))
        }

        override fun showRussian(show: Boolean) = update {
            copy(uiPreferencesState = uiPreferencesState().copy(showRussian = show))
        }

        override fun fontSizeArabic(fontSize: Float) = update {
            copy(uiPreferencesState = uiPreferencesState().copy(fontSizeArabic = fontSize))
        }

        override fun fontSizeRussian(fontSize: Float) = update {
            copy(uiPreferencesState = uiPreferencesState().copy(fontSizeRussian = fontSize))
        }

        override fun selectedReciter(reciter: String, reciterName: String) = update {
            copy(
                reciterState = reciterState().copy(
                    currentReciter = reciter, currentReciterName = reciterName
                )
            )
        }

        override fun selectedTranslator(translator: String, translatorName: String) = update {
            copy(
                translatorState = translatorState().copy(
                    translator = translator, translatorName = translatorName
                )
            )
        }

        override fun setAyahInText(ayah: Int) = update {
            copy(textState = textState().copy(currentAyah = ayah))
        }

        override fun updateAyahAndSurah(ayah: Int, surah: Int) =
            update { //todo реализовать и для surahPlayerState
                copy(
                    textState = textState().copy(currentAyah = ayah, currentSurahNumber = surah),
                )
            }

        override fun clear() {
            val base = SurahDetailScreenState.Base()
            update {
                copy(
                    textState = base.textState(),
                    reciterState = base.reciterState(),
                    uiPreferencesState = base.uiPreferencesState(),
                    bottomSheetState = base.bottomSheetState()
                )
            }
        }

        override fun showReciterDialog(show: Boolean) = update {
            copy(bottomSheetState = bottomSheetState().copy(showReciterDialog = show))
        }

        override fun showPlayerBottomSheet(show: Boolean) = update {
            copy(bottomSheetState = bottomSheetState().copy(showPlayerBottomSheet = show))
        }
    }
}