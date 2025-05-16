package com.zaur.features.surah.observables

import com.zaur.features.surah.base.Observable
import com.zaur.presentation.ui.ui_state.offline.OfflineUIState
import kotlinx.coroutines.flow.StateFlow

/**
* @author Zaur
* @since 2025-05-12
*/

interface OfflineObservable {

    interface Update : Observable.Update<OfflineUIState>

    interface Read : Observable.Read<OfflineUIState> {
        fun offlineState(): StateFlow<OfflineUIState>
    }

    interface Mutable : Update, Read

    class Base(
        private val initial: OfflineUIState
    ) : Observable.Abstract<OfflineUIState>(initial), Mutable {
        override fun offlineState(): StateFlow<OfflineUIState> = state()
    }

}