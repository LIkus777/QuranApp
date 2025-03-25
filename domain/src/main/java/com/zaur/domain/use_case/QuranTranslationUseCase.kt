package com.zaur.domain.use_case

import com.zaur.domain.models.translate.SingleTranslations
import com.zaur.domain.models.translate.Translation
import com.zaur.domain.repository.QuranTranslationRepository

class QuranTranslationUseCase(
    private val quranTranslationRepository: QuranTranslationRepository
) {
    suspend fun getTranslationForChapter(translationId: Int): SingleTranslations =
        quranTranslationRepository.getTranslationForChapter(translationId)

    suspend fun getAvailableTranslations(language: String): List<Translation> =
        quranTranslationRepository.getAvailableTranslations(language)
}