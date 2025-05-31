package com.zaur.features.surah.viewmodel.factory

import com.zaur.domain.al_quran_cloud.use_case.QuranAudioUseCase
import com.zaur.features.surah.manager.ReciterManager
import com.zaur.features.surah.manager.SurahDetailStateManager
import com.zaur.features.surah.manager.SurahPlayerStateManager
import com.zaur.features.surah.observables.SurahPlayerObservable
import com.zaur.features.surah.screen.surah_detail.player.SurahPlayer
import com.zaur.features.surah.viewmodel.SurahPlayerViewModel
import com.zaur.presentation.ui.ui_state.aqc.SurahPlayerUIState

/**
 * @author Zaur
 * @since 2025-05-12
 */

interface SurahPlayerViewModelFactory {

    fun create(): SurahPlayerViewModel

    class Base(
        private val surahPlayer: SurahPlayer? = null,
        private val reciterManager: ReciterManager? = null,
        private val surahDetailStateManager: SurahDetailStateManager,
        private val surahPlayerStateManager: SurahPlayerStateManager,
        private val observable: SurahPlayerObservable.Mutable = SurahPlayerObservable.Base(
            SurahPlayerUIState.Base()
        ),
        private val quranAudioUseCase: QuranAudioUseCase,
    ) : SurahPlayerViewModelFactory {
        override fun create() = SurahPlayerViewModel.Base(
            surahPlayer!!, reciterManager!!, surahDetailStateManager, surahPlayerStateManager, observable, quranAudioUseCase
        )
    }
}