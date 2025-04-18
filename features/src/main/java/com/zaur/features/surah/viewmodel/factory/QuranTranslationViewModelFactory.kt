package com.zaur.features.surah.viewmodel.factory

import com.zaur.domain.al_quran_cloud.use_case.QuranTranslationUseCaseAqc
import com.zaur.features.surah.observables.QuranTranslationObservable
import com.zaur.features.surah.ui_state.aqc.QuranTranslationAqcUIState
import com.zaur.features.surah.viewmodel.QuranTranslationViewModel

interface QuranTranslationViewModelFactory {

    fun create(): QuranTranslationViewModel

    class Base(
        private val observable: QuranTranslationObservable.Mutable = QuranTranslationObservable.Base(
            QuranTranslationAqcUIState()
        ),
        private val quranTranslationUseCaseAqc: QuranTranslationUseCaseAqc
    ) : QuranTranslationViewModelFactory{
        override fun create(): QuranTranslationViewModel {
            return QuranTranslationViewModel.Base(observable, quranTranslationUseCaseAqc)
        }
    }
}