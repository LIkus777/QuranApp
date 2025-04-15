package com.zaur.features.surah.observables

import com.zaur.features.surah.base.Observable
import com.zaur.features.surah.ui_state.aqc.QuranAudioAqcUIState
import kotlinx.coroutines.flow.StateFlow

interface QuranAudioObservable : Observable.Mutable<QuranAudioAqcUIState> {

    interface Update : Observable.Update<QuranAudioAqcUIState>

    interface Read : Observable.Read<QuranAudioAqcUIState> {
        fun audioState(): StateFlow<QuranAudioAqcUIState>
    }

    interface Mutable : Update, Read

    class Base(
        private val initial: QuranAudioAqcUIState
    ) : Observable.Abstract<QuranAudioAqcUIState>(initial), Mutable {
        override fun audioState(): StateFlow<QuranAudioAqcUIState> = state()
    }

}
