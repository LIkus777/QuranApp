package com.zaur.domain.al_quran_cloud.use_case

import com.zaur.domain.al_quran_cloud.models.translate.TranslationAqc
import com.zaur.domain.al_quran_cloud.repository.OfflineRepository
import com.zaur.domain.al_quran_cloud.repository.QuranTranslationRepository

/**
* @author Zaur
* @since 2025-05-12
*/

interface QuranTranslationUseCase {

    suspend fun getTranslationForChapter(
        chapterNumber: Int,
        translator: String,
    ): TranslationAqc

    class Base(
        private val offlineRepository: OfflineRepository,
        private val quranTranslationRepositoryLocal: QuranTranslationRepository.Local,
        private val quranTranslationRepositoryCloud: QuranTranslationRepository.Cloud,
    ) : QuranTranslationUseCase {
        override suspend fun getTranslationForChapter(
            chapterNumber: Int,
            translator: String,
        ): TranslationAqc {
            return if (offlineRepository.isOffline()) {
                quranTranslationRepositoryLocal.getTranslationForChapterLocal(
                    chapterNumber, translator
                )
            } else {
                quranTranslationRepositoryCloud.getTranslationForChapterCloud(
                    chapterNumber, translator
                )
            }
        }
    }
}