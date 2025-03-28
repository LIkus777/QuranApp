package com.zaur.domain.apiV4.repository

import com.zaur.domain.apiV4.models.translate.SingleTranslationsV4
import com.zaur.domain.apiV4.models.translate.TranslationV4
import com.zaur.domain.base.repository.BaseQuranTranslationRepository

interface QuranTranslationRepositoryV4 : BaseQuranTranslationRepository {
    suspend fun getTranslationForChapter(translationId: Int): SingleTranslationsV4
    suspend fun getAvailableTranslations(language: String): List<TranslationV4>
}