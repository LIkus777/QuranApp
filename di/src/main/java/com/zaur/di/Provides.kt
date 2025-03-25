package com.zaur.di

import com.zaur.data.api.QuranApi
import com.zaur.domain.repository.QuranAudioRepository
import com.zaur.domain.repository.QuranTafsirRepository
import com.zaur.domain.repository.QuranTajweedRepository
import com.zaur.domain.repository.QuranTextRepository
import com.zaur.domain.repository.QuranTranslationRepository

interface ProvideQuranApi {
    fun provideQuranApi(): QuranApi
}

interface ProvideQuranAudioRepository {
    fun provideQuranAudioRepository(): QuranAudioRepository
}

interface ProvideQuranTafsirRepository {
    fun provideQuranTafsirRepository(): QuranTafsirRepository
}

interface ProvideQuranTajweedRepository {
    fun provideQuranTajweedRepository(): QuranTajweedRepository
}

interface ProvideQuranTranslationRepository {
    fun provideQuranTranslationRepository(): QuranTranslationRepository
}

interface ProvideQuranTextRepository {
    fun provideQuranTextRepository(): QuranTextRepository
}