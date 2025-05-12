package com.zaur.domain.storage.theme

/**
* @author Zaur
* @since 2025-05-12
*/

class ThemeUseCase(private val themeStorage: ThemeStorage) {

    fun getIsDarkTheme(): Boolean {
        return themeStorage.getIsDarkTheme()
    }

    fun saveTheme(isDark: Boolean) {
        themeStorage.saveTheme(isDark)
    }
}