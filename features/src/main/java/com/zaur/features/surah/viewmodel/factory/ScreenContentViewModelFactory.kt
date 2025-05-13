package com.zaur.features.surah.viewmodel.factory

import com.zaur.features.surah.manager.SurahDetailStateManager
import com.zaur.features.surah.observables.AnimatedMenuObservable
import com.zaur.features.surah.observables.SurahModeObservable
import com.zaur.features.surah.viewmodel.ScreenContentViewModel


/**
 * @author Zaur
 * @since 13.05.2025
 */

interface ScreenContentViewModelFactory {

    fun create(): ScreenContentViewModel

    class Base(
        private val surahDetailStateManager: SurahDetailStateManager,
        private val surahModeObservable: SurahModeObservable.Mutable = SurahModeObservable.Base(),
        private val animatedMenuObservable: AnimatedMenuObservable.Mutable = AnimatedMenuObservable.Base(),
    ) : ScreenContentViewModelFactory {
        override fun create(): ScreenContentViewModel = ScreenContentViewModel.Base(
            surahDetailStateManager,
            surahModeObservable,
            animatedMenuObservable
        )
    }

}