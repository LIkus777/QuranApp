package com.zaur.features.surah.viewmodel.factory

import com.zaur.domain.al_quran_cloud.use_case.QuranAudioUseCase
import com.zaur.features.surah.manager.ReciterManager
import com.zaur.features.surah.observables.QuranAudioObservable
import com.zaur.features.surah.manager.SurahDetailStateManager
import com.zaur.features.surah.screen.surah_detail.player.SurahPlayer
import com.zaur.features.surah.ui_state.aqc.QuranAudioAqcUIState
import com.zaur.features.surah.viewmodel.QuranAudioViewModel

/**
* @author Zaur
* @since 2025-05-12
*/

interface QuranAudioViewModelFactory {

    fun create(): QuranAudioViewModel

    class Base(
        private val surahPlayer: SurahPlayer? = null,
        private val reciterManager: ReciterManager? = null,
        private val stateManager: SurahDetailStateManager,
        private val observable: QuranAudioObservable.Mutable = QuranAudioObservable.Base(
            QuranAudioAqcUIState.Base()
        ),
        private val quranAudioUseCase: QuranAudioUseCase,
    ) : QuranAudioViewModelFactory {
        override fun create(): QuranAudioViewModel {
            return QuranAudioViewModel.Base(
                surahPlayer!!, reciterManager!!, stateManager, observable, quranAudioUseCase
            )
        }
    }
}