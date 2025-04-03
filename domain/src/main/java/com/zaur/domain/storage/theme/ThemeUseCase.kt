package com.zaur.domain.storage.theme

import kotlinx.coroutines.flow.Flow

class ThemeUseCase(private val themeStorage: ThemeStorage) {

    fun getIsDarkTheme(): Flow<Boolean> {
        return themeStorage.getIsDarkTheme()
    }

    suspend fun saveTheme(isDark: Boolean) {
        themeStorage.saveTheme(isDark)
    }
}