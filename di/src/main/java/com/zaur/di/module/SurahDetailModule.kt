package com.zaur.di.module

import android.content.Context
import com.zaur.data.al_quran_aqc.repository_impl.cloud.QuranAudioCloudRepositoryAqcImpl
import com.zaur.data.al_quran_aqc.repository_impl.cloud.QuranTextCloudRepositoryAqcImpl
import com.zaur.data.al_quran_aqc.repository_impl.cloud.QuranTranslationCloupRepositoryAqcImpl
import com.zaur.data.al_quran_aqc.repository_impl.local.QuranAudioLocalRepositoryAqcImpl
import com.zaur.data.al_quran_aqc.repository_impl.local.QuranTextLocalRepositoryAqcImpl
import com.zaur.data.al_quran_aqc.repository_impl.local.QuranTranslationLocalRepositoryAqcImpl
import com.zaur.di.provides.ProvideAudioPlaybackHelper
import com.zaur.di.provides.ProvideAudioPlayer
import com.zaur.di.provides.ProvideAudioPlayerStateUpdater
import com.zaur.di.provides.ProvidePlaylistBuilder
import com.zaur.di.provides.ProvideQuranAudioRepositoryAqcCloud
import com.zaur.di.provides.ProvideQuranAudioRepositoryAqcLocal
import com.zaur.di.provides.ProvideQuranAudioUseCaseAqc
import com.zaur.di.provides.ProvideQuranAudioViewModelFactory
import com.zaur.di.provides.ProvideQuranTextRepositoryAqcCloud
import com.zaur.di.provides.ProvideQuranTextRepositoryAqcLocal
import com.zaur.di.provides.ProvideQuranTextUseCaseAqc
import com.zaur.di.provides.ProvideQuranTextViewModelFactory
import com.zaur.di.provides.ProvideQuranTranslationRepositoryAqcCloud
import com.zaur.di.provides.ProvideQuranTranslationRepositoryAqcLocal
import com.zaur.di.provides.ProvideQuranTranslationUseCaseAqc
import com.zaur.di.provides.ProvideReciterManager
import com.zaur.di.provides.ProvideReciterUseCase
import com.zaur.di.provides.ProvideSurahChooseViewModelFactory
import com.zaur.di.provides.ProvideSurahDetailStateManager
import com.zaur.di.provides.ProvideSurahDetailViewModel
import com.zaur.di.provides.ProvideSurahPlayer
import com.zaur.di.provides.ProvideThemeUseCase
import com.zaur.di.provides.ProvideTranslationViewModelFactory
import com.zaur.domain.al_quran_cloud.repository.QuranAudioRepositoryAqc
import com.zaur.domain.al_quran_cloud.repository.QuranTextRepositoryAqc
import com.zaur.domain.al_quran_cloud.repository.QuranTranslationRepositoryAqc
import com.zaur.domain.al_quran_cloud.use_case.QuranAudioUseCaseAqc
import com.zaur.domain.al_quran_cloud.use_case.QuranTextUseCaseAqc
import com.zaur.domain.al_quran_cloud.use_case.QuranTranslationUseCaseAqc
import com.zaur.domain.al_quran_cloud.use_case.ReciterUseCase
import com.zaur.domain.storage.theme.ThemeUseCase
import com.zaur.features.surah.base.AudioPlayer
import com.zaur.features.surah.manager.ReciterManager
import com.zaur.features.surah.screen.surah_detail.SurahDetailStateManager
import com.zaur.features.surah.screen.surah_detail.player.AudioPlaybackHelper
import com.zaur.features.surah.screen.surah_detail.player.AudioPlayerStateUpdater
import com.zaur.features.surah.screen.surah_detail.player.PlaylistBuilder
import com.zaur.features.surah.screen.surah_detail.player.SurahPlayer
import com.zaur.features.surah.viewmodel.SurahDetailViewModel
import com.zaur.features.surah.viewmodel.factory.QuranAudioViewModelFactory
import com.zaur.features.surah.viewmodel.factory.QuranTextViewModelFactory
import com.zaur.features.surah.viewmodel.factory.QuranTranslationViewModelFactory
import com.zaur.features.surah.viewmodel.factory.SurahChooseViewModelFactory

