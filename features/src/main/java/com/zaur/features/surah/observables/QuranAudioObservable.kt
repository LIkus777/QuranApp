package com.zaur.features.surah.observables

import androidx.compose.runtime.State
import com.zaur.features.surah.base.Observable
import com.zaur.features.surah.ui_state.aqc.QuranAudioAqcUIState

interface QuranAudioObservable : Observable.Mutable<QuranAudioAqcUIState> {

    interface Update : Observable.Update<QuranAudioAqcUIState>

    interface Read : Observable.Read<QuranAudioAqcUIState> {
        fun audioState(): State<QuranAudioAqcUIState>
    }

    interface Mutable : Update, Read

    class Base(
        private val initial: QuranAudioAqcUIState
    ) : Observable.Abstract<QuranAudioAqcUIState>(initial), Mutable {
        override fun audioState(): State<QuranAudioAqcUIState> = state()
    }

}
