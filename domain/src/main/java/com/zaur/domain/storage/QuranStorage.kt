package com.zaur.domain.storage

/**
* @author Zaur
* @since 2025-05-12
*/

interface QuranStorage {
    fun getFontSizeArabic(): Float
    fun getFontSizeRussian(): Float
    fun saveFontSizeArabic(size: Float)
    fun saveFontSizeRussian(size: Float)
    fun getLastPlayedSurah(): Int
    fun setLastPlayedSurah(surahNumber: Int)
    fun setLastReadSurah(surahNumber: Int)
    fun getLastReadSurah(): Int
    fun getLastReadAyahPosition(chapterNumber: Int): Int
    fun saveLastReadAyahPosition(chapterNumber: Int, ayahNumber: Int)
    fun getLastReadPagePosition(): Int
    fun saveLastReadPagePosition(pageNumber: Int)
    fun setSurahScreenOpened()
    fun isSurahScreenOpened(): Boolean
}