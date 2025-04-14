package com.zaur.features.surah.observables

import androidx.compose.runtime.State
import com.zaur.features.surah.base.Observable
import com.zaur.features.surah.ui_state.aqc.QuranTextAqcUIState

interface QuranTextObservable : Observable.Mutable<QuranTextAqcUIState> {

    interface Update : Observable.Update<QuranTextAqcUIState> {

    }

    interface Read : Observable.Read<QuranTextAqcUIState> {
        fun textState(): State<QuranTextAqcUIState>
    }

    interface Mutable : Update, Read

    class Base(
        private val initial: QuranTextAqcUIState
    ) : Observable.Abstract<QuranTextAqcUIState>(initial), Mutable {
        override fun textState(): State<QuranTextAqcUIState> = state()
    }
}