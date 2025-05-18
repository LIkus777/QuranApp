package com.zaur.di.module

import android.content.Context
import com.zaur.data.al_quran_aqc.repository_impl.cloud.QuranAudioCloudRepositoryImpl
import com.zaur.data.al_quran_aqc.repository_impl.cloud.QuranPageCloudRepositoryImpl
import com.zaur.data.al_quran_aqc.repository_impl.cloud.QuranTextCloudRepositoryImpl
import com.zaur.data.al_quran_aqc.repository_impl.cloud.QuranTranslationCloudRepositoryImpl
import com.zaur.data.al_quran_aqc.repository_impl.local.QuranAudioLocalRepositoryImpl
import com.zaur.data.al_quran_aqc.repository_impl.local.QuranPageLocalRepositoryImpl
import com.zaur.data.al_quran_aqc.repository_impl.local.QuranTextLocalRepositoryImpl
import com.zaur.data.al_quran_aqc.repository_impl.local.QuranTranslationLocalRepositoryImpl
import com.zaur.di.provides.ProvideAudioPlaybackHelper
import com.zaur.di.provides.ProvideAudioPlayer
import com.zaur.di.provides.ProvideAudioPlayerStateUpdater
import com.zaur.di.provides.ProvidePageUseCase
import com.zaur.di.provides.ProvidePlaylistBuilder
import com.zaur.di.provides.ProvideQuranAudioRepositoryAqcCloud
import com.zaur.di.provides.ProvideQuranAudioRepositoryAqcLocal
import com.zaur.di.provides.ProvideQuranAudioUseCaseAqc
import com.zaur.di.provides.ProvideQuranAudioViewModelFactory
import com.zaur.di.provides.ProvideQuranPageRepositopryCloud
import com.zaur.di.provides.ProvideQuranPageRepositopryLocal
import com.zaur.di.provides.ProvideQuranPageViewModelFactory
import com.zaur.di.provides.ProvideQuranTextRepositoryAqcCloud
import com.zaur.di.provides.ProvideQuranTextRepositoryAqcLocal
import com.zaur.di.provides.ProvideQuranTextUseCaseAqc
import com.zaur.di.provides.ProvideQuranTextViewModelFactory
import com.zaur.di.provides.ProvideQuranTranslationRepositoryAqcCloud
import com.zaur.di.provides.ProvideQuranTranslationRepositoryAqcLocal
import com.zaur.di.provides.ProvideQuranTranslationUseCaseAqc
import com.zaur.di.provides.ProvideReciterManager
import com.zaur.di.provides.ProvideReciterUseCase
import com.zaur.di.provides.ProvideScreenContentViewModelFactory
import com.zaur.di.provides.ProvideSurahChooseViewModelFactory
import com.zaur.di.provides.ProvideSurahDetailStateManager
import com.zaur.di.provides.ProvideSurahDetailViewModel
import com.zaur.di.provides.ProvideSurahPlayer
import com.zaur.di.provides.ProvideThemeUseCase
import com.zaur.di.provides.ProvideTranslationViewModelFactory
import com.zaur.domain.al_quran_cloud.repository.QuranAudioRepository
import com.zaur.domain.al_quran_cloud.repository.QuranPageRepository
import com.zaur.domain.al_quran_cloud.repository.QuranTextRepository
import com.zaur.domain.al_quran_cloud.repository.QuranTranslationRepository
import com.zaur.domain.al_quran_cloud.use_case.QuranAudioUseCase
import com.zaur.domain.al_quran_cloud.use_case.QuranPageUseCase
import com.zaur.domain.al_quran_cloud.use_case.QuranTextUseCase
import com.zaur.domain.al_quran_cloud.use_case.QuranTranslationUseCase
import com.zaur.domain.al_quran_cloud.use_case.ReciterUseCase
import com.zaur.domain.storage.theme.ThemeUseCase
import com.zaur.features.surah.base.AudioPlayer
import com.zaur.features.surah.manager.ReciterManager
import com.zaur.features.surah.manager.SurahDetailStateManager
import com.zaur.features.surah.screen.surah_detail.player.AudioPlaybackHelper
import com.zaur.features.surah.screen.surah_detail.player.AudioPlayerStateUpdater
import com.zaur.features.surah.screen.surah_detail.player.PlaylistBuilder
import com.zaur.features.surah.screen.surah_detail.player.SurahPlayer
import com.zaur.features.surah.viewmodel.SurahDetailViewModel
import com.zaur.features.surah.viewmodel.factory.QuranAudioViewModelFactory
import com.zaur.features.surah.viewmodel.factory.QuranPageViewModelFactory
import com.zaur.features.surah.viewmodel.factory.QuranTextViewModelFactory
import com.zaur.features.surah.viewmodel.factory.QuranTranslationViewModelFactory
import com.zaur.features.surah.viewmodel.factory.ScreenContentViewModelFactory
import com.zaur.features.surah.viewmodel.factory.SurahChooseViewModelFactory

/**
 * @author Zaur
 * @since 2025-05-12
 */

