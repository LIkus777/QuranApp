package com.zaur.features.surah.observables

import com.zaur.features.surah.base.Observable
import com.zaur.features.surah.ui_state.offline.OfflineUIState
import kotlinx.coroutines.flow.StateFlow

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