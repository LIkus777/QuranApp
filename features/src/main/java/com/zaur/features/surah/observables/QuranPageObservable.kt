package com.zaur.features.surah.observables

import com.zaur.features.surah.base.Observable
import com.zaur.features.surah.ui_state.aqc.QuranPageAqcUIState
import kotlinx.coroutines.flow.StateFlow


/**
 * @author Zaur
 * @since 14.05.2025
 */

interface QuranPageObservable : Observable.Mutable<QuranPageAqcUIState> {

    interface Update : Observable.Update<QuranPageAqcUIState.Base>

    interface Read : Observable.Read<QuranPageAqcUIState.Base> {
        fun pageState(): StateFlow<QuranPageAqcUIState.Base>
    }

    interface Mutable : Update, Read

    class Base(
        private val initial: QuranPageAqcUIState.Base,
    ) : Observable.Abstract<QuranPageAqcUIState.Base>(initial), Mutable {
        override fun pageState(): StateFlow<QuranPageAqcUIState.Base> = state()
    }

}