package com.zaur.features.surah.observables

import com.zaur.features.surah.base.Observable
import com.zaur.presentation.ui.ui_state.aqc.SurahDetailScreenState
import kotlinx.coroutines.flow.StateFlow


/**
 * @author Zaur
 * @since 25.05.2025
 */

interface SurahDetailStateObservable : Observable.Mutable<SurahDetailScreenState.Base> {
    interface Update : Observable.Update<SurahDetailScreenState.Base>

    interface Read : Observable.Read<SurahDetailScreenState.Base> {
        fun surahDetailState(): StateFlow<SurahDetailScreenState.Base>
    }

    interface Mutable : Update, Read

    class Base(
        private val initial: SurahDetailScreenState.Base,
    ) : Observable.Abstract<SurahDetailScreenState.Base>(initial), Mutable {
        override fun surahDetailState(): StateFlow<SurahDetailScreenState.Base> = state()
    }
}
