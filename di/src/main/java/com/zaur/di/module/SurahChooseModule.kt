package com.zaur.di.module

import com.zaur.data.al_quran_aqc.repository_impl.local.QuranTextLocalRepositoryAqcImpl
import com.zaur.di.provides.ProvideQuranTextRepositoryAqc
import com.zaur.di.provides.ProvideQuranTextUseCaseAqc
import com.zaur.di.provides.ProvideThemeUseCase
import com.zaur.domain.al_quran_cloud.repository.QuranTextRepositoryAqc
import com.zaur.domain.al_quran_cloud.use_case.QuranTextUseCaseAqc
import com.zaur.domain.storage.theme.ThemeUseCase

interface SurahChooseModule : ProvideThemeUseCase, ProvideQuranTextUseCaseAqc,
    ProvideQuranTextRepositoryAqc {

    class Base(private val dataModule: DataModule) : SurahChooseModule {
        private val themeUseCase by lazy {
            ThemeUseCase(dataModule.provideThemeStorage())
        }

        private val quranTextUseCaseAqc by lazy {
            QuranTextUseCaseAqc.Base(
                provideQuranTextRepositoryAqc(), dataModule.provideQuranStorage()
            )
        }

        override fun provideThemeUseCase(): ThemeUseCase = themeUseCase
        override fun provideQuranTextUseCaseAqc(): QuranTextUseCaseAqc = quranTextUseCaseAqc

        override fun provideQuranTextRepositoryAqc(): QuranTextRepositoryAqc =
            QuranTextLocalRepositoryAqcImpl(
                dataModule.provideChapterDao(), dataModule.provideArabicChapterDao()
            )
    }

}