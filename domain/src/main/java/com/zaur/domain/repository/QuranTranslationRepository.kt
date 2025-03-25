package com.zaur.domain.repository

import com.zaur.domain.models.translate.SingleTranslations
import com.zaur.domain.models.translate.Translation

interface QuranTranslationRepository {
    suspend fun getTranslationForChapter(translationId: Int): SingleTranslations
    suspend fun getAvailableTranslations(language: String): List<Translation>
}