interface SurahDetailModule : ProvideQuranPageRepositopryCloud, ProvideQuranPageRepositopryLocal,
    ProvidePageUseCase, ProvideReciterUseCase, ProvideReciterManager, ProvideThemeUseCase,
    ProvideScreenContentViewModelFactory, ProvideSurahChooseViewModelFactory,
    ProvideTranslationViewModelFactory, ProvideQuranAudioViewModelFactory,
    ProvideQuranPageViewModelFactory, ProvideQuranTextViewModelFactory, ProvideSurahDetailViewModel,
    ProvideSurahPlayer, ProvideSurahDetailStateManager, ProvideAudioPlayer, ProvidePlaylistBuilder,
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
            QuranTranslationViewModelFactory.Base(quranTranslationUseCase = provideQuranTranslationUseCaseAqc())
        }

        private val quranAudioViewModelFactory by lazy {
            QuranAudioViewModelFactory.Base(
                provideSurahPlayer(),
                provideReciterManager(),
                provideSurahDetailStateManager(),
                quranAudioUseCase = provideQuranAudioUseCaseAqc()
            )
        }

        private val quranTextViewModelFactory by lazy {
            QuranTextViewModelFactory.Base(surahDetailStateManager = provideSurahDetailStateManager(), quranTextUseCase = provideQuranTextUseCaseAqc())
        }

        private val quranAudioUseCase by lazy {
            QuranAudioUseCase.Base(
                dataModule.provideOfflineRepository(),
                provideQuranAudioRepositoryAqcLocal(),
                provideQuranAudioRepositoryAqcCloud()
            )
        }

        private val quranTextUseCase by lazy {
            QuranTextUseCase.Base(
                dataModule.provideQuranStorage(),
                dataModule.provideOfflineRepository(),
                provideQuranTextRepositoryAqcLocal(),
                provideQuranTextRepositoryAqcCloud()
            )
        }

        private val quranTranslationUseCase by lazy {
            QuranTranslationUseCase.Base(
                dataModule.provideOfflineRepository(),
                provideQuranTranslationRepositoryAqcLocal(),
                provideQuranTranslationRepositoryAqcCloud()
            )
        }

        private val audioRepositoryCloud by lazy {
            QuranAudioCloudRepositoryImpl(
                dataModule.provideQuranApiAqc(), dataModule.provideAudioDownloader()
            )
        }

        private val textRepositoryCloud by lazy {
            QuranTextCloudRepositoryImpl(
                dataModule.provideQuranApiAqc()
            )
        }

        override fun provideThemeUseCase(): ThemeUseCase = themeUseCase

        override fun provideQuranTextRepositoryAqcLocal(): QuranTextRepository.Local =
            QuranTextLocalRepositoryImpl(
                mapperModule.provideChapterMapper(),
                mapperModule.provideArabicMapper(),
                dataModule.provideChapterDao(),
                dataModule.provideArabicChapterDao()
            )

        override fun provideQuranTranslationRepositoryAqcCloud(): QuranTranslationRepository.Cloud =
            QuranTranslationCloudRepositoryImpl(dataModule.provideQuranApiAqc())

        override fun provideQuranAudioRepositoryAqcLocal(): QuranAudioRepository.Local =
            QuranAudioLocalRepositoryImpl(
                dataModule.provideChapterAudioDao(),
                dataModule.provideVerseAudioDao(),
                mapperModule.provideChapterAudioMapper(),
                mapperModule.provideVerseAudioMapper()
            )

        override fun provideQuranAudioUseCaseAqc(): QuranAudioUseCase = quranAudioUseCase

        override fun provideQuranTextUseCaseAqc(): QuranTextUseCase = quranTextUseCase

        override fun provideQuranTranslationUseCaseAqc(): QuranTranslationUseCase =
            quranTranslationUseCase

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

        override fun provideQuranAudioRepositoryAqcCloud(): QuranAudioRepository.Cloud =
            audioRepositoryCloud

        override fun provideQuranTextRepositoryAqcCloud(): QuranTextRepository.Cloud =
            textRepositoryCloud

        override fun provideQuranTranslationRepositoryAqcLocal(): QuranTranslationRepository.Local =
            QuranTranslationLocalRepositoryImpl(
                dataModule.provideTranslationChapterDao(), mapperModule.provideTranslationMapper()
            )

        override fun provideSurahChooseViewModelFactory(): SurahChooseViewModelFactory =
            SurahChooseViewModelFactory.Base(quranTextUseCase = provideQuranTextUseCaseAqc())

        override fun provideScreenContentViewModelFactory(): ScreenContentViewModelFactory =
            ScreenContentViewModelFactory.Base(provideSurahDetailStateManager())

        override fun provideQuranPageViewModelFactory(): QuranPageViewModelFactory =
            QuranPageViewModelFactory.Base(quranPageUseCase = providePageUseCase())

        override fun providePageUseCase(): QuranPageUseCase = QuranPageUseCase.Base(
            dataModule.provideQuranStorage(),
            dataModule.provideOfflineRepository(),
            provideQuranPageRepositoryCloud(),
            provideQuranPageRepositoryLocal()
        )

        override fun provideQuranPageRepositoryCloud(): QuranPageRepository.Cloud =
            QuranPageCloudRepositoryImpl(dataModule.provideQuranApiAqc())

        override fun provideQuranPageRepositoryLocal(): QuranPageRepository.Local =
            QuranPageLocalRepositoryImpl(dataModule.provideQuranApiAqc())
    }

}

