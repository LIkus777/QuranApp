package com.zaur.domain.apiV4.use_case

import com.zaur.domain.apiV4.models.translate.SingleTranslationsV4
import com.zaur.domain.apiV4.models.translate.TranslationV4
import com.zaur.domain.apiV4.repository.QuranTranslationRepositoryV4

class QuranTranslationUseCaseV4(
    private val quranTranslationRepositoryV4: QuranTranslationRepositoryV4
) {
    suspend fun getTranslationForChapter(translationId: Int): SingleTranslationsV4 =
        quranTranslationRepositoryV4.getTranslationForChapter(translationId)

    suspend fun getAvailableTranslations(language: String): List<TranslationV4> =
        quranTranslationRepositoryV4.getAvailableTranslations(language)
}