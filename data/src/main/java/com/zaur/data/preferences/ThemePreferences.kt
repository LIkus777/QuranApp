package com.zaur.data.preferences

import android.content.Context
import com.zaur.core.ThemeSharedPrefKeys
import com.zaur.domain.storage.theme.ThemeStorage

class ThemePreferences(private val context: Context) : ThemeStorage {
    private val sharedPreferences =
        context.getSharedPreferences(ThemeSharedPrefKeys.THEME_PREFS, Context.MODE_PRIVATE)

    // Публичный метод для получения значения
    override fun getIsDarkTheme(): Boolean =
        sharedPreferences.getBoolean(ThemeSharedPrefKeys.IS_DARK_THEME, false)

    override fun saveTheme(isDark: Boolean) {
        sharedPreferences.edit().putBoolean(ThemeSharedPrefKeys.IS_DARK_THEME, isDark).apply()
    }
}