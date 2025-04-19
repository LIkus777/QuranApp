package com.zaur.features.surah.viewmodel.factory

import android.content.Context
import com.zaur.data.downloader.AudioDownloader
import com.zaur.domain.al_quran_cloud.use_case.QuranAudioUseCaseAqc
import com.zaur.features.surah.base.AudioPlayer
import com.zaur.features.surah.observables.QuranAudioObservable
import com.zaur.features.surah.screen.SurahDetailStateManager
import com.zaur.features.surah.screen.surah_detail.player.AudioPlaybackHelper
import com.zaur.features.surah.screen.surah_detail.player.AudioPlayerStateUpdater
import com.zaur.features.surah.screen.surah_detail.player.PlaylistBuilder
import com.zaur.features.surah.ui_state.aqc.QuranAudioAqcUIState
import com.zaur.features.surah.viewmodel.QuranAudioViewModel

interface QuranAudioViewModelFactory {

    fun create(): QuranAudioViewModel

    class Base(
        private val playlistBuilder: PlaylistBuilder? = null,
        private val audioPlayerStateUpdater: AudioPlayerStateUpdater? = null,
        private val audioPlaybackHelper: AudioPlaybackHelper? = null,
        private val context: Context? = null,
        private val audioPlayer: AudioPlayer,
        private val stateManager: SurahDetailStateManager,
        private val observable: QuranAudioObservable.Mutable = QuranAudioObservable.Base(
            QuranAudioAqcUIState()
        ),
        private val quranAudioUseCaseAqc: QuranAudioUseCaseAqc,
    ) : QuranAudioViewModelFactory {
        override fun create(): QuranAudioViewModel {
            return QuranAudioViewModel.Base(
                playlistBuilder!!,
                audioPlayerStateUpdater!!,
                audioPlaybackHelper!!,
                audioPlayer,
                stateManager,
                observable,
                quranAudioUseCaseAqc
            )
        }
    }
}