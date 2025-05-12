package com.zaur.di.module

import com.zaur.data.al_quran_aqc.repository_impl.cloud.MainRepositoryLoadImpl
import com.zaur.data.al_quran_aqc.repository_impl.local.MainRepositorySaveImpl
import com.zaur.di.provides.ProvideMainRepositoryLoad
import com.zaur.di.provides.ProvideMainRepositorySave
import com.zaur.di.provides.ProvideMainUseCase
import com.zaur.di.provides.ProvideReciterManager
import com.zaur.di.provides.ProvideReciterUseCase
import com.zaur.di.provides.ProvideThemeUseCase
import com.zaur.domain.al_quran_cloud.repository.MainRepository
import com.zaur.domain.al_quran_cloud.use_case.MainUseCase
import com.zaur.domain.al_quran_cloud.use_case.ReciterUseCase
import com.zaur.domain.storage.theme.ThemeUseCase
import com.zaur.features.surah.manager.ReciterManager

/**
* @author Zaur
* @since 2025-05-12
*/

interface MainScreenModule : ProvideReciterUseCase, ProvideReciterManager,
    ProvideMainRepositorySave, ProvideMainRepositoryLoad, ProvideThemeUseCase, ProvideMainUseCase {

    class Base(private val dataModule: DataModule, private val mapperModule: MapperModule) : MainScreenModule {

        private val themeUseCase by lazy {
            ThemeUseCase(dataModule.provideThemeStorage())
        }

        private val mainUseCase by lazy {
            MainUseCase.Base(provideMainRepositoryLoad(), provideMainRepositorySave())
        }

        override fun provideThemeUseCase(): ThemeUseCase = themeUseCase

        override fun provideMainRepositorySave(): MainRepository.Save = MainRepositorySaveImpl(
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

        override fun provideMainRepositoryLoad(): MainRepository.Load =
            MainRepositoryLoadImpl(dataModule.provideQuranApiAqc())

        override fun provideMainUseCase(): MainUseCase = mainUseCase

        override fun provideReciterManager(): ReciterManager =
            ReciterManager.Base(provideReciterUseCase())

        override fun provideReciterUseCase(): ReciterUseCase =
            ReciterUseCase.Base(dataModule.provideReciterStorage())
    }
}