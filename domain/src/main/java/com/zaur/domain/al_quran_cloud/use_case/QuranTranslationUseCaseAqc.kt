package com.zaur.domain.al_quran_cloud.use_case

import com.zaur.domain.al_quran_cloud.models.translate.TranslationChapterAqc
import com.zaur.domain.al_quran_cloud.repository.QuranTranslationRepositoryAqc

class QuranTranslationUseCaseAqc(
    private val quranTranslationRepositoryAqc: QuranTranslationRepositoryAqc
) {
    suspend fun getTranslationForChapter(translator: String): TranslationChapterAqc =
        quranTranslationRepositoryAqc.getTranslationForChapter(translator)
}