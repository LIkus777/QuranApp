package com.zaur.domain.al_quran_cloud.use_case

import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChapter
import com.zaur.domain.al_quran_cloud.models.chapter.ChapterAqc
import com.zaur.domain.al_quran_cloud.repository.AssetsQuranLoader
import com.zaur.domain.al_quran_cloud.repository.OfflineRepository
import com.zaur.domain.al_quran_cloud.repository.QuranTextRepository
import com.zaur.domain.storage.QuranStorage

/**
 * @author Zaur
 * @since 2025-05-12
 */

interface QuranTextUseCase {

    fun onNextPage(currentPage: Int)
    fun onPreviousPage(currentPage: Int)

    suspend fun getAllChapters(): List<ChapterAqc.Base>
    suspend fun getArabicChapter(chapterNumber: Int): ArabicChapter.Base

    fun getFontSizeArabic(): Float
    fun getFontSizeRussian(): Float
    fun saveFontSizeArabic(size: Float)
    fun saveFontSizeRussian(size: Float)

    fun getLastReadSurah(): Int
    fun setLastReadSurah(surahNumber: Int)
    fun getLastReadAyahPosition(chapterNumber: Int): Int
    fun saveLastReadAyahPosition(chapterNumber: Int, ayahNumber: Int)

    fun markSurahScreenAsOpened()
    fun isSurahScreenPreviouslyOpened(): Boolean

    class Base(
        private val assetsQuranLoader: AssetsQuranLoader,
        private val quranStorage: QuranStorage,
        private val quranTextRepositoryLocal: QuranTextRepository.Local,
    ) : QuranTextUseCase {
        override fun onNextPage(currentPage: Int) {
            assetsQuranLoader.onNextPage(currentPage)
        }

        override fun onPreviousPage(currentPage: Int) {
            assetsQuranLoader.onPreviousPage(currentPage)
        }

        override suspend fun getAllChapters(): List<ChapterAqc.Base> {
            return quranTextRepositoryLocal.getAllChaptersLocal()
        }

        override suspend fun getArabicChapter(chapterNumber: Int): ArabicChapter.Base {
            return quranTextRepositoryLocal.getArabicChapterLocal(chapterNumber)
        }

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

        override fun setLastReadSurah(surahNumber: Int) = quranStorage.setLastReadSurah(surahNumber)

        override fun getLastReadSurah(): Int = quranStorage.getLastReadSurah()

        override fun getLastReadAyahPosition(chapterNumber: Int): Int =
            quranStorage.getLastReadAyahPosition(chapterNumber)

        override fun saveLastReadAyahPosition(chapterNumber: Int, ayahNumber: Int) =
            quranStorage.saveLastReadAyahPosition(chapterNumber, ayahNumber)

        override fun markSurahScreenAsOpened() {
            quranStorage.setSurahScreenOpened()
        }

        override fun isSurahScreenPreviouslyOpened(): Boolean = quranStorage.isSurahScreenOpened()
    }
}