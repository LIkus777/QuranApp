package com.zaur.domain.al_quran_cloud.use_case

import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChapter
import com.zaur.domain.al_quran_cloud.models.chapter.ChapterAqc
import com.zaur.domain.al_quran_cloud.repository.QuranTextRepositoryAqc
import com.zaur.domain.storage.QuranStorage

interface QuranTextUseCaseAqc {

    suspend fun fetchAllChapters(): List<ChapterAqc>
    suspend fun fetchArabicChapter(chapterNumber: Int): ArabicChapter

    fun getFontSizeArabic(): Float
    fun getFontSizeRussian(): Float
    fun saveFontSizeArabic(size: Float)
    fun saveFontSizeRussian(size: Float)

    fun getLastReadPosition(): Pair<Int, Int>
    fun markSurahScreenAsOpened()
    fun isSurahScreenPreviouslyOpened(): Boolean
    fun saveLastReadPosition(chapterNumber: Int, ayahNumber: Int)

    class Base(
        private val quranTextRepositoryAqc: QuranTextRepositoryAqc,
        private val quranStorage: QuranStorage,
    ) : QuranTextUseCaseAqc {
        override suspend fun fetchAllChapters(): List<ChapterAqc> =
            quranTextRepositoryAqc.getAllChapters()

        override suspend fun fetchArabicChapter(chapterNumber: Int): ArabicChapter =
            quranTextRepositoryAqc.getArabicChapter(chapterNumber)

        override fun getFontSizeArabic(): Float {
            val result = quranStorage.getFontSizeArabic()
            return if (result != 0.0f) result
            else 24.0f
        }

        override fun getFontSizeRussian(): Float {
            val result = quranStorage.getFontSizeRussian()
            return if (result != 0.0f) result
            else 24.0f
        }

        override fun saveFontSizeArabic(size: Float) {
            quranStorage.saveFontSizeArabic(size)
        }

        override fun saveFontSizeRussian(size: Float) {
            quranStorage.saveFontSizeRussian(size)
        }

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