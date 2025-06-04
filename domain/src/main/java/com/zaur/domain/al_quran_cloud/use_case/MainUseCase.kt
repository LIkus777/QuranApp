package com.zaur.domain.al_quran_cloud.use_case

import com.zaur.domain.al_quran_cloud.repository.MainRepository
import com.zaur.domain.storage.QuranStorage

/**
 * @author Zaur
 * @since 2025-05-12
 */

interface MainUseCase {

    fun isChaptersLoaded(): Boolean
    fun markChaptersLoaded()
    fun isArabicsLoaded(): Boolean
    fun markArabicsLoaded()
    fun isTranslationsLoaded(): Boolean
    fun markTranslationsLoaded()

    suspend fun loadChapters()
    suspend fun loadChaptersArabic(chaptersNumbers: IntRange)
    suspend fun loadChaptersAudio(
        chaptersNumbers: IntRange,
        reciter: String,
    )

    suspend fun loadChaptersTranslate(
        chaptersNumbers: IntRange,
        translator: String,
    )

    class Base(
        private val quranStorage: QuranStorage,
        private val mainRepositoryCloud: MainRepository.Cloud,
        private val mainRepositoryLocal: MainRepository.Local,
    ) : MainUseCase {
        override fun isChaptersLoaded(): Boolean = quranStorage.isChaptersLoaded()

        override fun markChaptersLoaded() = quranStorage.markChaptersLoaded()

        override fun isArabicsLoaded(): Boolean = quranStorage.isArabicsLoaded()

        override fun markArabicsLoaded() = quranStorage.markArabicsLoaded()

        override fun isTranslationsLoaded(): Boolean = quranStorage.isTranslationsLoaded()

        override fun markTranslationsLoaded() = quranStorage.markTranslationsLoaded()

        override suspend fun loadChapters() {
            val result = mainRepositoryCloud.loadChapters()
            mainRepositoryLocal.saveChapters(result)
        }

        override suspend fun loadChaptersArabic(chaptersNumbers: IntRange) {
            val result = mainRepositoryCloud.loadChaptersArabic(chaptersNumbers)
            mainRepositoryLocal.saveChaptersArabic(result)
        }

        override suspend fun loadChaptersAudio(chaptersNumbers: IntRange, reciter: String) {
            val result = mainRepositoryCloud.loadChaptersAudio(chaptersNumbers, reciter)
            mainRepositoryLocal.saveChaptersAudio(result)
        }

        override suspend fun loadChaptersTranslate(chaptersNumbers: IntRange, translator: String) {
            val result = mainRepositoryCloud.loadChaptersTranslate(chaptersNumbers, translator)
            mainRepositoryLocal.saveChaptersTranslate(result)
        }
    }

}