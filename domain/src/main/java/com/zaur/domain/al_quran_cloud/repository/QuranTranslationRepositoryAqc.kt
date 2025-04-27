package com.zaur.domain.al_quran_cloud.repository

import com.zaur.domain.al_quran_cloud.models.translate.TranslationAqc
import com.zaur.domain.al_quran_cloud.models.translate.TranslationsChapterAqc
import com.zaur.domain.base.repository.BaseQuranTranslationRepository

interface QuranTranslationRepositoryAqc : BaseQuranTranslationRepository {
    interface Local : QuranTranslationRepositoryAqc {
        suspend fun getTranslationForChapterLocal(chapterNumber: Int, translator: String): TranslationAqc
    }

    interface Cloud : QuranTranslationRepositoryAqc {
        suspend fun getTranslationForChapterCloud(
            chapterNumber: Int,
            translator: String,
        ): TranslationAqc
    }
}