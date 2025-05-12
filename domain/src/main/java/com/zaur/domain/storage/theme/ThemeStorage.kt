package com.zaur.domain.storage.theme

import kotlinx.coroutines.flow.Flow

/**
* @author Zaur
* @since 2025-05-12
*/

interface ThemeStorage {
    fun getIsDarkTheme(): Boolean
    fun saveTheme(isDark: Boolean)
}