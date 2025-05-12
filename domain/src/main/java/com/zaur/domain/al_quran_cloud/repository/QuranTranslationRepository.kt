package com.zaur.domain.al_quran_cloud.repository

import com.zaur.domain.al_quran_cloud.models.translate.TranslationAqc
import com.zaur.domain.base.repository.BaseQuranTranslationRepository

/**
* @author Zaur
* @since 2025-05-12
*/

interface QuranTranslationRepository : BaseQuranTranslationRepository {
    interface Local : QuranTranslationRepository {
        suspend fun getTranslationForChapterLocal(chapterNumber: Int, translator: String): TranslationAqc.Base
    }

    interface Cloud : QuranTranslationRepository {
        suspend fun getTranslationForChapterCloud(
            chapterNumber: Int,
            translator: String,
        ): TranslationAqc.Base
    }
}