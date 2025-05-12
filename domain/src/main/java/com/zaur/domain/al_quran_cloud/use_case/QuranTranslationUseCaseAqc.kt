package com.zaur.domain.al_quran_cloud.use_case

import com.zaur.domain.al_quran_cloud.models.translate.TranslationAqc
import com.zaur.domain.al_quran_cloud.repository.OfflineRepository
import com.zaur.domain.al_quran_cloud.repository.QuranTranslationRepositoryAqc

/**
* @author Zaur
* @since 2025-05-12
*/

interface QuranTranslationUseCaseAqc {

    suspend fun getTranslationForChapter(
        chapterNumber: Int,
        translator: String,
    ): TranslationAqc

    class Base(
        private val offlineRepository: OfflineRepository,
        private val quranTranslationRepositoryAqcLocal: QuranTranslationRepositoryAqc.Local,
        private val quranTranslationRepositoryAqcCloud: QuranTranslationRepositoryAqc.Cloud,
    ) : QuranTranslationUseCaseAqc {
        override suspend fun getTranslationForChapter(
            chapterNumber: Int,
            translator: String,
        ): TranslationAqc {
            return if (offlineRepository.isOffline()) {
                quranTranslationRepositoryAqcLocal.getTranslationForChapterLocal(
                    chapterNumber, translator
                )
            } else {
                quranTranslationRepositoryAqcCloud.getTranslationForChapterCloud(
                    chapterNumber, translator
                )
            }
        }
    }
}