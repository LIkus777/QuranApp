package com.zaur.data.al_quran_aqc.repository_impl.cloud

import com.zaur.data.al_quran_aqc.api.QuranApiAqc
import com.zaur.domain.al_quran_cloud.models.translate.TranslationAqc
import com.zaur.domain.al_quran_cloud.models.translate.TranslationsChapterAqc
import com.zaur.domain.al_quran_cloud.repository.QuranTranslationRepositoryAqc

/**
* @author Zaur
* @since 2025-05-12
*/

class QuranTranslationCloupRepositoryAqcImpl(
    private val quranApiAqc: QuranApiAqc,
) : QuranTranslationRepositoryAqc.Cloud {
    override suspend fun getTranslationForChapterCloud(
        chapterNumber: Int,
        translator: String,
    ): TranslationAqc.Base = quranApiAqc.getTranslationForChapter(chapterNumber, translator).translations()
}