package com.zaur.di

import android.content.Context
import com.zaur.data.al_quran_aqc.api.QuranApiAqc
import com.zaur.data.al_quran_aqc.repository_impl.QuranAudioRepositoryAqcImpl
import com.zaur.data.al_quran_aqc.repository_impl.QuranTextRepositoryAqcImpl
import com.zaur.data.al_quran_aqc.repository_impl.QuranTranslationRepositoryAqcImpl
import com.zaur.data.apiV4.api.QuranApiV4
import com.zaur.data.apiV4.repository_impl.QuranAudioRepositoryV4Impl
import com.zaur.data.apiV4.repository_impl.QuranTafsirRepositoryV4Impl
import com.zaur.data.apiV4.repository_impl.QuranTajweedRepositoryV4Impl
import com.zaur.data.apiV4.repository_impl.QuranTextRepositoryV4Impl
import com.zaur.data.apiV4.repository_impl.QuranTranslationRepositoryV4Impl
import com.zaur.data.network.ApiFactory
import com.zaur.data.preferences.QuranPreferences
import com.zaur.data.preferences.ReciterPreferences
import com.zaur.data.preferences.ThemePreferences
import com.zaur.domain.al_quran_cloud.repository.QuranAudioRepositoryAqc
import com.zaur.domain.al_quran_cloud.repository.QuranTextRepositoryAqc
import com.zaur.domain.al_quran_cloud.repository.QuranTranslationRepositoryAqc
import com.zaur.domain.al_quran_cloud.use_case.QuranAudioUseCaseAqc
import com.zaur.domain.al_quran_cloud.use_case.QuranTextUseCaseAqc
import com.zaur.domain.al_quran_cloud.use_case.QuranTranslationUseCaseAqc
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
import com.zaur.domain.storage.QuranStorage
import com.zaur.domain.storage.ReciterStorage
import com.zaur.domain.storage.theme.ThemeStorage
import com.zaur.domain.storage.theme.ThemeUseCase

interface DI : ProvideThemeStorage, ProvideReciterStorage, ProvideQuranStorage, ProvideThemeUseCase, ProvideQuranAudioUseCaseAqc,
    ProvideQuranTextUseCaseAqc, ProvideQuranTranslationUseCaseAqc, ProvideQuranAudioUseCaseV4,
    ProvideQuranTextUseCase, ProvideQuranTajweedUseCase, ProvideQuranTafsirUseCase,
    ProvideQuranTranslationUseCase, ProvideQuranApiAqc, ProvideQuranApiV4,
    ProvideQuranAudioRepositoryAqc, ProvideQuranTextRepositoryAqc,
    ProvideQuranTranslationRepositoryAqc, ProvideQuranAudioRepositoryV4,
    ProvideQuranTafsirRepositoryV4, ProvideQuranTajweedRepositoryV4,
    ProvideQuranTranslationRepositoryV4, ProvideQuranTextRepositoryV4 {

    class Base(private val context: Context) : DI {
        override fun provideQuranApiV4(): QuranApiV4 =
            ApiFactory.retrofit.create(QuranApiV4::class.java)

        override fun provideQuranApiAqc(): QuranApiAqc =
            ApiFactory.retrofit.create(QuranApiAqc::class.java)

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
            provideQuranStorage(),
            provideReciterStorage()
        )

        override fun provideQuranTajweedUseCaseV4(): QuranTajweedUseCaseV4 =
            QuranTajweedUseCaseV4(provideQuranTajweedRepositoryV4())

        override fun provideQuranTafsirUseCaseV4(): QuranTafsirUseCaseV4 =
            QuranTafsirUseCaseV4(provideQuranTafsirRepositoryV4())

        override fun provideQuranTranslationUseCaseV4(): QuranTranslationUseCaseV4 =
            QuranTranslationUseCaseV4(provideQuranTranslationRepositoryV4())

        override fun provideQuranStorage(): QuranStorage = QuranPreferences(context)

        override fun provideReciterStorage(): ReciterStorage = ReciterPreferences(context)

        override fun provideThemeStorage(): ThemeStorage = ThemePreferences(context)

        override fun provideQuranAudioUseCaseAqc(): QuranAudioUseCaseAqc =
            QuranAudioUseCaseAqc(provideQuranAudioRepositoryAqc())

        override fun provideQuranTextUseCaseAqc(): QuranTextUseCaseAqc = QuranTextUseCaseAqc(
            provideQuranTextRepositoryAqc(),
            provideQuranStorage(),
            provideReciterStorage()
        )

        override fun provideQuranTranslationUseCaseAqc(): QuranTranslationUseCaseAqc =
            QuranTranslationUseCaseAqc(provideQuranTranslationRepositoryAqc())

        override fun provideQuranTextRepositoryAqc(): QuranTextRepositoryAqc =
            QuranTextRepositoryAqcImpl(provideQuranApiAqc())

        override fun provideQuranTranslationRepositoryAqc(): QuranTranslationRepositoryAqc =
            QuranTranslationRepositoryAqcImpl(provideQuranApiAqc())

        override fun provideQuranAudioRepositoryAqc(): QuranAudioRepositoryAqc =
            QuranAudioRepositoryAqcImpl(provideQuranApiAqc())

        override fun provideThemeUseCase(): ThemeUseCase = ThemeUseCase(provideThemeStorage())
    }

}

