package com.zaur.features.surah.observables

import com.zaur.features.surah.base.Observable
import com.zaur.features.surah.ui_state.aqc.QuranTranslationAqcUIState
import kotlinx.coroutines.flow.StateFlow

interface QuranTranslationObservable : Observable.Mutable<QuranTranslationAqcUIState> {

    interface Update : Observable.Update<QuranTranslationAqcUIState>

    interface Read : Observable.Read<QuranTranslationAqcUIState> {
        fun translationState(): StateFlow<QuranTranslationAqcUIState>
    }

    interface Mutable : Update, Read

    class Base(
        private val initial: QuranTranslationAqcUIState
    ) : Observable.Abstract<QuranTranslationAqcUIState>(initial), Mutable {
        override fun translationState(): StateFlow<QuranTranslationAqcUIState> = state()
    }

}