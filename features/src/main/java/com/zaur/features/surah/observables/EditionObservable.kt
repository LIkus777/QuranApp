package com.zaur.features.surah.observables

import com.zaur.features.surah.base.Observable
import com.zaur.presentation.ui.ui_state.aqc.EditionUIState
import kotlinx.coroutines.flow.StateFlow


/**
 * @author Zaur
 * @since 21.05.2025
 */

interface EditionObservable : Observable.Mutable<EditionUIState.Base> {

    interface Update : Observable.Update<EditionUIState.Base>

    interface Read : Observable.Read<EditionUIState.Base> {
        fun editionState(): StateFlow<EditionUIState.Base>
    }

    interface Mutable : Update, Read

    class Base(
        private val initial: EditionUIState.Base,
    ) : Observable.Abstract<EditionUIState.Base>(initial), Mutable {
        override fun editionState(): StateFlow<EditionUIState.Base> = state()
    }

}