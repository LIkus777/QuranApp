package com.zaur.domain.al_quran_cloud.use_case

import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChaptersAqc
import com.zaur.domain.al_quran_cloud.models.chapter.ChaptersAqc
import com.zaur.domain.al_quran_cloud.repository.QuranTextRepositoryAqc
import com.zaur.domain.storage.QuranStorage

interface QuranTextUseCaseAqc {

    suspend fun fetchAllChapters(): ChaptersAqc
    suspend fun fetchArabicChapter(chapterNumber: Int): ArabicChaptersAqc
    fun getLastReadPosition(): Pair<Int, Int>
    fun markSurahScreenAsOpened()
    fun isSurahScreenPreviouslyOpened(): Boolean
    fun saveLastReadPosition(chapterNumber: Int, ayahNumber: Int)

    class Base(
        private val quranTextRepositoryAqc: QuranTextRepositoryAqc,
        private val quranStorage: QuranStorage
    ) : QuranTextUseCaseAqc {
        override suspend fun fetchAllChapters(): ChaptersAqc =
            quranTextRepositoryAqc.getAllChapters()

        override suspend fun fetchArabicChapter(chapterNumber: Int): ArabicChaptersAqc =
            quranTextRepositoryAqc.getArabicChapter(chapterNumber)

        override fun saveLastReadPosition(chapterNumber: Int, ayahNumber: Int) {
            quranStorage.saveLastRead(chapterNumber, ayahNumber)
        }

        override fun getLastReadPosition(): Pair<Int, Int> = quranStorage.getLastRead()

        override fun markSurahScreenAsOpened() {
            quranStorage.setSurahScreenOpened()
        }

        override fun isSurahScreenPreviouslyOpened(): Boolean = quranStorage.isSurahScreenOpened()
    }
}