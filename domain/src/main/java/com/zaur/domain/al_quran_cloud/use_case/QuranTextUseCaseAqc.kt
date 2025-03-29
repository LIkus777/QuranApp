package com.zaur.domain.al_quran_cloud.use_case

import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChaptersAqc
import com.zaur.domain.al_quran_cloud.models.chapter.ChaptersAqc
import com.zaur.domain.al_quran_cloud.repository.QuranTextRepositoryAqc
import com.zaur.domain.storage.QuranStorage

class QuranTextUseCaseAqc(
    private val quranTextRepositoryAqc: QuranTextRepositoryAqc,
    private val quranStorage: QuranStorage
) {
    suspend fun getAllChapters(): ChaptersAqc =
        quranTextRepositoryAqc.getAllChapters()

    suspend fun getArabicChapter(chapterNumber: Int): ArabicChaptersAqc =
        quranTextRepositoryAqc.getArabicChapter(chapterNumber)

    fun saveLastRead(chapterNumber: Int, ayahNumber: Int) {
        quranStorage.saveLastRead(chapterNumber, ayahNumber)
    }

    fun getLastRead(): Pair<Int, Int> = quranStorage.getLastRead()

    fun setSurahScreenOpened() {
        quranStorage.setSurahScreenOpened()
    }

    fun isSurahScreenOpened(): Boolean = quranStorage.isSurahScreenOpened()
}