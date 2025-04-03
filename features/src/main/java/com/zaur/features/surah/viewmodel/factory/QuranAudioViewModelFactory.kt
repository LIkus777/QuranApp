package com.zaur.features.surah.viewmodel.factory

import androidx.lifecycle.SavedStateHandle
import com.zaur.domain.al_quran_cloud.use_case.QuranAudioUseCaseAqc
import com.zaur.features.surah.viewmodel.QuranAudioViewModel

class QuranAudioViewModelFactory(
    private val quranAudioUseCaseAqc: QuranAudioUseCaseAqc
) {
    fun create(): QuranAudioViewModel {
        return QuranAudioViewModel(SavedStateHandle(), quranAudioUseCaseAqc)
    }
}