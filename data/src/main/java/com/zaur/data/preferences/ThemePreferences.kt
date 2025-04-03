package com.zaur.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.zaur.domain.storage.theme.ThemeStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "settings")

class ThemePreferences(private val context: Context) : ThemeStorage {

    private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")

    private val _isDarkTheme: Flow<Boolean> = context.dataStore.data
        .map { preferences -> preferences[DARK_MODE_KEY] == true }

    // Публичный метод для получения значения
    override fun getIsDarkTheme(): Flow<Boolean> = _isDarkTheme

    override suspend fun saveTheme(isDark: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DARK_MODE_KEY] = isDark
        }
    }
}