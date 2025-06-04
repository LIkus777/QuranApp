package com.zaur.di.module

import com.zaur.data.al_quran_aqc.repository_impl.cloud.MainRepositoryCloudImpl
import com.zaur.data.al_quran_aqc.repository_impl.local.MainRepositoryLocalImpl
import com.zaur.di.provides.ProvideMainRepositoryLoad
import com.zaur.di.provides.ProvideMainRepositorySave
import com.zaur.di.provides.ProvideMainUseCase
import com.zaur.di.provides.ProvideReciterManager
import com.zaur.di.provides.ProvideReciterUseCase
import com.zaur.di.provides.ProvideThemeUseCase
import com.zaur.di.provides.ProvideTranslatorManager
import com.zaur.di.provides.ProvideTranslatorUseCase
import com.zaur.domain.al_quran_cloud.repository.MainRepository
import com.zaur.domain.al_quran_cloud.use_case.MainUseCase
import com.zaur.domain.al_quran_cloud.use_case.ReciterUseCase
import com.zaur.domain.al_quran_cloud.use_case.TranslatorUseCase
import com.zaur.domain.storage.theme.ThemeUseCase
import com.zaur.features.surah.manager.ReciterManager
import com.zaur.features.surah.manager.TranslatorManager

/**
 * @author Zaur
 * @since 2025-05-12
 */

interface MainScreenModule : ProvideTranslatorManager, ProvideTranslatorUseCase, ProvideReciterUseCase, ProvideReciterManager,
    ProvideMainRepositorySave, ProvideMainRepositoryLoad, ProvideThemeUseCase, ProvideMainUseCase {

    class Base(private val dataModule: DataModule, private val mapperModule: MapperModule) :
        MainScreenModule {

        private val themeUseCase by lazy {
            ThemeUseCase(dataModule.provideThemeStorage())
        }

        private val mainUseCase by lazy {
            MainUseCase.Base(
                dataModule.provideQuranStorage(),
                provideMainRepositoryLoad(),
                provideMainRepositorySave()
            )
        }

        override fun provideThemeUseCase(): ThemeUseCase = themeUseCase

        override fun provideMainRepositorySave(): MainRepository.Local = MainRepositoryLocalImpl(
            dataModule.provideAudioDownloader(),
            mapperModule.provideChapterMapper(),
            mapperModule.provideChapterAudioMapper(),
            mapperModule.provideVerseAudioMapper(),
            mapperModule.provideTranslationMapper(),
            mapperModule.provideArabicMapper(),
            dataModule.provideChapterDao(),
            dataModule.provideVerseAudioDao(),
            dataModule.provideChapterAudioDao(),
            dataModule.provideArabicChapterDao(),
            dataModule.provideTranslationChapterDao()
        )

        override fun provideMainRepositoryLoad(): MainRepository.Cloud =
            MainRepositoryCloudImpl(dataModule.provideQuranApiAqc(), dataModule.provideAssetsQuranLoader())

        override fun provideMainUseCase(): MainUseCase = mainUseCase

        override fun provideReciterManager(): ReciterManager =
            ReciterManager.Base(provideReciterUseCase())

        override fun provideReciterUseCase(): ReciterUseCase =
            ReciterUseCase.Base(dataModule.provideReciterStorage())

        override fun provideTranslatorManager(): TranslatorManager = TranslatorManager.Base(provideTranslatorUseCase())

        override fun provideTranslatorUseCase(): TranslatorUseCase = TranslatorUseCase.Base(dataModule.provideTranslatorStorage())
    }
}