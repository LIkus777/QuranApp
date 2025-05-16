package com.zaur.features.surah.viewmodel

import com.zaur.core.BaseViewModel
import com.zaur.domain.al_quran_cloud.use_case.OfflineUseCase
import com.zaur.features.surah.observables.OfflineObservable
import com.zaur.presentation.ui.ui_state.offline.OfflineUIState
import kotlinx.coroutines.flow.StateFlow

/**
* @author Zaur
* @since 2025-05-12
*/

interface OfflineViewModel : OfflineObservable.Read {

    fun isOffline(): Boolean
    fun setIsOffline(isOffline: Boolean)

    class Base(
        private val observable: OfflineObservable.Mutable = OfflineObservable.Base(OfflineUIState.Base()),
        private val offlineUseCase: OfflineUseCase
    ) : BaseViewModel(), OfflineViewModel {
        override fun offlineState(): StateFlow<OfflineUIState> = observable.offlineState()
        override fun isOffline(): Boolean = offlineUseCase.isOffline()
        override fun setIsOffline(isOffline: Boolean) = offlineUseCase.setIsOffline(isOffline)
    }

}