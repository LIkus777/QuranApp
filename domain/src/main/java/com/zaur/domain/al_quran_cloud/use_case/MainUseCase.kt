package com.zaur.domain.al_quran_cloud.use_case

import com.zaur.domain.al_quran_cloud.repository.MainRepository

/**
 * @author Zaur
 * @since 2025-05-12
 */

interface MainUseCase {

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
        private val mainRepositoryCloud: MainRepository.Cloud,
        private val mainRepositoryLocal: MainRepository.Local,
    ) : MainUseCase {

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