package com.zaur.di.module

import android.content.Context
import com.zaur.data.al_quran_aqc.repository_impl.cloud.QuranAudioCloudRepositoryAqcImpl
import com.zaur.data.al_quran_aqc.repository_impl.cloud.QuranTextCloudRepositoryAqcImpl
import com.zaur.data.al_quran_aqc.repository_impl.cloud.QuranTranslationCloupRepositoryAqcImpl
import com.zaur.data.al_quran_aqc.repository_impl.local.QuranAudioLocalRepositoryAqcImpl
import com.zaur.data.al_quran_aqc.repository_impl.local.QuranTextLocalRepositoryAqcImpl
import com.zaur.data.al_quran_aqc.repository_impl.local.QuranTranslationLocalRepositoryAqcImpl
import com.zaur.data.apiV4.api.QuranApiV4
import com.zaur.data.apiV4.repository_impl.QuranAudioRepositoryV4Impl
import com.zaur.data.apiV4.repository_impl.QuranTafsirRepositoryV4Impl
import com.zaur.data.apiV4.repository_impl.QuranTajweedRepositoryV4Impl
import com.zaur.data.apiV4.repository_impl.QuranTextRepositoryV4Impl
import com.zaur.data.apiV4.repository_impl.QuranTranslationRepositoryV4Impl
import com.zaur.data.network.ApiFactory
import com.zaur.di.provides.ProvideAudioPlaybackHelper
import com.zaur.di.provides.ProvideAudioPlayer
import com.zaur.di.provides.ProvideAudioPlayerStateUpdater
import com.zaur.di.provides.ProvidePlaylistBuilder
import com.zaur.di.provides.ProvideQuranApiV4
import com.zaur.di.provides.ProvideQuranAudioRepositoryAqcCloud
import com.zaur.di.provides.ProvideQuranAudioRepositoryAqcLocal
import com.zaur.di.provides.ProvideQuranAudioRepositoryV4
import com.zaur.di.provides.ProvideQuranAudioUseCaseAqc
import com.zaur.di.provides.ProvideQuranAudioUseCaseV4
import com.zaur.di.provides.ProvideQuranAudioViewModelFactory
import com.zaur.di.provides.ProvideQuranTafsirRepositoryV4
import com.zaur.di.provides.ProvideQuranTafsirUseCase
import com.zaur.di.provides.ProvideQuranTajweedRepositoryV4
import com.zaur.di.provides.ProvideQuranTajweedUseCase
import com.zaur.di.provides.ProvideQuranTextRepositoryAqcCloud
import com.zaur.di.provides.ProvideQuranTextRepositoryAqcLocal
import com.zaur.di.provides.ProvideQuranTextRepositoryV4
import com.zaur.di.provides.ProvideQuranTextUseCase
import com.zaur.di.provides.ProvideQuranTextUseCaseAqc
import com.zaur.di.provides.ProvideQuranTextViewModelFactory
import com.zaur.di.provides.ProvideQuranTranslationRepositoryAqcCloud
import com.zaur.di.provides.ProvideQuranTranslationRepositoryAqcLocal
import com.zaur.di.provides.ProvideQuranTranslationRepositoryV4
import com.zaur.di.provides.ProvideQuranTranslationUseCase
import com.zaur.di.provides.ProvideQuranTranslationUseCaseAqc
import com.zaur.di.provides.ProvideReciterManager
import com.zaur.di.provides.ProvideReciterUseCase
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
import com.zaur.domain.apiV4.repository.QuranAudioRepositoryV4
import com.zaur.domain.apiV4.repository.QuranTafsirRepositoryV4
import com.zaur.domain.apiV4.repository.QuranTajweedRepositoryV4
import com.zaur.domain.apiV4.repository.QuranTextRepositoryV4
import com.zaur.domain.apiV4.repository.QuranTranslationRepositoryV4
import com.zaur.domain.apiV4.use_case.QuranAudioUseCaseV4
import com.zaur.domain.apiV4.use_case.QuranTafsirUseCaseV4
import com.zaur.domain.apiV4.use_case.QuranTajweedUseCaseV4
import com.zaur.domain.apiV4.use_case.QuranTextUseCaseV4
import com.zaur.domain.apiV4.use_case.QuranTranslationUseCaseV4
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

