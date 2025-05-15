package com.zaur.data.preferences

import android.content.Context
import com.zaur.core.QuranSharedPrefKeys
import com.zaur.domain.storage.QuranStorage

/**
 * @author Zaur
 * @since 2025-05-12
 */

class QuranPreferences(context: Context) : QuranStorage {
    private val sharedPreferences =
        context.getSharedPreferences(QuranSharedPrefKeys.QURAN_PREFS, Context.MODE_PRIVATE)

    override fun getFontSizeArabic(): Float =
        sharedPreferences.getFloat(QuranSharedPrefKeys.ARABIC_FONT_SIZE, 0f)

    override fun getFontSizeRussian(): Float =
        sharedPreferences.getFloat(QuranSharedPrefKeys.RUSSIAN_FONT_SIZE, 0f)

    override fun saveFontSizeArabic(size: Float) =
        sharedPreferences.edit().putFloat(QuranSharedPrefKeys.ARABIC_FONT_SIZE, size).apply()

    override fun saveFontSizeRussian(size: Float) =
        sharedPreferences.edit().putFloat(QuranSharedPrefKeys.RUSSIAN_FONT_SIZE, size).apply()

    override fun getLastReadAyahPosition(): Pair<Int, Int> {
        val chapter = sharedPreferences.getInt(QuranSharedPrefKeys.LAST_READ_AYAH_CHAPTER, 1)
        val ayah = sharedPreferences.getInt(QuranSharedPrefKeys.LAST_READ_AYAH_NUMBER, 1)
        return Pair(chapter, ayah)
    }

    override fun saveLastReadAyahPosition(chapterNumber: Int, ayahNumber: Int) =
        sharedPreferences.edit().putInt(QuranSharedPrefKeys.LAST_READ_AYAH_CHAPTER, chapterNumber)
            .putInt(QuranSharedPrefKeys.LAST_READ_AYAH_NUMBER, ayahNumber).apply()

    override fun getLastReadPagePosition(): Int =
        sharedPreferences.getInt(QuranSharedPrefKeys.LAST_READ_PAGE_NUMBER, 1)

    override fun saveLastReadPagePosition(pageNumber: Int) =
        sharedPreferences.edit().putInt(QuranSharedPrefKeys.LAST_READ_PAGE_NUMBER, pageNumber)
            .apply()

    override fun setSurahScreenOpened() =
        sharedPreferences.edit().putBoolean(QuranSharedPrefKeys.SURAH_OPENED, true).apply()

    override fun isSurahScreenOpened(): Boolean =
        sharedPreferences.getBoolean(QuranSharedPrefKeys.SURAH_OPENED, false)
}