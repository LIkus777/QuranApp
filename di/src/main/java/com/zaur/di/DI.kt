package com.zaur.di

import android.content.Context
import com.zaur.di.module.AudioModule
import com.zaur.di.module.DataModule
import com.zaur.di.module.MainScreenModule
import com.zaur.di.module.MapperModule
import com.zaur.di.module.SurahChooseModule
import com.zaur.di.module.SurahDetailModule
import com.zaur.di.module.SurahDetailStateManagerModule
import com.zaur.di.provides.ProvidesModules

/**
 * @author Zaur
 * @since 2025-05-12
 */


interface DI : ProvidesModules {

    class Base(private val context: Context) : DI {

        private val surahDetailStateManagerModule by lazy { SurahDetailStateManagerModule.Base() }
        private val audioModule by lazy {
            AudioModule.Base(
                context,
                provideDataModule(),
                provideSurahDetailStateManagerModule()
            )
        }

        override fun provideMainScreenModule(): MainScreenModule =
            MainScreenModule.Base(provideDataModule(), provideMapperModule())

        override fun provideSurahChooseModule(): SurahChooseModule =
            SurahChooseModule.Base(provideDataModule(), provideMapperModule())

        override fun provideSurahDetailModule(): SurahDetailModule = SurahDetailModule.Base(
            provideDataModule(),
            provideMapperModule(),
            provideAudioModule(),
            provideSurahDetailStateManagerModule()
        )

        override fun provideDataModule(): DataModule = DataModule.Base(context)

        override fun provideMapperModule(): MapperModule = MapperModule.Base()

        override fun provideSurahDetailStateManagerModule(): SurahDetailStateManagerModule =
            surahDetailStateManagerModule

        override fun provideAudioModule(): AudioModule = audioModule

    }
}
