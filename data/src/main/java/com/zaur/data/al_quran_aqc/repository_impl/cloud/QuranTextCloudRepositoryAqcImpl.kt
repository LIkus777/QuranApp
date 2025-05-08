package com.zaur.data.al_quran_aqc.repository_impl.cloud

import com.zaur.data.al_quran_aqc.api.QuranApiAqc
import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChapter
import com.zaur.domain.al_quran_cloud.models.chapter.ChapterAqc
import com.zaur.domain.al_quran_cloud.repository.QuranTextRepositoryAqc

class QuranTextCloudRepositoryAqcImpl(
    private val quranApiAqc: QuranApiAqc,
) : QuranTextRepositoryAqc.Cloud {
    override suspend fun getAllChaptersCloud(): List<ChapterAqc.Base> =
        quranApiAqc.getAllChapters().chapters()

    override suspend fun getArabicChapterCloud(chapterNumber: Int): ArabicChapter.Base =
        quranApiAqc.getArabicChapter(chapterNumber).arabicChapters()
}