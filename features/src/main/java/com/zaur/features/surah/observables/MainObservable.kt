package com.zaur.features.surah.observables

import com.zaur.features.surah.base.Observable
import com.zaur.presentation.ui.ui_state.main.MainState
import kotlinx.coroutines.flow.StateFlow

/**
* @author Zaur
* @since 2025-05-12
*/

interface MainObservable : Observable.Mutable<MainState> {

    interface Update : Observable.Update<MainState>

    interface Read : Observable.Read<MainState> {
        fun quranState(): StateFlow<MainState>
    }

    interface Mutable : Update, Read

    class Base(
        private val initial: MainState
    ) : Observable.Abstract<MainState>(initial), Mutable {
        override fun quranState(): StateFlow<MainState> = state()
    }

}