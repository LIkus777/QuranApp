package com.zaur.features.surah.observables

import com.zaur.features.surah.base.Observable
import com.zaur.presentation.ui.ui_state.aqc.QuranTranslationUIState
import com.zaur.presentation.ui.ui_state.aqc.QuranTranslationUIState.Base
import kotlinx.coroutines.flow.StateFlow

/**
* @author Zaur
* @since 2025-05-12
*/

interface QuranTranslationObservable : Observable.Mutable<Base> {

    interface Update : Observable.Update<QuranTranslationUIState.Base>

    interface Read : Observable.Read<QuranTranslationUIState.Base> {
        fun translationState(): StateFlow<QuranTranslationUIState.Base>
    }

    interface Mutable : Update, Read

    class Base(
        private val initial: QuranTranslationUIState.Base,
    ) : Observable.Abstract<QuranTranslationUIState.Base>(initial), Mutable {
        override fun translationState(): StateFlow<QuranTranslationUIState.Base> = state()
    }

}