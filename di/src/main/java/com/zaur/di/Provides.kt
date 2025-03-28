package com.zaur.di

import com.zaur.data.apiV4.api.QuranApiV4
import com.zaur.domain.apiV4.repository.QuranAudioRepositoryV4
import com.zaur.domain.apiV4.repository.QuranTafsirRepositoryV4
import com.zaur.domain.apiV4.repository.QuranTajweedRepositoryV4
import com.zaur.domain.apiV4.repository.QuranTextRepositoryV4
import com.zaur.domain.apiV4.repository.QuranTranslationRepositoryV4
import com.zaur.domain.storage.QuranStorage
import com.zaur.domain.apiV4.use_case.QuranAudioUseCaseV4
import com.zaur.domain.apiV4.use_case.QuranTafsirUseCaseV4
import com.zaur.domain.apiV4.use_case.QuranTajweedUseCaseV4
import com.zaur.domain.apiV4.use_case.QuranTextUseCaseV4
import com.zaur.domain.apiV4.use_case.QuranTranslationUseCaseV4

interface ProvideQuranStorage {
    fun provideQuranStorage(): QuranStorage
}

interface ProvideQuranAudioUseCase {
    fun provideQuranAudioUseCase(): QuranAudioUseCaseV4
}

interface ProvideQuranTafsirUseCase {
    fun provideQuranTafsirUseCase(): QuranTafsirUseCaseV4
}

interface ProvideQuranTajweedUseCase {
    fun provideQuranTajweedUseCase(): QuranTajweedUseCaseV4
}

interface ProvideQuranTextUseCase {
    fun provideQuranTextUseCase(): QuranTextUseCaseV4
}

interface ProvideQuranTranslationUseCase {
    fun provideQuranTranslationUseCase(): QuranTranslationUseCaseV4
}

interface ProvideQuranApi {
    fun provideQuranApi(): QuranApiV4
}

interface ProvideQuranAudioRepository {
    fun provideQuranAudioRepository(): QuranAudioRepositoryV4
}

interface ProvideQuranTafsirRepository {
    fun provideQuranTafsirRepository(): QuranTafsirRepositoryV4
}

interface ProvideQuranTajweedRepository {
    fun provideQuranTajweedRepository(): QuranTajweedRepositoryV4
}

interface ProvideQuranTranslationRepository {
    fun provideQuranTranslationRepository(): QuranTranslationRepositoryV4
}

interface ProvideQuranTextRepository {
    fun provideQuranTextRepository(): QuranTextRepositoryV4
}