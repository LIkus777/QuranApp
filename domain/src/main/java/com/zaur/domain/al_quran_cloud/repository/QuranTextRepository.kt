package com.zaur.domain.al_quran_cloud.repository

import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChapter
import com.zaur.domain.al_quran_cloud.models.chapter.ChapterAqc
import com.zaur.domain.base.repository.BaseQuranTextRepository

/**
* @author Zaur
* @since 2025-05-12
*/

interface QuranTextRepository : BaseQuranTextRepository {
    interface Local : QuranTextRepository {
        suspend fun getAllChaptersLocal(): List<ChapterAqc.Base>
        suspend fun getArabicChapterLocal(chapterNumber: Int): ArabicChapter.Base
    }

    interface Cloud : QuranTextRepository{
        suspend fun getAllChaptersCloud(): List<ChapterAqc.Base>
        suspend fun getArabicChapterCloud(chapterNumber: Int): ArabicChapter.Base
    }
}