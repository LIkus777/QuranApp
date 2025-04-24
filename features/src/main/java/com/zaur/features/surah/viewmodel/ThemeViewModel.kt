package com.zaur.features.surah.viewmodel

import com.zaur.core.BaseViewModel
import com.zaur.domain.storage.theme.ThemeUseCase
import com.zaur.features.surah.observables.ThemeObservable
import com.zaur.features.surah.ui_state.theme.ThemeUIState
import kotlinx.coroutines.flow.StateFlow

interface ThemeViewModel : ThemeObservable.Read {

    fun saveTheme(isDark: Boolean)
    fun getIsDarkTheme(): Boolean

    class Base(
        private val observable: ThemeObservable.Mutable = ThemeObservable.Base(ThemeUIState()),
        private val themeUseCase: ThemeUseCase,
    ) : BaseViewModel(), ThemeViewModel {

        override fun themeState(): StateFlow<ThemeUIState> = observable.themeState() //todo начать использовать

        override fun getIsDarkTheme(): Boolean {
            return themeUseCase.getIsDarkTheme()
        }

        override fun saveTheme(isDark: Boolean) {
            themeUseCase.saveTheme(isDark)
        }
    }
}