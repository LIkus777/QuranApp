package com.zaur.data.al_quran_aqc.repository_impl.cloud

import com.zaur.data.al_quran_aqc.api.QuranApiAqc
import com.zaur.domain.al_quran_cloud.models.translate.TranslationAqc
import com.zaur.domain.al_quran_cloud.repository.QuranTranslationRepositoryAqc

class QuranTranslationCloupRepositoryAqcImpl(
    private val quranApiAqc: QuranApiAqc,
) : QuranTranslationRepositoryAqc.Cloud {
    override suspend fun getTranslationForChapterCloud(
        chapterNumber: Int,
        translator: String,
    ): TranslationAqc = quranApiAqc.getTranslationForChapter(chapterNumber, translator).translations
}