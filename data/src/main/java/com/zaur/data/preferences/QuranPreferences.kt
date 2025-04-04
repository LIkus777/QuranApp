package com.zaur.data.preferences

import android.content.Context
import com.zaur.core.QuranSharedPrefKeys
import com.zaur.domain.storage.QuranStorage

class QuranPreferences(context: Context) : QuranStorage {
    private val sharedPreferences =
        context.getSharedPreferences(QuranSharedPrefKeys.QURAN_PREFS, Context.MODE_PRIVATE)

    override fun saveLastRead(chapterNumber: Int, ayahNumber: Int) {
        sharedPreferences.edit().putInt(QuranSharedPrefKeys.LAST_CHAPTER, chapterNumber)
            .putInt(QuranSharedPrefKeys.LAST_AYAH, ayahNumber).apply()
    }

    override fun getLastRead(): Pair<Int, Int> {
        val chapter = sharedPreferences.getInt(QuranSharedPrefKeys.LAST_CHAPTER, 1)
        val ayah = sharedPreferences.getInt(QuranSharedPrefKeys.LAST_AYAH, 1)
        return Pair(chapter, ayah)
    }

    override fun setSurahScreenOpened() {
        sharedPreferences.edit().putBoolean(QuranSharedPrefKeys.SURAH_OPENED, true).apply()
    }

    override fun isSurahScreenOpened(): Boolean {
        return sharedPreferences.getBoolean(QuranSharedPrefKeys.SURAH_OPENED, false)
    }

}