package com.zaur.di

import android.content.Context
import com.zaur.data.apiV4.api.QuranApiV4
import com.zaur.data.network.ApiFactory
import com.zaur.data.preferences.QuranPreferences
import com.zaur.data.apiV4.repository_impl.QuranAudioRepositoryImpl
import com.zaur.data.apiV4.repository_impl.QuranTafsirRepositoryImpl
import com.zaur.data.apiV4.repository_impl.QuranTajweedRepositoryImpl
import com.zaur.data.apiV4.repository_impl.QuranTextRepositoryImpl
import com.zaur.data.apiV4.repository_impl.QuranTranslationRepositoryImpl
import com.zaur.domain.storage.QuranStorage
import com.zaur.domain.repository.QuranAudioRepository
import com.zaur.domain.repository.QuranTafsirRepository
import com.zaur.domain.repository.QuranTajweedRepository
import com.zaur.domain.repository.QuranTextRepository
import com.zaur.domain.repository.QuranTranslationRepository
import com.zaur.domain.apiV4.use_case.QuranAudioUseCaseV4
import com.zaur.domain.apiV4.use_case.QuranTafsirUseCaseV4
import com.zaur.domain.apiV4.use_case.QuranTajweedUseCaseV4
import com.zaur.domain.apiV4.use_case.QuranTextUseCaseV4
import com.zaur.domain.apiV4.use_case.QuranTranslationUseCaseV4

interface DI : ProvideQuranStorage, ProvideQuranAudioUseCase, ProvideQuranTextUseCase,
    ProvideQuranTajweedUseCase,
    ProvideQuranTafsirUseCase, ProvideQuranTranslationUseCase, ProvideQuranApi,
    ProvideQuranAudioRepository, ProvideQuranTafsirRepository, ProvideQuranTajweedRepository,
    ProvideQuranTranslationRepository, ProvideQuranTextRepository {

    class Base(private val context: Context) : DI {
        override fun provideQuranApi(): QuranApiV4 = ApiFactory.retrofit.create(QuranApiV4::class.java)
        override fun provideQuranAudioRepository(): QuranAudioRepository =
            QuranAudioRepositoryImpl(provideQuranApi())

        override fun provideQuranTafsirRepository(): QuranTafsirRepository =
            QuranTafsirRepositoryImpl(provideQuranApi())

        override fun provideQuranTajweedRepository(): QuranTajweedRepository =
            QuranTajweedRepositoryImpl(provideQuranApi())

        override fun provideQuranTranslationRepository(): QuranTranslationRepository =
            QuranTranslationRepositoryImpl(provideQuranApi())

        override fun provideQuranTextRepository(): QuranTextRepository =
            QuranTextRepositoryImpl(provideQuranApi())

        override fun provideQuranAudioUseCase(): QuranAudioUseCaseV4 =
            QuranAudioUseCaseV4(provideQuranAudioRepository())

        override fun provideQuranTextUseCase(): QuranTextUseCaseV4 =
            QuranTextUseCaseV4(provideQuranTextRepository(), provideQuranStorage())

        override fun provideQuranTajweedUseCase(): QuranTajweedUseCaseV4 =
            QuranTajweedUseCaseV4(provideQuranTajweedRepository())

        override fun provideQuranTafsirUseCase(): QuranTafsirUseCaseV4 =
            QuranTafsirUseCaseV4(provideQuranTafsirRepository())

        override fun provideQuranTranslationUseCase(): QuranTranslationUseCaseV4 =
            QuranTranslationUseCaseV4(provideQuranTranslationRepository())

        override fun provideQuranStorage(): QuranStorage = QuranPreferences(context)
    }

}

