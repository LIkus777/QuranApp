package com.zaur.data.apiV4.repository_impl

import com.zaur.data.apiV4.api.QuranApiV4
import com.zaur.domain.apiV4.models.translate.SingleTranslationsV4
import com.zaur.domain.apiV4.models.translate.TranslationV4
import com.zaur.domain.apiV4.repository.QuranTranslationRepositoryV4

class QuranTranslationRepositoryV4Impl(
    private val quranApiV4: QuranApiV4
) : QuranTranslationRepositoryV4 {

    override suspend fun getTranslationForChapter(translationId: Int): SingleTranslationsV4 {
        return quranApiV4.getTranslationForChapter(translationId)
    }

    override suspend fun getAvailableTranslations(language: String): List<TranslationV4> {
        return quranApiV4.getAvailableTranslations(language)
    }
}