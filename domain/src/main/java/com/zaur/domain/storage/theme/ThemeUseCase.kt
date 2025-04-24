package com.zaur.domain.storage.theme

class ThemeUseCase(private val themeStorage: ThemeStorage) {

    fun getIsDarkTheme(): Boolean {
        return themeStorage.getIsDarkTheme()
    }

    fun saveTheme(isDark: Boolean) {
        themeStorage.saveTheme(isDark)
    }
}