package com.zaur.features.surah.observables

import com.zaur.features.surah.base.Observable
import com.zaur.presentation.ui.ui_state.aqc.QuranTextUIState
import com.zaur.presentation.ui.ui_state.aqc.QuranTextUIState.Base
import kotlinx.coroutines.flow.StateFlow

/**
* @author Zaur
* @since 2025-05-12
*/

interface QuranTextObservable : Observable.Mutable<Base> {

    interface Update : Observable.Update<QuranTextUIState.Base>

    interface Read : Observable.Read<QuranTextUIState.Base> {
        fun textState(): StateFlow<QuranTextUIState.Base>
    }

    interface Mutable : Update, Read

    class Base(
        private val initial: QuranTextUIState.Base,
    ) : Observable.Abstract<QuranTextUIState.Base>(initial), Mutable {
        override fun textState(): StateFlow<QuranTextUIState.Base> = state()
    }
}