interface SurahDetailModule : ProvideReciterUseCase, ProvideReciterManager, ProvideThemeUseCase, ProvideSurahChooseViewModelFactory,
    ProvideTranslationViewModelFactory, ProvideQuranAudioViewModelFactory,
    ProvideQuranTextViewModelFactory, ProvideSurahDetailViewModel, ProvideSurahPlayer,
    ProvideSurahDetailStateManager, ProvideAudioPlayer, ProvidePlaylistBuilder,
    ProvideAudioPlayerStateUpdater, ProvideAudioPlaybackHelper, ProvideQuranAudioUseCaseAqc,
    ProvideQuranTextUseCaseAqc, ProvideQuranTranslationUseCaseAqc,
    ProvideQuranAudioRepositoryAqcLocal, ProvideQuranAudioRepositoryAqcCloud,
    ProvideQuranTextRepositoryAqcLocal, ProvideQuranTextRepositoryAqcCloud,
    ProvideQuranTranslationRepositoryAqcCloud, ProvideQuranTranslationRepositoryAqcLocal {

    class Base(
        private val context: Context,
        private val dataModule: DataModule,
        private val mapperModule: MapperModule,
    ) : SurahDetailModule {

        private val themeUseCase by lazy {
            ThemeUseCase(dataModule.provideThemeStorage())
        }

        private val surahDetailStateManager by lazy {
            SurahDetailStateManager.Base()
        }
        private val audioPlayer by lazy {
            AudioPlayer.Base(context)
        }

        private val quranTranslationViewModelFactory by lazy {
            QuranTranslationViewModelFactory.Base(quranTranslationUseCaseAqc = provideQuranTranslationUseCaseAqc())
        }

        private val quranAudioViewModelFactory by lazy {
            QuranAudioViewModelFactory.Base(
                provideSurahPlayer(),
                provideReciterManager(),
                provideSurahDetailStateManager(),
                quranAudioUseCaseAqc = provideQuranAudioUseCaseAqc()
            )
        }

        private val quranTextViewModelFactory by lazy {
            QuranTextViewModelFactory.Base(quranTextUseCaseAqc = provideQuranTextUseCaseAqc())
        }

        private val quranAudioUseCaseAqc by lazy {
            QuranAudioUseCaseAqc.Base(
                dataModule.provideOfflineRepository(),
                provideQuranAudioRepositoryAqcLocal(),
                provideQuranAudioRepositoryAqcCloud()
            )
        }

        private val quranTextUseCaseAqc by lazy {
            QuranTextUseCaseAqc.Base(
                dataModule.provideQuranStorage(),
                dataModule.provideOfflineRepository(),
                provideQuranTextRepositoryAqcLocal(),
                provideQuranTextRepositoryAqcCloud()
            )
        }

        private val quranTranslationUseCaseAqc by lazy {
            QuranTranslationUseCaseAqc.Base(
                dataModule.provideOfflineRepository(),
                provideQuranTranslationRepositoryAqcLocal(),
                provideQuranTranslationRepositoryAqcCloud()
            )
        }

        private val audioRepositoryCloud by lazy {
            QuranAudioCloudRepositoryAqcImpl(
                dataModule.provideQuranApiAqc(), dataModule.provideAudioDownloader()
            )
        }

        private val textRepositoryCloud by lazy {
            QuranTextCloudRepositoryAqcImpl(
                dataModule.provideQuranApiAqc()
            )
        }

        override fun provideThemeUseCase(): ThemeUseCase = themeUseCase

        override fun provideQuranTextRepositoryAqcLocal(): QuranTextRepositoryAqc.Local =
            QuranTextLocalRepositoryAqcImpl(
                mapperModule.provideChapterMapper(),
                mapperModule.provideArabicMapper(),
                dataModule.provideChapterDao(),
                dataModule.provideArabicChapterDao()
            )

        override fun provideQuranTranslationRepositoryAqcCloud(): QuranTranslationRepositoryAqc.Cloud =
            QuranTranslationCloupRepositoryAqcImpl(dataModule.provideQuranApiAqc())

        override fun provideQuranAudioRepositoryAqcLocal(): QuranAudioRepositoryAqc.Local =
            QuranAudioLocalRepositoryAqcImpl(
                dataModule.provideChapterAudioDao(),
                dataModule.provideVerseAudioDao(),
                mapperModule.provideChapterAudioMapper(),
                mapperModule.provideVerseAudioMapper()
            )

        override fun provideQuranAudioUseCaseAqc(): QuranAudioUseCaseAqc = quranAudioUseCaseAqc

        override fun provideQuranTextUseCaseAqc(): QuranTextUseCaseAqc = quranTextUseCaseAqc

        override fun provideQuranTranslationUseCaseAqc(): QuranTranslationUseCaseAqc =
            quranTranslationUseCaseAqc

        override fun providePlaylistBuilder(): PlaylistBuilder =
            PlaylistBuilder.Base(dataModule.provideAudioDownloader())

        override fun provideAudioPlayerStateUpdater(): AudioPlayerStateUpdater =
            AudioPlayerStateUpdater.Base(provideSurahDetailStateManager()) //todo

        override fun provideAudioPlaybackHelper(): AudioPlaybackHelper =
            AudioPlaybackHelper.Base(dataModule.provideAudioDownloader(), provideAudioPlayer())

        override fun provideAudioPlayer(): AudioPlayer = audioPlayer

        override fun provideSurahDetailStateManager(): SurahDetailStateManager =
            surahDetailStateManager

        override fun provideSurahPlayer(): SurahPlayer = SurahPlayer.Base(
            providePlaylistBuilder(),
            provideAudioPlayerStateUpdater(),
            provideAudioPlaybackHelper(),
            provideAudioPlayer(),
            provideSurahDetailStateManager()
        )

        override fun provideQuranTranslationViewModelFactory(): QuranTranslationViewModelFactory =
            quranTranslationViewModelFactory

        override fun provideQuranAudioViewModelFactory(): QuranAudioViewModelFactory =
            quranAudioViewModelFactory

        override fun provideQuranTextViewModelFactory(): QuranTextViewModelFactory =
            quranTextViewModelFactory

        override fun provideSurahDetailViewModel(): SurahDetailViewModel =
            SurahDetailViewModel.Base(provideSurahDetailStateManager())

        override fun provideReciterManager(): ReciterManager =
            ReciterManager.Base(provideReciterUseCase())

        override fun provideReciterUseCase(): ReciterUseCase =
            ReciterUseCase.Base(dataModule.provideReciterStorage())

        override fun provideQuranAudioRepositoryAqcCloud(): QuranAudioRepositoryAqc.Cloud =
            audioRepositoryCloud

        override fun provideQuranTextRepositoryAqcCloud(): QuranTextRepositoryAqc.Cloud =
            textRepositoryCloud

        override fun provideQuranTranslationRepositoryAqcLocal(): QuranTranslationRepositoryAqc.Local =
            QuranTranslationLocalRepositoryAqcImpl(
                dataModule.provideTranslationChapterDao(), mapperModule.provideTranslationMapper()
            )

        override fun provideSurahChooseViewModelFactory(): SurahChooseViewModelFactory =
            SurahChooseViewModelFactory.Base(quranTextUseCaseAqc = provideQuranTextUseCaseAqc())
    }

}

