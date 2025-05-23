package com.zaur.features.surah.viewmodel.factory

import com.zaur.domain.al_quran_cloud.use_case.QuranTranslationUseCase
import com.zaur.features.surah.observables.QuranTranslationObservable
import com.zaur.presentation.ui.ui_state.aqc.QuranTranslationUIState
import com.zaur.features.surah.viewmodel.QuranTranslationViewModel

/**
* @author Zaur
* @since 2025-05-12
*/

interface QuranTranslationViewModelFactory {

    fun create(): QuranTranslationViewModel

    class Base(
        private val observable: QuranTranslationObservable.Mutable = QuranTranslationObservable.Base(
            QuranTranslationUIState.Base()
        ),
        private val quranTranslationUseCase: QuranTranslationUseCase
    ) : QuranTranslationViewModelFactory{
        override fun create(): QuranTranslationViewModel {
            return QuranTranslationViewModel.Base(observable, quranTranslationUseCase)
        }
    }
}