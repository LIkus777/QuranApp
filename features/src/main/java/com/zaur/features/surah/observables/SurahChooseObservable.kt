package com.zaur.features.surah.observables

import com.zaur.features.surah.base.Observable
import com.zaur.presentation.ui.ui_state.aqc.QuranTextAqcUIState
import kotlinx.coroutines.flow.StateFlow

/**
* @author Zaur
* @since 2025-05-12
*/

interface SurahChooseObservable : Observable.Mutable<QuranTextAqcUIState.Base> {

    interface Update : Observable.Update<QuranTextAqcUIState.Base>

    interface Read : Observable.Read<QuranTextAqcUIState.Base> {
        fun textState(): StateFlow<QuranTextAqcUIState.Base>
    }

    interface Mutable : Update, Read

    class Base(
        private val initial: QuranTextAqcUIState.Base
    ) : Observable.Abstract<QuranTextAqcUIState.Base>(initial), Mutable {
        override fun textState(): StateFlow<QuranTextAqcUIState.Base> = state()
    }


}