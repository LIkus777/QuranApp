package com.zaur.features.surah.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.zaur.core.BaseViewModel
import com.zaur.domain.storage.theme.ThemeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

interface ThemeViewModel {

    fun saveTheme(isDark: Boolean)
    fun getIsDarkTheme(): Flow<Boolean>

    class Base(
        private val savedStateHandle: SavedStateHandle,
        private val themeUseCase: ThemeUseCase
    ) : BaseViewModel(savedStateHandle), ThemeViewModel {
        override fun getIsDarkTheme(): Flow<Boolean> {
            return themeUseCase.getIsDarkTheme()
        }

        override fun saveTheme(isDark: Boolean) {
            viewModelScope.launch(Dispatchers.IO) {
                themeUseCase.saveTheme(isDark)
            }
        }
    }
}