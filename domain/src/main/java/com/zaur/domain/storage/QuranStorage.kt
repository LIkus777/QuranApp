package com.zaur.domain.storage

interface QuranStorage {
    fun saveLastRead(chapterNumber: Int, ayahNumber: Int)
    fun getLastRead(): Pair<Int, Int>
    fun setSurahScreenOpened()
    fun isSurahScreenOpened(): Boolean
}