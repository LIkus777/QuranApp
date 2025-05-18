package com.zaur.features.surah.viewmodel.factory

import com.zaur.domain.al_quran_cloud.use_case.QuranTextUseCase
import com.zaur.features.surah.manager.SurahDetailStateManager
import com.zaur.features.surah.observables.QuranTextObservable
import com.zaur.presentation.ui.ui_state.aqc.QuranTextUIState
import com.zaur.features.surah.viewmodel.QuranTextViewModel

/**
* @author Zaur
* @since 2025-05-12
*/

interface QuranTextViewModelFactory {

    fun create(): QuranTextViewModel

    class Base(
        private val surahDetailStateManager: SurahDetailStateManager,
        private val observable: QuranTextObservable.Mutable = QuranTextObservable.Base(
            QuranTextUIState.Base()
        ),
        private val quranTextUseCase: QuranTextUseCase
    ) : QuranTextViewModelFactory {
        override fun create(): QuranTextViewModel {
            return QuranTextViewModel.Base(observable, quranTextUseCase, surahDetailStateManager)
        }
    }

}