package com.zaur.domain.al_quran_cloud.use_case

import com.zaur.domain.al_quran_cloud.models.translate.TranslationsChapterAqc
import com.zaur.domain.al_quran_cloud.repository.QuranTranslationRepositoryAqc

class QuranTranslationUseCaseAqc(
    private val quranTranslationRepositoryAqc: QuranTranslationRepositoryAqc
) {
    suspend fun getTranslationForChapter(
        chapterNumber: Int,
        translator: String
    ): TranslationsChapterAqc =
        quranTranslationRepositoryAqc.getTranslationForChapter(chapterNumber, translator)
}