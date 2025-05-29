package com.zaur.domain.al_quran_cloud.repository

import com.zaur.domain.al_quran_cloud.models.translate.Translation
import com.zaur.domain.base.repository.BaseQuranTranslationRepository

/**
 * @author Zaur
 * @since 2025-05-12
 */

interface QuranTranslationRepository : BaseQuranTranslationRepository {
    interface Local : QuranTranslationRepository {
        suspend fun getTranslationForChapterLocal(
            chapterNumber: Int,
            translator: String,
        ): Translation.Base
    }

    interface Cloud : QuranTranslationRepository {
        suspend fun getTranslationForChapterCloud(
            chapterNumber: Int,
            translator: String,
        ): Translation.Base
    }
}