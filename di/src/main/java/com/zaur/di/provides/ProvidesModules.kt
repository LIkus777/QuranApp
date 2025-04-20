package com.zaur.di.provides

import com.zaur.di.module.DataModule
import com.zaur.di.module.MainScreenModule
import com.zaur.di.module.SurahChooseModule
import com.zaur.di.module.SurahDetailModule

interface ProvidesModules : ProvideMainScreenModule, ProvideSurahChooseModule,
    ProvideSurahDetailModule, ProvideDatabaseModule

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