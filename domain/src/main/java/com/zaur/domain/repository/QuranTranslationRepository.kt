package com.zaur.domain.repository

import com.zaur.domain.models.translate.SingleTranslation

interface QuranTranslationRepository {
    fun getTranslationForChapter(translationId: Int): SingleTranslation
}