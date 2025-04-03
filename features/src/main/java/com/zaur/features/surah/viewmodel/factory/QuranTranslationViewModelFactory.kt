package com.zaur.features.surah.viewmodel.factory

import androidx.lifecycle.SavedStateHandle
import com.zaur.domain.al_quran_cloud.use_case.QuranTranslationUseCaseAqc
import com.zaur.features.surah.viewmodel.QuranTranslationViewModel

class QuranTranslationViewModelFactory(
    private val quranTranslationUseCaseAqc: QuranTranslationUseCaseAqc
) {
    fun create(): QuranTranslationViewModel {
        return QuranTranslationViewModel(SavedStateHandle(), quranTranslationUseCaseAqc)
    }
}