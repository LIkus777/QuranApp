package com.zaur.domain.al_quran_cloud.use_case

import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChapter
import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChaptersAqc
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudioFile
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudiosFileAqc
import com.zaur.domain.al_quran_cloud.models.chapter.ChapterAqc
import com.zaur.domain.al_quran_cloud.models.chapter.ChaptersAqc
import com.zaur.domain.al_quran_cloud.models.translate.TranslationAqc
import com.zaur.domain.al_quran_cloud.models.translate.TranslationsChapterAqc
import com.zaur.domain.al_quran_cloud.repository.MainRepository

interface MainUseCase {

    suspend fun loadChapters(): ChaptersAqc
    suspend fun loadChaptersAudio(): List<ChapterAudiosFileAqc>
    suspend fun loadChaptersArabic(): List<ArabicChaptersAqc>
    suspend fun loadChaptersTranslate(): List<TranslationsChapterAqc>

    suspend fun saveChapters(chaptersAqc: List<ChapterAqc>)
    suspend fun saveChaptersAudio(chaptersAudio: List<ChapterAudioFile>)
    suspend fun saveChaptersArabic(chaptersArabic: List<ArabicChapter>)
    suspend fun saveChaptersTranslate(chaptersTranslate: List<TranslationAqc>)

    class Base(
        private val mainRepositoryLoad: MainRepository.Load,
        private val mainRepositorySave: MainRepository.Save,
    ) : MainUseCase {
        override suspend fun loadChapters(): ChaptersAqc {
            return mainRepositoryLoad.loadChapters()
        }

        override suspend fun loadChaptersAudio(): List<ChapterAudiosFileAqc> {
            return mainRepositoryLoad.loadChaptersAudio()
        }

        override suspend fun loadChaptersArabic(): List<ArabicChaptersAqc> {
            return mainRepositoryLoad.loadChaptersArabic()
        }

        override suspend fun loadChaptersTranslate(): List<TranslationsChapterAqc> {
            return mainRepositoryLoad.loadChaptersTranslate()
        }

        override suspend fun saveChapters(chaptersAqc: List<ChapterAqc>) {
            mainRepositorySave.saveChapters(chaptersAqc)
        }

        override suspend fun saveChaptersAudio(chaptersAudio: List<ChapterAudioFile>) {
            mainRepositorySave.saveChaptersAudio(chaptersAudio)
        }

        override suspend fun saveChaptersArabic(chaptersArabic: List<ArabicChapter>) {
            mainRepositorySave.saveChaptersArabic(chaptersArabic)
        }

        override suspend fun saveChaptersTranslate(chaptersTranslate: List<TranslationAqc>) {
            mainRepositorySave.saveChaptersTranslate(chaptersTranslate)
        }
    }

}