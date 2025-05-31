package com.zaur.di.module

import android.content.Context
import com.zaur.di.provides.ProvideAudioPlaybackHelper
import com.zaur.di.provides.ProvideAudioPlayer
import com.zaur.di.provides.ProvideAudioPlayerStateUpdater
import com.zaur.di.provides.ProvidePlaylistBuilder
import com.zaur.di.provides.ProvideSurahPlayer
import com.zaur.di.provides.ProvideSurahPlayerStateManager
import com.zaur.features.surah.base.AudioPlayer
import com.zaur.features.surah.manager.SurahPlayerStateManager
import com.zaur.features.surah.screen.surah_detail.player.AudioPlaybackHelper
import com.zaur.features.surah.screen.surah_detail.player.AudioPlayerStateUpdater
import com.zaur.features.surah.screen.surah_detail.player.PlaylistBuilder
import com.zaur.features.surah.screen.surah_detail.player.SurahPlayer


/**
 * @author Zaur
 * @since 24.05.2025
 */

interface AudioModule : ProvideSurahPlayerStateManager, ProvideSurahPlayer, ProvidePlaylistBuilder, ProvideAudioPlayer,
    ProvideAudioPlayerStateUpdater, ProvideAudioPlaybackHelper {

    class Base(
        private val context: Context,
        private val dataModule: DataModule,
        private val surahDetailStateManagerModule: SurahDetailStateManagerModule,
    ) : AudioModule {

        private val audioPlayer by lazy {
            AudioPlayer.Base(context)
        }

        private val surahPlayerStateManager by lazy {
            SurahPlayerStateManager.Base()
        }

        override fun provideSurahPlayer(): SurahPlayer = SurahPlayer.Base(
            providePlaylistBuilder(),
            provideAudioPlayerStateUpdater(),
            provideAudioPlaybackHelper(),
            provideAudioPlayer(),
            surahDetailStateManagerModule.provideSurahDetailStateManager(),
            provideSurahPlayerStateManager()
        )

        override fun providePlaylistBuilder(): PlaylistBuilder = PlaylistBuilder.Base(
            surahDetailStateManagerModule.provideSurahDetailStateManager().surahDetailState(),
            dataModule.provideAudioDownloader()
        )

        override fun provideAudioPlayerStateUpdater(): AudioPlayerStateUpdater =
            AudioPlayerStateUpdater.Base(surahDetailStateManagerModule.provideSurahDetailStateManager(), surahPlayerStateManager) //todo

        override fun provideAudioPlaybackHelper(): AudioPlaybackHelper = AudioPlaybackHelper.Base(
            surahDetailStateManagerModule.provideSurahDetailStateManager().surahDetailState(),
            dataModule.provideAudioDownloader(),
            provideAudioPlayer()
        )

        override fun provideAudioPlayer(): AudioPlayer = audioPlayer
        override fun provideSurahPlayerStateManager(): SurahPlayerStateManager = surahPlayerStateManager
    }

}