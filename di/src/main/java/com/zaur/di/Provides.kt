package com.zaur.di

import com.zaur.data.al_quran_aqc.api.QuranApiAqc
import com.zaur.data.apiV4.api.QuranApiV4
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
import com.zaur.domain.storage.QuranStorage
import com.zaur.domain.apiV4.use_case.QuranAudioUseCaseV4
import com.zaur.domain.apiV4.use_case.QuranTafsirUseCaseV4
import com.zaur.domain.apiV4.use_case.QuranTajweedUseCaseV4
import com.zaur.domain.apiV4.use_case.QuranTextUseCaseV4
import com.zaur.domain.apiV4.use_case.QuranTranslationUseCaseV4

interface ProvideQuranStorage {
    fun provideQuranStorage(): QuranStorage
}

interface ProvideQuranAudioUseCaseAqc {
    fun provideQuranAudioUseCaseAqc(): QuranAudioUseCaseAqc
}

interface ProvideQuranTextUseCaseAqc {
    fun provideQuranTextUseCaseAqc(): QuranTextUseCaseAqc
}

interface ProvideQuranTranslationUseCaseAqc {
    fun provideQuranTranslationUseCaseAqc(): QuranTranslationUseCaseAqc
}

interface ProvideQuranAudioUseCaseV4 {
    fun provideQuranAudioUseCaseV4(): QuranAudioUseCaseV4
}

interface ProvideQuranTafsirUseCase {
    fun provideQuranTafsirUseCaseV4(): QuranTafsirUseCaseV4
}

interface ProvideQuranTajweedUseCase {
    fun provideQuranTajweedUseCaseV4(): QuranTajweedUseCaseV4
}

interface ProvideQuranTextUseCase {
    fun provideQuranTextUseCaseV4(): QuranTextUseCaseV4
}

interface ProvideQuranTranslationUseCase {
    fun provideQuranTranslationUseCaseV4(): QuranTranslationUseCaseV4
}

interface ProvideQuranApiAqc {
    fun provideQuranApiAqc(): QuranApiAqc
}

interface ProvideQuranApiV4 {
    fun provideQuranApiV4(): QuranApiV4
}

interface ProvideQuranAudioRepositoryAqc {
    fun provideQuranAudioRepositoryAqc(): QuranAudioRepositoryAqc
}

interface ProvideQuranTextRepositoryAqc {
    fun provideQuranTextRepositoryAqc(): QuranTextRepositoryAqc
}

interface ProvideQuranTranslationRepositoryAqc {
    fun provideQuranTranslationRepositoryAqc(): QuranTranslationRepositoryAqc
}

interface ProvideQuranAudioRepositoryV4 {
    fun provideQuranAudioRepositoryV4(): QuranAudioRepositoryV4
}

interface ProvideQuranTafsirRepositoryV4 {
    fun provideQuranTafsirRepositoryV4(): QuranTafsirRepositoryV4
}

interface ProvideQuranTajweedRepositoryV4 {
    fun provideQuranTajweedRepositoryV4(): QuranTajweedRepositoryV4
}

interface ProvideQuranTranslationRepositoryV4 {
    fun provideQuranTranslationRepositoryV4(): QuranTranslationRepositoryV4
}

interface ProvideQuranTextRepositoryV4 {
    fun provideQuranTextRepositoryV4(): QuranTextRepositoryV4
}