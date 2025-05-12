package com.zaur.features.surah.viewmodel.factory

import com.zaur.domain.al_quran_cloud.use_case.QuranTextUseCaseAqc
import com.zaur.features.surah.observables.QuranTextObservable
import com.zaur.features.surah.ui_state.aqc.QuranTextAqcUIState
import com.zaur.features.surah.viewmodel.QuranTextViewModel

/**
* @author Zaur
* @since 2025-05-12
*/

interface QuranTextViewModelFactory {

    fun create(): QuranTextViewModel

    class Base(
        private val observable: QuranTextObservable.Mutable = QuranTextObservable.Base(
            QuranTextAqcUIState.Base()
        ),
        private val quranTextUseCaseAqc: QuranTextUseCaseAqc
    ) : QuranTextViewModelFactory {
        override fun create(): QuranTextViewModel {
            return QuranTextViewModel.Base(observable, quranTextUseCaseAqc)
        }
    }

}