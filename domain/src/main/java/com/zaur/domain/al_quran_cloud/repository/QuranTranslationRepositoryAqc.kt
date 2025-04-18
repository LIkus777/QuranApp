package com.zaur.domain.al_quran_cloud.repository

import com.zaur.domain.al_quran_cloud.models.translate.TranslationAqc
import com.zaur.domain.base.repository.BaseQuranTranslationRepository

interface QuranTranslationRepositoryAqc : BaseQuranTranslationRepository {
    suspend fun getTranslationForChapter(chapterNumber: Int, translator: String): TranslationAqc
}