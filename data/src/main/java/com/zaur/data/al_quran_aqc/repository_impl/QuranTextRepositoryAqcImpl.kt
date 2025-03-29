package com.zaur.data.al_quran_aqc.repository_impl

import com.zaur.data.al_quran_aqc.api.QuranApiAqc
import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChaptersAqc
import com.zaur.domain.al_quran_cloud.models.chapter.ChaptersAqc
import com.zaur.domain.al_quran_cloud.repository.QuranTextRepositoryAqc

class QuranTextRepositoryAqcImpl(
    private val quranApiAqc: QuranApiAqc
) : QuranTextRepositoryAqc {
    override suspend fun getAllChapters(): ChaptersAqc =
        quranApiAqc.getAllChapters()

    override suspend fun getArabicChapter(chapterNumber: Int): ArabicChaptersAqc =
        quranApiAqc.getArabicChapter(chapterNumber)
}