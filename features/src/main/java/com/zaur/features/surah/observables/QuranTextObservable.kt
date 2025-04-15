package com.zaur.features.surah.observables

import com.zaur.features.surah.base.Observable
import com.zaur.features.surah.ui_state.aqc.QuranTextAqcUIState
import kotlinx.coroutines.flow.StateFlow

interface QuranTextObservable : Observable.Mutable<QuranTextAqcUIState> {

    interface Update : Observable.Update<QuranTextAqcUIState>

    interface Read : Observable.Read<QuranTextAqcUIState> {
        fun textState(): StateFlow<QuranTextAqcUIState>
    }

    interface Mutable : Update, Read

    class Base(
        private val initial: QuranTextAqcUIState
    ) : Observable.Abstract<QuranTextAqcUIState>(initial), Mutable {
        override fun textState(): StateFlow<QuranTextAqcUIState> = state()
    }
}