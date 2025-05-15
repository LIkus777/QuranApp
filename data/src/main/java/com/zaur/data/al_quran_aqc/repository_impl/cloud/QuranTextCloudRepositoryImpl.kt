package com.zaur.data.al_quran_aqc.repository_impl.cloud

import com.zaur.data.al_quran_aqc.api.QuranApiAqc
import com.zaur.data.network.retryWithBackoff
import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChapter
import com.zaur.domain.al_quran_cloud.models.chapter.ChapterAqc
import com.zaur.domain.al_quran_cloud.repository.QuranTextRepository

/**
* @author Zaur
* @since 2025-05-12
*/

class QuranTextCloudRepositoryImpl(
    private val quranApiAqc: QuranApiAqc,
) : QuranTextRepository.Cloud {
    override suspend fun getAllChaptersCloud(): List<ChapterAqc.Base> = retryWithBackoff {
        quranApiAqc.getAllChapters().chapters() }

    override suspend fun getArabicChapterCloud(chapterNumber: Int): ArabicChapter.Base = retryWithBackoff {
        quranApiAqc.getArabicChapter(chapterNumber).arabicChapters() }
}