package com.zaur.domain.apiV4.use_case

import com.zaur.domain.apiV4.models.chapter.ChapterV4
import com.zaur.domain.apiV4.models.juz.JuzV4
import com.zaur.domain.apiV4.repository.QuranTextRepositoryV4
import com.zaur.domain.storage.QuranStorage

class QuranTextUseCaseV4(
    private val quranTextRepositoryV4: QuranTextRepositoryV4, private val quranStorage: QuranStorage
) {
    suspend fun getAllChapters(language: String): List<ChapterV4> =
        quranTextRepositoryV4.getAllChapters(language)

    suspend fun getChapter(chapterNumber: Int, language: String): ChapterV4 =
        quranTextRepositoryV4.getChapter(chapterNumber, language)

    suspend fun getAllJuzs(): List<JuzV4> = quranTextRepositoryV4.getAllJuzs()

    fun saveLastRead(chapterNumber: Int, ayahNumber: Int) {
        quranStorage.saveLastRead(chapterNumber, ayahNumber)
    }

    fun getLastRead(): Pair<Int, Int> = quranStorage.getLastRead()

    fun setSurahScreenOpened() {
        quranStorage.setSurahScreenOpened()
    }

    fun isSurahScreenOpened(): Boolean = quranStorage.isSurahScreenOpened()
}