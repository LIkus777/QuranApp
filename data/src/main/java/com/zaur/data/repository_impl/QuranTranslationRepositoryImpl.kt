package com.zaur.data.repository_impl

import com.zaur.data.api.QuranApi
import com.zaur.domain.models.translate.SingleTranslations
import com.zaur.domain.models.translate.Translation
import com.zaur.domain.repository.QuranTranslationRepository

class QuranTranslationRepositoryImpl(
    private val quranApi: QuranApi
) : QuranTranslationRepository {

    override suspend fun getTranslationForChapter(translationId: Int): SingleTranslations {
        return quranApi.getTranslationForChapter(translationId)
    }

    override suspend fun getAvailableTranslations(language: String): Translation {
        return quranApi.getAvailableTranslations(language)
    }
}