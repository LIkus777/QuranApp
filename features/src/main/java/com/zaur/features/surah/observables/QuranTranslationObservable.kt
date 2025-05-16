package com.zaur.features.surah.observables

import com.zaur.features.surah.base.Observable
import com.zaur.presentation.ui.ui_state.aqc.QuranTranslationAqcUIState
import com.zaur.presentation.ui.ui_state.aqc.QuranTranslationAqcUIState.Base
import kotlinx.coroutines.flow.StateFlow

/**
* @author Zaur
* @since 2025-05-12
*/

interface QuranTranslationObservable : Observable.Mutable<Base> {

    interface Update : Observable.Update<QuranTranslationAqcUIState.Base>

    interface Read : Observable.Read<QuranTranslationAqcUIState.Base> {
        fun translationState(): StateFlow<QuranTranslationAqcUIState.Base>
    }

    interface Mutable : Update, Read

    class Base(
        private val initial: QuranTranslationAqcUIState.Base,
    ) : Observable.Abstract<QuranTranslationAqcUIState.Base>(initial), Mutable {
        override fun translationState(): StateFlow<QuranTranslationAqcUIState.Base> = state()
    }

}