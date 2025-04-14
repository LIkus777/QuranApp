package com.zaur.features.surah.observables

import androidx.compose.runtime.State
import com.zaur.features.surah.base.Observable
import com.zaur.features.surah.ui_state.aqc.QuranTranslationAqcUIState

interface QuranTranslationObservable : Observable.Mutable<QuranTranslationAqcUIState> {

    interface Update : Observable.Update<QuranTranslationAqcUIState> {

    }

    interface Read : Observable.Read<QuranTranslationAqcUIState> {
        fun translationState(): State<QuranTranslationAqcUIState>
    }

    interface Mutable : Update, Read

    class Base(
        private val initial: QuranTranslationAqcUIState
    ) : Observable.Abstract<QuranTranslationAqcUIState>(initial), Mutable {
        override fun translationState(): State<QuranTranslationAqcUIState> = state()
    }

}