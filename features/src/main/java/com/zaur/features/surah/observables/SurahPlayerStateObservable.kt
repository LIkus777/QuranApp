package com.zaur.features.surah.observables

import com.zaur.features.surah.base.Observable
import com.zaur.presentation.ui.ui_state.SurahPlayerState
import kotlinx.coroutines.flow.StateFlow


/**
 * @author Zaur
 * @since 31.05.2025
 */

interface SurahPlayerStateObservable : Observable.Mutable<SurahPlayerState.Base> {

    interface Update : Observable.Update<SurahPlayerState.Base>

    interface Read : Observable.Read<SurahPlayerState.Base> {
        fun surahPlayerState(): StateFlow<SurahPlayerState.Base>
    }

    interface Mutable : Update, Read

    class Base(
        private val initial: SurahPlayerState.Base,
    ) : Observable.Abstract<SurahPlayerState.Base>(initial), Mutable {
        override fun surahPlayerState(): StateFlow<SurahPlayerState.Base> = state()
    }

}