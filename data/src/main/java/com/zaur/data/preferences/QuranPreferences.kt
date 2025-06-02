package com.zaur.data.preferences

import android.content.Context
import android.util.Log
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

    override fun getAudioSurahName(): String =
        sharedPreferences.getString(QuranSharedPrefKeys.AUDIO_SURAH_NAME, "").toString()

    override fun setAudioSurahName(surahName: String) = sharedPreferences.edit().putString(
        QuranSharedPrefKeys.AUDIO_SURAH_NAME, surahName
    ).apply()

    override fun getLastPlayedAyah(): Int =
        sharedPreferences.getInt(QuranSharedPrefKeys.LAST_PLAYED_AYAH, 0)

    override fun setLastPlayedAyah(ayahNumber: Int) = sharedPreferences.edit().putInt(
        QuranSharedPrefKeys.LAST_PLAYED_AYAH, ayahNumber
    ).apply()

    override fun getLastPlayedSurah(): Int =
        sharedPreferences.getInt(QuranSharedPrefKeys.LAST_PLAYED_SURAH, 0)

    override fun setLastPlayedSurah(surahNumber: Int) = sharedPreferences.edit().putInt(
        QuranSharedPrefKeys.LAST_PLAYED_SURAH, surahNumber
    ).apply()

    override fun setLastReadSurah(surahNumber: Int) =
        sharedPreferences.edit().putInt(QuranSharedPrefKeys.LAST_READ_SURAH, surahNumber).apply()

    override fun getLastReadSurah(): Int =
        sharedPreferences.getInt(QuranSharedPrefKeys.LAST_READ_SURAH, 0)

    override fun getLastReadAyahPosition(chapterNumber: Int): Int {
        val key = "${QuranSharedPrefKeys.LAST_READ_AYAH}_$chapterNumber"
        Log.i("TAG", "getLastReadAyahPosition: chapterNumber $chapterNumber ayahNumber ${sharedPreferences.getInt(key, 1)}")
        return sharedPreferences.getInt(key, 1)
    }

    override fun saveLastReadAyahPosition(chapterNumber: Int, ayahNumber: Int) {
        val key = "${QuranSharedPrefKeys.LAST_READ_AYAH}_$chapterNumber"
        Log.i("TAG", "saveLastReadAyahPosition: chapterNumber $chapterNumber ayahNumber $ayahNumber")
        sharedPreferences.edit().putInt(key, ayahNumber).apply()
    }

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