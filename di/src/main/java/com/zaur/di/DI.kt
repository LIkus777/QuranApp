package com.zaur.di

import android.content.Context
import com.zaur.data.api.QuranApi
import com.zaur.data.network.ApiFactory
import com.zaur.data.repository_impl.QuranAudioRepositoryImpl
import com.zaur.data.repository_impl.QuranTafsirRepositoryImpl
import com.zaur.data.repository_impl.QuranTajweedRepositoryImpl
import com.zaur.data.repository_impl.QuranTextRepositoryImpl
import com.zaur.data.repository_impl.QuranTranslationRepositoryImpl
import com.zaur.domain.repository.QuranAudioRepository
import com.zaur.domain.repository.QuranTafsirRepository
import com.zaur.domain.repository.QuranTajweedRepository
import com.zaur.domain.repository.QuranTextRepository
import com.zaur.domain.repository.QuranTranslationRepository
import com.zaur.domain.use_case.QuranAudioUseCase
import com.zaur.domain.use_case.QuranTafsirUseCase
import com.zaur.domain.use_case.QuranTajweedUseCase
import com.zaur.domain.use_case.QuranTextUseCase
import com.zaur.domain.use_case.QuranTranslationUseCase

interface DI : ProvideQuranAudioUseCase, ProvideQuranTextUseCase, ProvideQuranTajweedUseCase,
    ProvideQuranTafsirUseCase, ProvideQuranTranslationUseCase, ProvideQuranApi,
    ProvideQuranAudioRepository, ProvideQuranTafsirRepository, ProvideQuranTajweedRepository,
    ProvideQuranTranslationRepository, ProvideQuranTextRepository {

    class Base(private val context: Context) : DI {
        override fun provideQuranApi(): QuranApi = ApiFactory.retrofit.create(QuranApi::class.java)
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

        override fun provideQuranAudioUseCase(): QuranAudioUseCase =
            QuranAudioUseCase(provideQuranAudioRepository())

        override fun provideQuranTextUseCase(): QuranTextUseCase =
            QuranTextUseCase(provideQuranTextRepository())

        override fun provideQuranTajweedUseCase(): QuranTajweedUseCase =
            QuranTajweedUseCase(provideQuranTajweedRepository())

        override fun provideQuranTafsirUseCase(): QuranTafsirUseCase =
            QuranTafsirUseCase(provideQuranTafsirRepository())

        override fun provideQuranTranslationUseCase(): QuranTranslationUseCase =
            QuranTranslationUseCase(provideQuranTranslationRepository())
    }

}

