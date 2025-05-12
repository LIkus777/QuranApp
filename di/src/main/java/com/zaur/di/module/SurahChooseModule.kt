package com.zaur.di.module

import com.zaur.data.al_quran_aqc.repository_impl.cloud.QuranTextCloudRepositoryImpl
import com.zaur.data.al_quran_aqc.repository_impl.local.QuranTextLocalRepositoryImpl
import com.zaur.di.provides.ProvideQuranTextRepositoryAqcCloud
import com.zaur.di.provides.ProvideQuranTextRepositoryAqcLocal
import com.zaur.di.provides.ProvideQuranTextUseCaseAqc
import com.zaur.di.provides.ProvideThemeUseCase
import com.zaur.domain.al_quran_cloud.repository.QuranTextRepository
import com.zaur.domain.al_quran_cloud.use_case.QuranTextUseCase
import com.zaur.domain.storage.theme.ThemeUseCase

/**
* @author Zaur
* @since 2025-05-12
*/

interface SurahChooseModule : ProvideThemeUseCase, ProvideQuranTextUseCaseAqc,
    ProvideQuranTextRepositoryAqcLocal, ProvideQuranTextRepositoryAqcCloud {

    class Base(private val dataModule: DataModule, private val mapperModule: MapperModule) :
        SurahChooseModule {
        private val themeUseCase by lazy {
            ThemeUseCase(dataModule.provideThemeStorage())
        }

        private val quranTextUseCase by lazy {
            QuranTextUseCase.Base(
                dataModule.provideQuranStorage(),
                dataModule.provideOfflineRepository(),
                provideQuranTextRepositoryAqcLocal(),
                provideQuranTextRepositoryAqcCloud()
            )
        }

        override fun provideThemeUseCase(): ThemeUseCase = themeUseCase
        override fun provideQuranTextUseCaseAqc(): QuranTextUseCase = quranTextUseCase

        override fun provideQuranTextRepositoryAqcLocal(): QuranTextRepository.Local =
            QuranTextLocalRepositoryImpl(
                mapperModule.provideChapterMapper(),
                mapperModule.provideArabicMapper(),
                dataModule.provideChapterDao(),
                dataModule.provideArabicChapterDao()
            )

        override fun provideQuranTextRepositoryAqcCloud(): QuranTextRepository.Cloud =
            QuranTextCloudRepositoryImpl(
                dataModule.provideQuranApiAqc()
            )
    }

}