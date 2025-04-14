package com.zaur.domain.al_quran_cloud.use_case

import com.zaur.domain.al_quran_cloud.models.translate.TranslationsChapterAqc
import com.zaur.domain.al_quran_cloud.repository.QuranTranslationRepositoryAqc

interface QuranTranslationUseCaseAqc {

    suspend fun getTranslationForChapter(
        chapterNumber: Int, translator: String
    ): TranslationsChapterAqc

    class Base(
        private val quranTranslationRepositoryAqc: QuranTranslationRepositoryAqc
    ) : QuranTranslationUseCaseAqc {
        override suspend fun getTranslationForChapter(
            chapterNumber: Int, translator: String
        ): TranslationsChapterAqc =
            quranTranslationRepositoryAqc.getTranslationForChapter(chapterNumber, translator)
    }
}