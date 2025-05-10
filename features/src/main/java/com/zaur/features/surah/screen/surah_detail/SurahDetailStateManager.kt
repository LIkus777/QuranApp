package com.zaur.features.surah.screen.surah_detail

import com.zaur.features.surah.base.Observable
import com.zaur.features.surah.ui_state.aqc.SurahDetailScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

interface SurahDetailStateObservable : Observable.Mutable<SurahDetailScreenState> {
    interface Update : Observable.Update<SurahDetailScreenState>

    interface Read : Observable.Read<SurahDetailScreenState> {
        fun surahDetailState(): StateFlow<SurahDetailScreenState>
    }

    interface Mutable : Update, Read

    class Base(
        private val initial: SurahDetailScreenState,
    ) : Observable.Abstract<SurahDetailScreenState>(initial), Mutable {
        override fun surahDetailState(): StateFlow<SurahDetailScreenState> = state()
    }
}

interface SurahDetailStateManager : SurahDetailStateObservable.Mutable {

    fun updateState(state: SurahDetailScreenState.Base)
    fun getState(): StateFlow<SurahDetailScreenState.Base>

    fun setSurahNumber(surahNumber: Int)
    fun showReciterDialog(show: Boolean)
    fun showTextBottomSheet(show: Boolean)
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
    ) : Observable.Abstract<SurahDetailScreenState>(initial), SurahDetailStateManager {

        private val state = MutableStateFlow(initial)

        override fun surahDetailState(): StateFlow<SurahDetailScreenState> = state

        override fun updateState(state: SurahDetailScreenState.Base) {
            this.state.update {
                it.copy(
                    audioPlayerState = state.audioPlayerState(),
                    reciterState = state.reciterState(),
                    uiPreferencesState = state.uiPreferencesState(),
                    bottomSheetState = state.bottomSheetState()
                )
            }
        }

        override fun getState(): StateFlow<SurahDetailScreenState.Base> {
            return state
        }

        override fun setSurahNumber(surahNumber: Int) {
            state.update {
                it.copy(
                    audioPlayerState = it.audioPlayerState().copy(
                        currentSurahNumber = surahNumber
                    )
                )
            }
        }

        override fun showTextBottomSheet(show: Boolean) {
            state.update {
                it.copy(
                    bottomSheetState = it.bottomSheetState.copy(
                        showTextBottomSheet = show
                    )
                )
            }
        }

        override fun showSettingsBottomSheet(show: Boolean) {
            state.update {
                it.copy(
                    bottomSheetState = it.bottomSheetState.copy(showSettingsBottomSheet = show)
                )
            }
        }

        override fun showArabic(show: Boolean) {
            state.update {
                it.copy(uiPreferencesState = it.uiPreferencesState.copy(showArabic = show))
            }
        }

        override fun showRussian(show: Boolean) {
            state.update {
                it.copy(uiPreferencesState = it.uiPreferencesState.copy(showRussian = show))
            }
        }

        override fun fontSizeArabic(fontSize: Float) {
            state.update {
                it.copy(uiPreferencesState = it.uiPreferencesState.copy(fontSizeArabic = fontSize))
            }
        }

        override fun fontSizeRussian(fontSize: Float) {
            state.update {
                it.copy(uiPreferencesState = it.uiPreferencesState.copy(fontSizeRussian = fontSize))
            }
        }

        override fun selectedReciter(reciter: String) {
            state.update {
                it.copy(reciterState = it.reciterState.copy(currentReciter = reciter))
            }
        }

        override fun setAyahInSurahNumber(ayahInSurah: Int) {
            state.update {
                it.copy(audioPlayerState = it.audioPlayerState.copy(currentAyahInSurah = ayahInSurah))
            }
        }

        override fun setOfflineMode(isOffline: Boolean) {
            state.update {
                it.copy(audioPlayerState = it.audioPlayerState.copy(isOfflineMode = isOffline))
            }
        }

        override fun clear() {
            val base = SurahDetailScreenState.Base()
            state.update {
                it.copy(
                    audioPlayerState = base.audioPlayerState,
                    reciterState = base.reciterState,
                    uiPreferencesState = base.uiPreferencesState,
                    bottomSheetState = base.bottomSheetState
                )
            }
        }

        override fun showReciterDialog(show: Boolean) {
            state.update {
                it.copy(reciterState = it.reciterState.copy(showReciterDialog = show))
            }
        }
    }
}


