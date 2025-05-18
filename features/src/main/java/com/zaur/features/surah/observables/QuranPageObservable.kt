package com.zaur.features.surah.observables

import com.zaur.features.surah.base.Observable
import com.zaur.presentation.ui.ui_state.aqc.QuranPageUIState
import kotlinx.coroutines.flow.StateFlow


/**
 * @author Zaur
 * @since 14.05.2025
 */

interface QuranPageObservable : Observable.Mutable<QuranPageUIState> {

    interface Update : Observable.Update<QuranPageUIState.Base>

    interface Read : Observable.Read<QuranPageUIState.Base> {
        fun pageState(): StateFlow<QuranPageUIState.Base>
    }

    interface Mutable : Update, Read

    class Base(
        private val initial: QuranPageUIState.Base,
    ) : Observable.Abstract<QuranPageUIState.Base>(initial), Mutable {
        override fun pageState(): StateFlow<QuranPageUIState.Base> = state()
    }

}