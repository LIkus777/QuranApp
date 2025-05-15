package com.zaur.data.al_quran_aqc.repository_impl.cloud

import com.zaur.data.al_quran_aqc.api.QuranApiAqc
import com.zaur.data.network.retryWithBackoff
import com.zaur.domain.al_quran_cloud.models.translate.TranslationAqc
import com.zaur.domain.al_quran_cloud.repository.QuranTranslationRepository

/**
* @author Zaur
* @since 2025-05-12
*/

class QuranTranslationCloudRepositoryImpl(
    private val quranApiAqc: QuranApiAqc,
) : QuranTranslationRepository.Cloud {
    override suspend fun getTranslationForChapterCloud(
        chapterNumber: Int,
        translator: String,
    ): TranslationAqc.Base = retryWithBackoff { quranApiAqc.getTranslationForChapter(chapterNumber, translator).translations() }
}