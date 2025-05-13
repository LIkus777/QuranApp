package com.zaur.features.surah.observables

import com.zaur.features.surah.base.Observable
import com.zaur.features.surah.ui_state.SurahDetailUiState
import kotlinx.coroutines.flow.StateFlow


/**
 * @author Zaur
 * @since 13.05.2025
 */

interface SurahModeObservable : Observable.Mutable<SurahDetailUiState> {

    interface Update : Observable.Update<SurahDetailUiState>

    interface Read : Observable.Read<SurahDetailUiState> {
        fun surahMode(): StateFlow<SurahDetailUiState>
    }

    interface Mutable : Update, Read

    class Base(
        private val initial: SurahDetailUiState = SurahDetailUiState.Loading
    ) : Observable.Abstract<SurahDetailUiState>(initial), Mutable {
        override fun surahMode(): StateFlow<SurahDetailUiState> = state()
    }

}