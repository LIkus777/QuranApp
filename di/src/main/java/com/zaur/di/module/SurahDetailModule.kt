package com.zaur.di.module

import com.zaur.data.al_quran_aqc.repository_impl.cloud.EditionCloudRepositoryImpl
import com.zaur.data.al_quran_aqc.repository_impl.cloud.QuranAudioCloudRepositoryImpl
import com.zaur.data.al_quran_aqc.repository_impl.cloud.QuranPageCloudRepositoryImpl
import com.zaur.data.al_quran_aqc.repository_impl.cloud.QuranTextCloudRepositoryImpl
import com.zaur.data.al_quran_aqc.repository_impl.cloud.QuranTranslationCloudRepositoryImpl
import com.zaur.data.al_quran_aqc.repository_impl.local.EditionLocalRepositoryImpl
import com.zaur.data.al_quran_aqc.repository_impl.local.QuranAudioLocalRepositoryImpl
import com.zaur.data.al_quran_aqc.repository_impl.local.QuranPageLocalRepositoryImpl
import com.zaur.data.al_quran_aqc.repository_impl.local.QuranTextLocalRepositoryImpl
import com.zaur.data.al_quran_aqc.repository_impl.local.QuranTranslationLocalRepositoryImpl
import com.zaur.di.provides.ProvideEditionRepositoryCloud
import com.zaur.di.provides.ProvideEditionRepositoryLocal
import com.zaur.di.provides.ProvideEditionUseCase
import com.zaur.di.provides.ProvideEditionViewModelFactory
import com.zaur.di.provides.ProvidePageUseCase
import com.zaur.di.provides.ProvideQuranAudioRepositoryCloud
import com.zaur.di.provides.ProvideQuranAudioRepositoryLocal
import com.zaur.di.provides.ProvideQuranAudioUseCase
import com.zaur.di.provides.ProvideSurahPlayerViewModelFactory
import com.zaur.di.provides.ProvideQuranPageRepositopryCloud
import com.zaur.di.provides.ProvideQuranPageRepositopryLocal
import com.zaur.di.provides.ProvideQuranPageViewModelFactory
import com.zaur.di.provides.ProvideQuranTextRepositoryCloud
import com.zaur.di.provides.ProvideQuranTextRepositoryLocal
import com.zaur.di.provides.ProvideQuranTextUseCase
import com.zaur.di.provides.ProvideQuranTextViewModelFactory
import com.zaur.di.provides.ProvideQuranTranslationRepositoryCloud
import com.zaur.di.provides.ProvideQuranTranslationRepositoryLocal
import com.zaur.di.provides.ProvideQuranTranslationUseCase
import com.zaur.di.provides.ProvideReciterManager
import com.zaur.di.provides.ProvideReciterUseCase
import com.zaur.di.provides.ProvideScreenContentViewModelFactory
import com.zaur.di.provides.ProvideSurahChooseViewModelFactory
import com.zaur.di.provides.ProvideSurahDetailViewModel
import com.zaur.di.provides.ProvideThemeUseCase
import com.zaur.di.provides.ProvideTranslationViewModelFactory
import com.zaur.domain.al_quran_cloud.repository.EditionRepository
import com.zaur.domain.al_quran_cloud.repository.QuranAudioRepository
import com.zaur.domain.al_quran_cloud.repository.QuranPageRepository
import com.zaur.domain.al_quran_cloud.repository.QuranTextRepository
import com.zaur.domain.al_quran_cloud.repository.QuranTranslationRepository
import com.zaur.domain.al_quran_cloud.use_case.EditionUseCase
import com.zaur.domain.al_quran_cloud.use_case.QuranAudioUseCase
import com.zaur.domain.al_quran_cloud.use_case.QuranPageUseCase
import com.zaur.domain.al_quran_cloud.use_case.QuranTextUseCase
import com.zaur.domain.al_quran_cloud.use_case.QuranTranslationUseCase
import com.zaur.domain.al_quran_cloud.use_case.ReciterUseCase
import com.zaur.domain.storage.theme.ThemeUseCase
import com.zaur.features.surah.manager.ReciterManager
import com.zaur.features.surah.viewmodel.SurahDetailViewModel
import com.zaur.features.surah.viewmodel.factory.EditionViewModelFactory
import com.zaur.features.surah.viewmodel.factory.SurahPlayerViewModelFactory
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
    ProvideEditionUseCase, ProvidePageUseCase, ProvideReciterUseCase, ProvideReciterManager,
    ProvideThemeUseCase, ProvideScreenContentViewModelFactory, ProvideSurahChooseViewModelFactory,
    ProvideTranslationViewModelFactory, ProvideEditionViewModelFactory,
    ProvideSurahPlayerViewModelFactory, ProvideQuranPageViewModelFactory,
    ProvideQuranTextViewModelFactory, ProvideSurahDetailViewModel,
    ProvideQuranAudioUseCase, ProvideQuranTextUseCase, ProvideQuranTranslationUseCase,
    ProvideQuranAudioRepositoryLocal, ProvideEditionRepositoryLocal, ProvideEditionRepositoryCloud,
    ProvideQuranAudioRepositoryCloud, ProvideQuranTextRepositoryLocal,
    ProvideQuranTextRepositoryCloud, ProvideQuranTranslationRepositoryCloud,
    ProvideQuranTranslationRepositoryLocal {

    class Base(
        private val dataModule: DataModule,
        private val mapperModule: MapperModule,
        private val audioModule: AudioModule,
        private val surahDetailStateManagerModule: SurahDetailStateManagerModule,
    ) : SurahDetailModule {

        private val themeUseCase by lazy {
            ThemeUseCase(dataModule.provideThemeStorage())
        }

        private val quranTranslationViewModelFactory by lazy {
            QuranTranslationViewModelFactory.Base(quranTranslationUseCase = provideQuranTranslationUseCase())
        }

        private val surahPlayerViewModelFactory by lazy {
            SurahPlayerViewModelFactory.Base(
                audioModule.provideSurahPlayer(),
                provideReciterManager(),
                surahDetailStateManagerModule.provideSurahDetailStateManager(),
                quranAudioUseCase = provideQuranAudioUseCase()
            )
        }

        private val quranTextViewModelFactory by lazy {
            QuranTextViewModelFactory.Base(
                surahDetailStateManager = surahDetailStateManagerModule.provideSurahDetailStateManager(),
                quranTextUseCase = provideQuranTextUseCase()
            )
        }

        private val editionViewModelFactory by lazy {
            EditionViewModelFactory.Base(editionUseCase = provideEditionUseCase())
        }

        private val quranAudioUseCase by lazy {
            QuranAudioUseCase.Base(
                dataModule.provideQuranStorage(),
                dataModule.provideOfflineRepository(),
                provideQuranAudioRepositoryLocal(),
                provideQuranAudioRepositoryCloud()
            )
        }

        private val quranTextUseCase by lazy {
            QuranTextUseCase.Base(
                dataModule.provideQuranStorage(),
                dataModule.provideOfflineRepository(),
                provideQuranTextRepositoryLocal(),
                provideQuranTextRepositoryCloud()
            )
        }

        private val quranTranslationUseCase by lazy {
            QuranTranslationUseCase.Base(
                dataModule.provideOfflineRepository(),
                provideQuranTranslationRepositoryLocal(),
                provideQuranTranslationRepositoryCloud()
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

        override fun provideQuranTextRepositoryLocal(): QuranTextRepository.Local =
            QuranTextLocalRepositoryImpl(
                mapperModule.provideChapterMapper(),
                mapperModule.provideArabicMapper(),
                dataModule.provideChapterDao(),
                dataModule.provideArabicChapterDao()
            )

        override fun provideQuranTranslationRepositoryCloud(): QuranTranslationRepository.Cloud =
            QuranTranslationCloudRepositoryImpl(dataModule.provideQuranApiAqc())

        override fun provideQuranAudioRepositoryLocal(): QuranAudioRepository.Local =
            QuranAudioLocalRepositoryImpl(
                dataModule.provideChapterAudioDao(),
                dataModule.provideVerseAudioDao(),
                mapperModule.provideChapterAudioMapper(),
                mapperModule.provideVerseAudioMapper()
            )

        override fun provideQuranAudioUseCase(): QuranAudioUseCase = quranAudioUseCase

        override fun provideQuranTextUseCase(): QuranTextUseCase = quranTextUseCase

        override fun provideQuranTranslationUseCase(): QuranTranslationUseCase =
            quranTranslationUseCase


        override fun provideQuranTranslationViewModelFactory(): QuranTranslationViewModelFactory =
            quranTranslationViewModelFactory

        override fun provideSurahPlayerViewModelFactory(): SurahPlayerViewModelFactory =
            surahPlayerViewModelFactory

        override fun provideQuranTextViewModelFactory(): QuranTextViewModelFactory =
            quranTextViewModelFactory

        override fun provideSurahDetailViewModel(): SurahDetailViewModel =
            SurahDetailViewModel.Base(surahDetailStateManagerModule.provideSurahDetailStateManager())

        override fun provideReciterManager(): ReciterManager =
            ReciterManager.Base(provideReciterUseCase())

        override fun provideReciterUseCase(): ReciterUseCase =
            ReciterUseCase.Base(dataModule.provideReciterStorage())

        override fun provideQuranAudioRepositoryCloud(): QuranAudioRepository.Cloud =
            audioRepositoryCloud

        override fun provideQuranTextRepositoryCloud(): QuranTextRepository.Cloud =
            textRepositoryCloud

        override fun provideQuranTranslationRepositoryLocal(): QuranTranslationRepository.Local =
            QuranTranslationLocalRepositoryImpl(
                dataModule.provideTranslationChapterDao(), mapperModule.provideTranslationMapper()
            )

        override fun provideSurahChooseViewModelFactory(): SurahChooseViewModelFactory =
            SurahChooseViewModelFactory.Base(quranTextUseCase = provideQuranTextUseCase())

        override fun provideScreenContentViewModelFactory(): ScreenContentViewModelFactory =
            ScreenContentViewModelFactory.Base(surahDetailStateManagerModule.provideSurahDetailStateManager())

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

        override fun provideEditionViewModelFactory(): EditionViewModelFactory =
            editionViewModelFactory

        override fun provideEditionUseCase(): EditionUseCase = EditionUseCase.Base(
            dataModule.provideOfflineRepository(),
            provideEditionRepositoryLocal(),
            provideEditionRepositoryCloud()
        )

        override fun provideEditionRepositoryCloud(): EditionRepository.Cloud =
            EditionCloudRepositoryImpl(dataModule.provideQuranApiAqc())

        override fun provideEditionRepositoryLocal(): EditionRepository.Local =
            EditionLocalRepositoryImpl()
    }

}