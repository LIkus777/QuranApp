package com.zaur.features.surah.observables

import com.zaur.features.surah.base.Observable
import com.zaur.presentation.ui.ui_state.aqc.SurahPlayerUIState
import com.zaur.presentation.ui.ui_state.aqc.SurahPlayerUIState.Base
import kotlinx.coroutines.flow.StateFlow

/**
* @author Zaur
* @since 2025-05-12
*/

interface SurahPlayerObservable : Observable.Mutable<Base> {

    interface Update : Observable.Update<SurahPlayerUIState.Base>

    interface Read : Observable.Read<SurahPlayerUIState.Base> {
        fun audioState(): StateFlow<SurahPlayerUIState.Base>
    }

    interface Mutable : Update, Read

    class Base(
        private val initial: SurahPlayerUIState.Base
    ) : Observable.Abstract<SurahPlayerUIState.Base>(initial), Mutable {
        override fun audioState(): StateFlow<SurahPlayerUIState.Base> = state()
    }

}