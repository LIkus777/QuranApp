package com.zaur.features.surah.viewmodel.factory

import com.zaur.domain.al_quran_cloud.use_case.QuranTextUseCase
import com.zaur.features.surah.observables.SurahChooseObservable
import com.zaur.presentation.ui.ui_state.aqc.QuranTextUIState
import com.zaur.features.surah.viewmodel.SurahChooseViewModel

/**
* @author Zaur
* @since 2025-05-12
*/

interface SurahChooseViewModelFactory {

    fun create(): SurahChooseViewModel

    class Base(
        private val observable: SurahChooseObservable.Mutable = SurahChooseObservable.Base(
            QuranTextUIState.Base()
        ), private val quranTextUseCase: QuranTextUseCase
    ) : SurahChooseViewModelFactory {
        override fun create(): SurahChooseViewModel {
            return SurahChooseViewModel.Base(observable, quranTextUseCase)
        }
    }
}