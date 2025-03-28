package com.zaur.data.apiV4.repository_impl

import com.zaur.data.apiV4.api.QuranApiV4
import com.zaur.domain.models.translate.SingleTranslations
import com.zaur.domain.models.translate.Translation
import com.zaur.domain.repository.QuranTranslationRepository

class QuranTranslationRepositoryImpl(
    private val quranApiV4: QuranApiV4
) : QuranTranslationRepository {

    override suspend fun getTranslationForChapter(translationId: Int): SingleTranslations {
        return quranApiV4.getTranslationForChapter(translationId)
    }

    override suspend fun getAvailableTranslations(language: String): List<Translation> {
        return quranApiV4.getAvailableTranslations(language)
    }
}