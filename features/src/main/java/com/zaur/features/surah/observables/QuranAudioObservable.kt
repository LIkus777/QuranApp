package com.zaur.features.surah.observables

import com.zaur.features.surah.base.Observable
import com.zaur.presentation.ui.ui_state.aqc.QuranAudioUIState
import com.zaur.presentation.ui.ui_state.aqc.QuranAudioUIState.Base
import kotlinx.coroutines.flow.StateFlow

/**
* @author Zaur
* @since 2025-05-12
*/

interface QuranAudioObservable : Observable.Mutable<Base> {

    interface Update : Observable.Update<QuranAudioUIState.Base>

    interface Read : Observable.Read<QuranAudioUIState.Base> {
        fun audioState(): StateFlow<QuranAudioUIState.Base>
    }

    interface Mutable : Update, Read

    class Base(
        private val initial: QuranAudioUIState.Base
    ) : Observable.Abstract<QuranAudioUIState.Base>(initial), Mutable {
        override fun audioState(): StateFlow<QuranAudioUIState.Base> = state()
    }

}