package com.zaur.data.preferences

import android.content.Context
import com.zaur.core.SharedPrefKeys
import com.zaur.domain.storage.QuranStorage

class QuranPreferences(context: Context) : QuranStorage {
    private val sharedPreferences =
    context.getSharedPreferences(SharedPrefKeys.QURAN_PREFS, Context.MODE_PRIVATE)

    override fun saveLastRead(chapterNumber: Int, ayahNumber: Int) {
        sharedPreferences.edit()
            .putInt(SharedPrefKeys.LAST_CHAPTER, chapterNumber)
            .putInt(SharedPrefKeys.LAST_AYAH, ayahNumber)
            .apply()
    }

    override fun getLastRead(): Pair<Int, Int> {
        val chapter = sharedPreferences.getInt(SharedPrefKeys.LAST_CHAPTER, 1)
        val ayah = sharedPreferences.getInt(SharedPrefKeys.LAST_AYAH, 1)
        return Pair(chapter, ayah)
    }

    override fun setSurahScreenOpened() {
        sharedPreferences.edit().putBoolean(SharedPrefKeys.SURAH_OPENED, true).apply()
    }

    override fun isSurahScreenOpened(): Boolean {
        return sharedPreferences.getBoolean(SharedPrefKeys.SURAH_OPENED, false)
    }

}