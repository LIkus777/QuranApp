package com.zaur.di.module

import com.zaur.di.provides.ProvideSurahDetailStateManager
import com.zaur.features.surah.manager.SurahDetailStateManager


/**
 * @author Zaur
 * @since 24.05.2025
 */

interface SurahDetailStateManagerModule : ProvideSurahDetailStateManager {

    class Base : SurahDetailStateManagerModule {

        private val surahDetailStateManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            SurahDetailStateManager.Base()
        }

        override fun provideSurahDetailStateManager(): SurahDetailStateManager =
            surahDetailStateManager
    }

}