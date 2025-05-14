package com.zaur.features.surah.manager

import com.zaur.features.surah.base.Observable
import com.zaur.features.surah.ui_state.aqc.SurahDetailScreenState
import kotlinx.coroutines.flow.StateFlow

/**
 * @author Zaur
 * @since 2025-05-12
 */

interface SurahDetailStateObservable : Observable.Mutable<SurahDetailScreenState.Base> {
    interface Update : Observable.Update<SurahDetailScreenState.Base>

    interface Read : Observable.Read<SurahDetailScreenState.Base> {
        fun surahDetailState(): StateFlow<SurahDetailScreenState.Base>
    }

    interface Mutable : Update, Read

    class Base(
        private val initial: SurahDetailScreenState.Base,
    ) : Observable.Abstract<SurahDetailScreenState.Base>(initial), Mutable {
        override fun surahDetailState(): StateFlow<SurahDetailScreenState.Base> = state()
    }
}

interface SurahDetailStateManager : SurahDetailStateObservable.Read {

    fun updateState(state: SurahDetailScreenState.Base)

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
        private val initial: SurahDetailScreenState.Base = SurahDetailScreenState.Base(),
        private val observable: SurahDetailStateObservable.Mutable = SurahDetailStateObservable.Base(
            initial
        ),
    ) : Observable.Abstract<SurahDetailScreenState.Base>(initial), SurahDetailStateManager {

        override fun surahDetailState(): StateFlow<SurahDetailScreenState.Base> =
            observable.surahDetailState()
        
        override fun updateState(state: SurahDetailScreenState.Base) {
            observable.update(
                observable.surahDetailState().value.copy(
                    audioPlayerState = state.audioPlayerState(),
                    reciterState = state.reciterState(),
                    uiPreferencesState = state.uiPreferencesState(),
                    bottomSheetState = state.bottomSheetState()
                )
            )
        }

        override fun setSurahNumber(surahNumber: Int) {
            observable.update(
                observable.surahDetailState().value.copy(
                    audioPlayerState = observable.surahDetailState().value.audioPlayerState().copy(
                        currentSurahNumber = surahNumber
                    )
                )
            )
        }

        override fun showTextBottomSheet(show: Boolean) {
            observable.update(
                observable.surahDetailState().value.copy(
                    bottomSheetState = observable.surahDetailState().value.bottomSheetState.copy(
                        showTextBottomSheet = show
                    )
                )
            )
        }

        override fun showSurahMode(show: Boolean) {
            observable.update(
                observable.surahDetailState().value.copy(
                    uiPreferencesState = observable.surahDetailState().value.uiPreferencesState.copy(
                        showSurahMode = show
                    )
                )
            )
        }

        override fun showPageMode(show: Boolean) {
            observable.update(
                observable.surahDetailState().value.copy(
                    uiPreferencesState = observable.surahDetailState().value.uiPreferencesState.copy(
                        showPageMode = show
                    )
                )
            )
        }

        override fun showSettingsBottomSheet(show: Boolean) {
            observable.update(
                observable.surahDetailState().value.copy(
                    bottomSheetState = observable.surahDetailState().value.bottomSheetState.copy(
                        showSettingsBottomSheet = show
                    )
                )
            )
        }

        override fun showArabic(show: Boolean) {
            observable.update(
                observable.surahDetailState().value.copy(
                    uiPreferencesState = observable.surahDetailState().value.uiPreferencesState.copy(
                        showArabic = show
                    )
                )
            )
        }

        override fun showRussian(show: Boolean) {
            observable.update(
                observable.surahDetailState().value.copy(
                    uiPreferencesState = observable.surahDetailState().value.uiPreferencesState.copy(
                        showRussian = show
                    )
                )
            )
        }

        override fun fontSizeArabic(fontSize: Float) {
            observable.update(
                observable.surahDetailState().value.copy(
                    uiPreferencesState = observable.surahDetailState().value.uiPreferencesState.copy(
                        fontSizeArabic = fontSize
                    )
                )
            )
        }

        override fun fontSizeRussian(fontSize: Float) {
            observable.update(
                observable.surahDetailState().value.copy(
                    uiPreferencesState = observable.surahDetailState().value.uiPreferencesState.copy(
                        fontSizeRussian = fontSize
                    )
                )
            )
        }

        override fun selectedReciter(reciter: String) {
            observable.update(
                observable.surahDetailState().value.copy(
                    reciterState = observable.surahDetailState().value.reciterState.copy(
                        currentReciter = reciter
                    )
                )
            )
        }

        override fun setAyahInSurahNumber(ayahInSurah: Int) {
            observable.update(
                observable.surahDetailState().value.copy(
                    audioPlayerState = observable.surahDetailState().value.audioPlayerState.copy(
                        currentAyahInSurah = ayahInSurah
                    )
                )
            )
        }

        override fun setOfflineMode(isOffline: Boolean) {
            observable.update(
                observable.surahDetailState().value.copy(
                    audioPlayerState = observable.surahDetailState().value.audioPlayerState.copy(
                        isOfflineMode = isOffline
                    )
                )
            )
        }

        override fun clear() {
            val base = SurahDetailScreenState.Base()
            observable.update(
                observable.surahDetailState().value.copy(
                    audioPlayerState = base.audioPlayerState,
                    reciterState = base.reciterState,
                    uiPreferencesState = base.uiPreferencesState,
                    bottomSheetState = base.bottomSheetState
                )
            )
        }

        override fun showReciterDialog(show: Boolean) {
            observable.update(
                observable.surahDetailState().value.copy(
                    reciterState = observable.surahDetailState().value.reciterState.copy(
                        showReciterDialog = show
                    )
                )
            )
        }
    }
}


