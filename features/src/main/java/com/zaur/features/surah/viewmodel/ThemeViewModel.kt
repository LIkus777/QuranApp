package com.zaur.features.surah.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.zaur.core.BaseViewModel
import com.zaur.domain.storage.theme.ThemeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ThemeViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val themeUseCase: ThemeUseCase
) : BaseViewModel(savedStateHandle) {
    fun getIsDarkTheme(): Flow<Boolean> {
        return themeUseCase.getIsDarkTheme()
    }

    fun saveTheme(isDark: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            themeUseCase.saveTheme(isDark)
        }
    }
}