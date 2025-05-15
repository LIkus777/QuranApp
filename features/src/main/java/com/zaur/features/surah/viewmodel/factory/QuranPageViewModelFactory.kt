package com.zaur.features.surah.viewmodel.factory

import com.zaur.domain.al_quran_cloud.use_case.QuranPageUseCase
import com.zaur.features.surah.observables.QuranPageObservable
import com.zaur.features.surah.ui_state.aqc.QuranPageAqcUIState
import com.zaur.features.surah.viewmodel.QuranPageViewModel


/**
 * @author Zaur
 * @since 15.05.2025
 */

interface QuranPageViewModelFactory {

    fun create(): QuranPageViewModel

    class Base(
        private val quranPageUseCase: QuranPageUseCase,
        private val observable: QuranPageObservable.Mutable = QuranPageObservable.Base(
            QuranPageAqcUIState.Base()
        ),
    ) : QuranPageViewModelFactory {
        override fun create(): QuranPageViewModel =
            QuranPageViewModel.Base(quranPageUseCase, observable)
    }

}