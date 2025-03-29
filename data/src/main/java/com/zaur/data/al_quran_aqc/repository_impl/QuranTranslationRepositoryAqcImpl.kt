package com.zaur.data.al_quran_aqc.repository_impl

import com.zaur.data.al_quran_aqc.api.QuranApiAqc
import com.zaur.domain.al_quran_cloud.models.translate.TranslationsChapterAqc
import com.zaur.domain.al_quran_cloud.repository.QuranTranslationRepositoryAqc

class QuranTranslationRepositoryAqcImpl(
    private val quranApiAqc: QuranApiAqc
) : QuranTranslationRepositoryAqc {
    override suspend fun getTranslationForChapter(
        chapterNumber: Int, translator: String
    ): TranslationsChapterAqc = quranApiAqc.getTranslationForChapter(chapterNumber, translator)
}