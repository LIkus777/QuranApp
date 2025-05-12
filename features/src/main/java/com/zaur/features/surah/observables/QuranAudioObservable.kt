package com.zaur.features.surah.observables

import com.zaur.features.surah.base.Observable
import com.zaur.features.surah.ui_state.aqc.QuranAudioAqcUIState
import com.zaur.features.surah.ui_state.aqc.QuranAudioAqcUIState.Base
import kotlinx.coroutines.flow.StateFlow

/**
* @author Zaur
* @since 2025-05-12
*/

interface QuranAudioObservable : Observable.Mutable<Base> {

    interface Update : Observable.Update<QuranAudioAqcUIState.Base>

    interface Read : Observable.Read<QuranAudioAqcUIState.Base> {
        fun audioState(): StateFlow<QuranAudioAqcUIState.Base>
    }

    interface Mutable : Update, Read

    class Base(
        private val initial: QuranAudioAqcUIState.Base
    ) : Observable.Abstract<QuranAudioAqcUIState.Base>(initial), Mutable {
        override fun audioState(): StateFlow<QuranAudioAqcUIState.Base> = state()
    }

}