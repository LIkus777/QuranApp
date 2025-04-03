package com.zaur.features.surah.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.zaur.core.BaseViewModel
import com.zaur.domain.storage.theme.ThemeUseCase
import kotlinx.coroutines.flow.Flow

class ThemeViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val themeUseCase: ThemeUseCase
) : BaseViewModel(savedStateHandle) {
    fun getIsDarkTheme(): Flow<Boolean> {
        return themeUseCase.getIsDarkTheme()
    }

    suspend fun saveTheme(isDark: Boolean) {
        themeUseCase.saveTheme(isDark)
    }
}