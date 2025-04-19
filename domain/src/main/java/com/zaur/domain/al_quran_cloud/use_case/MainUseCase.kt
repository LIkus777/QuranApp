package com.zaur.domain.al_quran_cloud.use_case

import com.zaur.domain.al_quran_cloud.repository.MainRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

    /*suspend fun saveChapters(chaptersAqc: List<ChapterAqc>)
    suspend fun saveChaptersArabic(chaptersArabic: List<ArabicChapter>)
    suspend fun saveChaptersAudio(chaptersAudio: List<ChapterAudioFile>)
    suspend fun saveChaptersTranslate(chaptersTranslate: List<TranslationAqc>)*/

    class Base(
        private val mainRepositoryLoad: MainRepository.Load,
        private val mainRepositorySave: MainRepository.Save,
    ) : MainUseCase {

        override suspend fun loadChapters() {
            CoroutineScope(Dispatchers.IO).launch {
                val result = mainRepositoryLoad.loadChapters()
                mainRepositorySave.saveChapters(result)
            }
        }

        override suspend fun loadChaptersArabic(chaptersNumbers: IntRange) {
            CoroutineScope(Dispatchers.IO).launch {
                val result = mainRepositoryLoad.loadChaptersArabic(chaptersNumbers)
                mainRepositorySave.saveChaptersArabic(result)
            }
        }

        override suspend fun loadChaptersAudio(
            chaptersNumbers: IntRange,
            reciter: String,
        ) {
            CoroutineScope(Dispatchers.IO).launch {
                val result = mainRepositoryLoad.loadChaptersAudio(chaptersNumbers, reciter)
                mainRepositorySave.saveChaptersAudio(result)
            }
        }

        override suspend fun loadChaptersTranslate(
            chaptersNumbers: IntRange,
            translator: String,
        ) {
            CoroutineScope(Dispatchers.IO).launch {
                val result = mainRepositoryLoad.loadChaptersTranslate(chaptersNumbers, translator)
                mainRepositorySave.saveChaptersTranslate(result)
            }
        }

        /*override suspend fun saveChapters(chaptersAqc: List<ChapterAqc>) {
            mainRepositorySave.saveChapters(chaptersAqc)
        }

        override suspend fun saveChaptersArabic(chaptersArabic: List<ArabicChapter>) {
            mainRepositorySave.saveChaptersArabic(chaptersArabic)
        }

        override suspend fun saveChaptersAudio(chaptersAudio: List<ChapterAudioFile>) {
            mainRepositorySave.saveChaptersAudio(chaptersAudio)
        }

        override suspend fun saveChaptersTranslate(chaptersTranslate: List<TranslationAqc>) {
            mainRepositorySave.saveChaptersTranslate(chaptersTranslate)
        }*/
    }

}