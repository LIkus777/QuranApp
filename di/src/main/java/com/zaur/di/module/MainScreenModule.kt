package com.zaur.di.module

import com.zaur.data.room.repository.MainRepositoryLoadImpl
import com.zaur.data.room.repository.MainRepositorySaveImpl
import com.zaur.di.provides.ProvideMainRepositoryLoad
import com.zaur.di.provides.ProvideMainRepositorySave
import com.zaur.di.provides.ProvideMainUseCase
import com.zaur.di.provides.ProvideThemeUseCase
import com.zaur.domain.al_quran_cloud.repository.MainRepository
import com.zaur.domain.al_quran_cloud.use_case.MainUseCase
import com.zaur.domain.storage.theme.ThemeUseCase

interface MainScreenModule : ProvideMainRepositorySave, ProvideMainRepositoryLoad,
    ProvideThemeUseCase, ProvideMainUseCase {

    class Base(private val dataModule: DataModule) : MainScreenModule {

        private val themeUseCase by lazy {
            ThemeUseCase(dataModule.provideThemeStorage())
        }

        private val mainUseCase by lazy {
            MainUseCase.Base(provideMainRepositoryLoad(), provideMainRepositorySave())
        }

        override fun provideThemeUseCase(): ThemeUseCase = themeUseCase

        override fun provideMainRepositorySave(): MainRepository.Save = MainRepositorySaveImpl(
            dataModule.provideAudioDownloader(),
            dataModule.provideChapterDao(),
            dataModule.provideVerseAudioDao(),
            dataModule.provideChapterAudioDao(),
            dataModule.provideArabicChapterDao(),
            dataModule.provideTranslationChapterDao()
        )

        override fun provideMainRepositoryLoad(): MainRepository.Load =
            MainRepositoryLoadImpl(dataModule.provideQuranApiAqc())

        override fun provideMainUseCase(): MainUseCase = mainUseCase


    }


}