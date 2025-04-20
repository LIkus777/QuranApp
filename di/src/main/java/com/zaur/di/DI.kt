package com.zaur.di

import android.content.Context
import com.zaur.di.module.DataModule
import com.zaur.di.module.MainScreenModule
import com.zaur.di.module.SurahChooseModule
import com.zaur.di.module.SurahDetailModule
import com.zaur.di.provides.ProvidesModules


interface DI : ProvidesModules {

    class Base(private val context: Context) : DI {
        override fun provideMainScreenModule(): MainScreenModule =
            MainScreenModule.Base(provideDataModule())

        override fun provideSurahChooseModule(): SurahChooseModule =
            SurahChooseModule.Base(provideDataModule())

        override fun provideSurahDetailModule(): SurahDetailModule =
            SurahDetailModule.Base(context, provideDataModule())

        override fun provideDataModule(): DataModule = DataModule.Base(context)
    }
}
