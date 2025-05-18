package com.zaur.features.surah.viewmodel

import com.zaur.core.BaseViewModel
import com.zaur.domain.storage.theme.ThemeUseCase
import com.zaur.features.surah.observables.ThemeObservable
import com.zaur.presentation.ui.ui_state.theme.ThemeUIState
import kotlinx.coroutines.flow.StateFlow

/**
* @author Zaur
* @since 2025-05-12
*/

interface ThemeViewModel : ThemeObservable.Read {

    fun saveTheme(isDark: Boolean)

    class Base(
        private val observable: ThemeObservable.Mutable = ThemeObservable.Base(ThemeUIState()),
        private val themeUseCase: ThemeUseCase,
    ) : BaseViewModel(), ThemeViewModel {

        override fun themeState(): StateFlow<ThemeUIState> = observable.themeState() //todo начать использовать

        override fun saveTheme(isDark: Boolean) {
            observable.update(themeState().value.copy(isDarkTheme = isDark))
            themeUseCase.saveTheme(isDark)
        }
    }
}