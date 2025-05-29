package com.zaur.domain.al_quran_cloud.use_case

import com.zaur.domain.al_quran_cloud.models.translate.Translation
import com.zaur.domain.al_quran_cloud.repository.QuranTranslationRepository

/**
 * @author Zaur
 * @since 2025-05-12
 */

interface QuranTranslationUseCase {

    suspend fun getTranslationForChapter(
        chapterNumber: Int,
        translator: String,
    ): Translation

    class Base(
        private val quranTranslationRepositoryLocal: QuranTranslationRepository.Local,
    ) : QuranTranslationUseCase {
        override suspend fun getTranslationForChapter(
            chapterNumber: Int,
            translator: String,
        ): Translation {
            return quranTranslationRepositoryLocal.getTranslationForChapterLocal(
                chapterNumber, translator
            )
        }
    }
}