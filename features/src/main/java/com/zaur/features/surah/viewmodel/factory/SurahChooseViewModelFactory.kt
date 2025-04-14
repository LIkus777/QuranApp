package com.zaur.features.surah.viewmodel.factory

import com.zaur.domain.al_quran_cloud.use_case.QuranTextUseCaseAqc
import com.zaur.features.surah.observables.SurahChooseObservable
import com.zaur.features.surah.ui_state.aqc.QuranTextAqcUIState
import com.zaur.features.surah.viewmodel.SurahChooseViewModel

interface SurahChooseViewModelFactory {

    fun create(): SurahChooseViewModel

    class Base(
        private val observable: SurahChooseObservable.Mutable = SurahChooseObservable.Base(
            QuranTextAqcUIState()
        ), private val quranTextUseCaseAqc: QuranTextUseCaseAqc
    ) : SurahChooseViewModelFactory {
        override fun create(): SurahChooseViewModel {
            return SurahChooseViewModel.Base(observable, quranTextUseCaseAqc)
        }
    }
}