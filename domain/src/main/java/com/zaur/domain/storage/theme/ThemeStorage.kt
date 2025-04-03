package com.zaur.domain.storage.theme

import kotlinx.coroutines.flow.Flow

interface ThemeStorage {
    fun getIsDarkTheme(): Flow<Boolean>
    suspend fun saveTheme(isDark: Boolean)
}