package com.zaur.di.provides

import com.zaur.di.module.DataModule
import com.zaur.di.module.MainScreenModule
import com.zaur.di.module.MapperModule
import com.zaur.di.module.SurahChooseModule
import com.zaur.di.module.SurahDetailModule

/**
* @author Zaur
* @since 2025-05-12
*/

interface ProvidesModules : ProvideMainScreenModule, ProvideSurahChooseModule,
    ProvideSurahDetailModule, ProvideDatabaseModule, ProvideMapperModule

interface ProvideMainScreenModule {
    fun provideMainScreenModule(): MainScreenModule
}

interface ProvideSurahChooseModule {
    fun provideSurahChooseModule(): SurahChooseModule
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