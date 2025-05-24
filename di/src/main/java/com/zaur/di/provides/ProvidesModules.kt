package com.zaur.di.provides

import com.zaur.di.module.AudioModule
import com.zaur.di.module.DataModule
import com.zaur.di.module.MainScreenModule
import com.zaur.di.module.MapperModule
import com.zaur.di.module.SurahChooseModule
import com.zaur.di.module.SurahDetailModule
import com.zaur.di.module.SurahDetailStateManagerModule

/**
* @author Zaur
* @since 2025-05-12
*/

interface ProvidesModules : ProvideMainScreenModule, ProvideSurahChooseModule, ProvideSurahDetailStateManagerModule, ProvideAudioModule,
    ProvideSurahDetailModule, ProvideDatabaseModule, ProvideMapperModule

interface ProvideMainScreenModule {
    fun provideMainScreenModule(): MainScreenModule
}

interface ProvideSurahChooseModule {
    fun provideSurahChooseModule(): SurahChooseModule
}

interface ProvideSurahDetailStateManagerModule {
    fun provideSurahDetailStateManagerModule(): SurahDetailStateManagerModule
}

interface ProvideAudioModule {
    fun provideAudioModule(): AudioModule
}

interface ProvideSurahDetailModule {
    fun provideSurahDetailModule(): SurahDetailModule
}

interface ProvideDatabaseModule {
    fun provideDataModule(): DataModule
}

interface ProvideMapperModule {
    fun provideMapperModule(): MapperModule
}