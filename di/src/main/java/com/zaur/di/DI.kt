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

interface DI : ProvideQuranApi, ProvideQuranAudioRepository, ProvideQuranTafsirRepository,
    ProvideQuranTajweedRepository, ProvideQuranTranslationRepository, ProvideQuranTextRepository {

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
    }

}

