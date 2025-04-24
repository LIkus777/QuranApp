package com.zaur.domain.storage.theme

import kotlinx.coroutines.flow.Flow

interface ThemeStorage {
    fun getIsDarkTheme(): Boolean
    fun saveTheme(isDark: Boolean)
}