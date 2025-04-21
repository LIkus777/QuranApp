package com.zaur.domain.al_quran_cloud.use_case

import com.zaur.domain.al_quran_cloud.repository.MainRepository

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
        private val mainRepositoryLoad: MainRepository.Load,
        private val mainRepositorySave: MainRepository.Save,
    ) : MainUseCase {

        override suspend fun loadChapters() {
            val result = mainRepositoryLoad.loadChapters()
            mainRepositorySave.saveChapters(result)
        }

        override suspend fun loadChaptersArabic(chaptersNumbers: IntRange) {
            val result = mainRepositoryLoad.loadChaptersArabic(chaptersNumbers)
            mainRepositorySave.saveChaptersArabic(result)
        }

        override suspend fun loadChaptersAudio(chaptersNumbers: IntRange, reciter: String) {
            val result = mainRepositoryLoad.loadChaptersAudio(chaptersNumbers, reciter)
            mainRepositorySave.saveChaptersAudio(result)
        }

        override suspend fun loadChaptersTranslate(chaptersNumbers: IntRange, translator: String) {
            val result = mainRepositoryLoad.loadChaptersTranslate(chaptersNumbers, translator)
            mainRepositorySave.saveChaptersTranslate(result)
        }
    }

}