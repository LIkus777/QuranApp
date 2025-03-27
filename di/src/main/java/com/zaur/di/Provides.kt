package com.zaur.di

import com.zaur.data.api.QuranApi
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

interface ProvideQuranAudioUseCase {
    fun provideQuranAudioUseCase(): QuranAudioUseCase
}

interface ProvideQuranTafsirUseCase {
    fun provideQuranTafsirUseCase(): QuranTafsirUseCase
}

interface ProvideQuranTajweedUseCase {
    fun provideQuranTajweedUseCase(): QuranTajweedUseCase
}

interface ProvideQuranTextUseCase {
    fun provideQuranTextUseCase(): QuranTextUseCase
}

interface ProvideQuranTranslationUseCase {
    fun provideQuranTranslationUseCase(): QuranTranslationUseCase
}

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