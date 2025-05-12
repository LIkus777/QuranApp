package com.zaur.di.module

import com.zaur.data.al_quran_aqc.repository_impl.cloud.QuranTextCloudRepositoryAqcImpl
import com.zaur.data.al_quran_aqc.repository_impl.local.QuranTextLocalRepositoryAqcImpl
import com.zaur.di.provides.ProvideQuranTextRepositoryAqcCloud
import com.zaur.di.provides.ProvideQuranTextRepositoryAqcLocal
import com.zaur.di.provides.ProvideQuranTextUseCaseAqc
import com.zaur.di.provides.ProvideThemeUseCase
import com.zaur.domain.al_quran_cloud.repository.QuranTextRepositoryAqc
import com.zaur.domain.al_quran_cloud.use_case.QuranTextUseCaseAqc
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

        private val quranTextUseCaseAqc by lazy {
            QuranTextUseCaseAqc.Base(
                dataModule.provideQuranStorage(),
                dataModule.provideOfflineRepository(),
                provideQuranTextRepositoryAqcLocal(),
                provideQuranTextRepositoryAqcCloud()
            )
        }

        override fun provideThemeUseCase(): ThemeUseCase = themeUseCase
        override fun provideQuranTextUseCaseAqc(): QuranTextUseCaseAqc = quranTextUseCaseAqc

        override fun provideQuranTextRepositoryAqcLocal(): QuranTextRepositoryAqc.Local =
            QuranTextLocalRepositoryAqcImpl(
                mapperModule.provideChapterMapper(),
                mapperModule.provideArabicMapper(),
                dataModule.provideChapterDao(),
                dataModule.provideArabicChapterDao()
            )

        override fun provideQuranTextRepositoryAqcCloud(): QuranTextRepositoryAqc.Cloud =
            QuranTextCloudRepositoryAqcImpl(
                dataModule.provideQuranApiAqc()
            )
    }

}