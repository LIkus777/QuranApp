package com.zaur.features.surah.screen

import android.util.Log
import com.zaur.features.surah.ui_state.aqc.SurahDetailScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

interface SurahDetailStateManager {
    fun updateState(state: SurahDetailScreenState)
    fun getState(): StateFlow<SurahDetailScreenState>

    // Обновление конкретных под-состояний
    /*fun updateAudioState(update: AudioPlayerState.() -> AudioPlayerState)
    fun updateReciterState(update: ReciterState.() -> ReciterState)
    fun updateUiPreferences(update: UiPreferencesState.() -> UiPreferencesState)
    fun updateBottomSheetState(update: BottomSheetState.() -> BottomSheetState)*/

    fun showReciterDialog(show: Boolean)
    fun showTextBottomSheet(show: Boolean)
    fun showSettingsBottomSheet(show: Boolean)
    fun showArabic(show: Boolean)
    fun showRussian(show: Boolean)
    fun fontSizeArabic(fontSize: Float)
    fun fontSizeRussian(fontSize: Float)
    fun selectedReciter(reciter: String)

    class Base : SurahDetailStateManager {
        private var state = MutableStateFlow(SurahDetailScreenState())

        override fun updateState(state: SurahDetailScreenState) {
            Log.i("TAGGG", "SurahDetailStateManager updateState: state = $state")
            this.state.update { state }
        }

        override fun getState(): StateFlow<SurahDetailScreenState> {
            return state
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
                it.copy(uiPreferences = it.uiPreferences.copy(showArabic = show))
            }
        }

        override fun showRussian(show: Boolean) {
            state.update {
                it.copy(uiPreferences = it.uiPreferences.copy(showRussian = show))
            }
        }

        override fun fontSizeArabic(fontSize: Float) {
            state.update {
                it.copy(uiPreferences = it.uiPreferences.copy(fontSizeArabic = fontSize))
            }
        }

        override fun fontSizeRussian(fontSize: Float) {
            state.update {
                it.copy(uiPreferences = it.uiPreferences.copy(fontSizeRussian = fontSize))
            }
        }

        override fun selectedReciter(reciter: String) {
            Log.i("TAGGG", "SurahDetailViewModel ЧТЕЦ: reciter = $reciter")
            state.update {
                it.copy(reciterState = it.reciterState.copy(currentReciter = reciter))
            }
        }

        override fun showReciterDialog(show: Boolean) {
            state.update {
                it.copy(reciterState = it.reciterState.copy(showReciterDialog = show))
            }
        }

        /*override fun updateAudioState(update: AudioPlayerState.() -> AudioPlayerState) {
        state = state.copy(audioPlayerState = state.audioPlayerState.update())
    }

    override fun updateReciterState(update: ReciterState.() -> ReciterState) {
        state = state.copy(reciterState = state.reciterState.update())
    }

    override fun updateUiPreferences(update: UiPreferencesState.() -> UiPreferencesState) {
        state = state.copy(uiPreferences = state.uiPreferences.update())
    }

    override fun updateBottomSheetState(update: BottomSheetState.() -> BottomSheetState) {
        state = state.copy(bottomSheetState = state.bottomSheetState.update())
    }*/
    }
}


