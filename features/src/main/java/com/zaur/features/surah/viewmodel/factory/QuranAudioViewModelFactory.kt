package com.zaur.features.surah.viewmodel.factory

import com.zaur.domain.al_quran_cloud.use_case.QuranAudioUseCaseAqc
import com.zaur.features.surah.observables.QuranAudioObservable
import com.zaur.features.surah.screen.SurahDetailStateManager
import com.zaur.features.surah.screen.surah_detail.player.SurahPlayer
import com.zaur.features.surah.ui_state.aqc.QuranAudioAqcUIState
import com.zaur.features.surah.viewmodel.QuranAudioViewModel

interface QuranAudioViewModelFactory {

    fun create(): QuranAudioViewModel

    class Base(
        private val surahPlayer: SurahPlayer? = null,
        private val stateManager: SurahDetailStateManager,
        private val observable: QuranAudioObservable.Mutable = QuranAudioObservable.Base(
            QuranAudioAqcUIState()
        ),
        private val quranAudioUseCaseAqc: QuranAudioUseCaseAqc,
    ) : QuranAudioViewModelFactory {
        override fun create(): QuranAudioViewModel {
            return QuranAudioViewModel.Base(
                surahPlayer!!, stateManager, observable, quranAudioUseCaseAqc
            )
        }
    }
}