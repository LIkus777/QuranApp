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
    fun saveLastRead(chapterNumber: Int, ayahNumber: Int)
    fun getLastRead(): Pair<Int, Int>
    fun setSurahScreenOpened()
    fun isSurahScreenOpened(): Boolean
}