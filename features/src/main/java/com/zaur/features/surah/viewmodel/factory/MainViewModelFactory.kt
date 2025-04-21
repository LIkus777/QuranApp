package com.zaur.features.surah.viewmodel.factory

import com.zaur.domain.al_quran_cloud.use_case.MainUseCase
import com.zaur.features.surah.manager.ReciterManager
import com.zaur.features.surah.observables.MainObservable
import com.zaur.features.surah.ui_state.main.MainState
import com.zaur.features.surah.viewmodel.MainViewModel

interface MainViewModelFactory {

    fun create(): MainViewModel

    class Base(
        private val mainUseCase: MainUseCase,
        private val reciterManager: ReciterManager,
        private val observable: MainObservable.Mutable = MainObservable.Base(
            MainState()
        )
    ) : MainViewModelFactory {
        override fun create(): MainViewModel {
            return MainViewModel.Base(mainUseCase, reciterManager, observable)
        }
    }

}