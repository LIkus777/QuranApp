package com.zaur.domain.al_quran_cloud.use_case

import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChapter
import com.zaur.domain.al_quran_cloud.models.chapter.ChapterAqc
import com.zaur.domain.al_quran_cloud.repository.OfflineRepository
import com.zaur.domain.al_quran_cloud.repository.QuranTextRepositoryAqc
import com.zaur.domain.storage.QuranStorage

interface QuranTextUseCaseAqc {

    suspend fun getAllChapters(): List<ChapterAqc>
    suspend fun getArabicChapter(chapterNumber: Int): ArabicChapter

    fun getFontSizeArabic(): Float
    fun getFontSizeRussian(): Float
    fun saveFontSizeArabic(size: Float)
    fun saveFontSizeRussian(size: Float)

    fun getLastReadPosition(): Pair<Int, Int>
    fun markSurahScreenAsOpened()
    fun isSurahScreenPreviouslyOpened(): Boolean
    fun saveLastReadPosition(chapterNumber: Int, ayahNumber: Int)

    class Base(
        private val quranStorage: QuranStorage,
        private val offlineRepository: OfflineRepository,
        private val quranTextRepositoryAqcLocal: QuranTextRepositoryAqc.Local,
        private val quranTextRepositoryAqcCloud: QuranTextRepositoryAqc.Cloud,
    ) : QuranTextUseCaseAqc {

        override suspend fun getAllChapters(): List<ChapterAqc> {
            return if (offlineRepository.isOffline()) {
                quranTextRepositoryAqcLocal.getAllChaptersLocal()
            } else {
                quranTextRepositoryAqcCloud.getAllChaptersCloud()
            }
        }

        override suspend fun getArabicChapter(chapterNumber: Int): ArabicChapter {
            return if (offlineRepository.isOffline()) {
                quranTextRepositoryAqcLocal.getArabicChapterLocal(chapterNumber)
            } else {
                quranTextRepositoryAqcCloud.getArabicChapterCloud(chapterNumber)
            }
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