interface SurahDetailModule : ProvideReciterUseCase, ProvideReciterManager, ProvideThemeUseCase,
    ProvideTranslationViewModelFactory, ProvideQuranAudioViewModelFactory,
    ProvideQuranTextViewModelFactory, ProvideSurahDetailViewModel, ProvideSurahPlayer,
    ProvideSurahDetailStateManager, ProvideAudioPlayer, ProvidePlaylistBuilder,
    ProvideAudioPlayerStateUpdater, ProvideAudioPlaybackHelper, ProvideQuranAudioUseCaseAqc,
    ProvideQuranTextUseCaseAqc, ProvideQuranTranslationUseCaseAqc, ProvideQuranAudioUseCaseV4,
    ProvideQuranTextUseCase, ProvideQuranTajweedUseCase, ProvideQuranTafsirUseCase,
    ProvideQuranTranslationUseCase, ProvideQuranApiV4, ProvideQuranAudioRepositoryAqcLocal,
    ProvideQuranAudioRepositoryAqcCloud, ProvideQuranTextRepositoryAqcLocal,
    ProvideQuranTextRepositoryAqcCloud, ProvideQuranTranslationRepositoryAqcCloud,
    ProvideQuranTranslationRepositoryAqcLocal, ProvideQuranAudioRepositoryV4,
    ProvideQuranTafsirRepositoryV4, ProvideQuranTajweedRepositoryV4,
    ProvideQuranTranslationRepositoryV4, ProvideQuranTextRepositoryV4 {

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
            QuranAudioCloudRepositoryAqcImpl(dataModule.provideQuranApiAqc())
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

        override fun provideQuranApiV4(): QuranApiV4 =
            ApiFactory.retrofit.create(QuranApiV4::class.java)

        override fun provideQuranAudioRepositoryV4(): QuranAudioRepositoryV4 =
            QuranAudioRepositoryV4Impl(provideQuranApiV4())

        override fun provideQuranTafsirRepositoryV4(): QuranTafsirRepositoryV4 =
            QuranTafsirRepositoryV4Impl(provideQuranApiV4())

        override fun provideQuranTajweedRepositoryV4(): QuranTajweedRepositoryV4 =
            QuranTajweedRepositoryV4Impl(provideQuranApiV4())

        override fun provideQuranTranslationRepositoryV4(): QuranTranslationRepositoryV4 =
            QuranTranslationRepositoryV4Impl(provideQuranApiV4())

        override fun provideQuranTextRepositoryV4(): QuranTextRepositoryV4 =
            QuranTextRepositoryV4Impl(provideQuranApiV4())

        override fun provideQuranAudioUseCaseV4(): QuranAudioUseCaseV4 =
            QuranAudioUseCaseV4(provideQuranAudioRepositoryV4())

        override fun provideQuranTextUseCaseV4(): QuranTextUseCaseV4 = QuranTextUseCaseV4(
            provideQuranTextRepositoryV4(),
            dataModule.provideQuranStorage(),
            dataModule.provideReciterStorage()
        )

        override fun provideQuranTajweedUseCaseV4(): QuranTajweedUseCaseV4 =
            QuranTajweedUseCaseV4(provideQuranTajweedRepositoryV4())

        override fun provideQuranTafsirUseCaseV4(): QuranTafsirUseCaseV4 =
            QuranTafsirUseCaseV4(provideQuranTafsirRepositoryV4())

        override fun provideQuranTranslationUseCaseV4(): QuranTranslationUseCaseV4 =
            QuranTranslationUseCaseV4(provideQuranTranslationRepositoryV4())

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
    }

}

