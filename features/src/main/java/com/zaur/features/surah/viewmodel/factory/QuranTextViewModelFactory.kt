package com.zaur.features.surah.viewmodel.factory

import androidx.lifecycle.SavedStateHandle
import com.zaur.domain.al_quran_cloud.use_case.QuranTextUseCaseAqc
import com.zaur.features.surah.viewmodel.QuranTextViewModel

class QuranTextViewModelFactory(
    private val quranTextUseCaseAqc: QuranTextUseCaseAqc
) {
    fun create(): QuranTextViewModel {
        return QuranTextViewModel(SavedStateHandle(), quranTextUseCaseAqc)
    }
}
