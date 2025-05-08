package com.zaur.domain.al_quran_cloud.repository

import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChapter
import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChaptersAqc
import com.zaur.domain.al_quran_cloud.models.chapter.ChapterAqc
import com.zaur.domain.al_quran_cloud.models.chapter.ChaptersAqc
import com.zaur.domain.base.repository.BaseQuranTextRepository

interface QuranTextRepositoryAqc : BaseQuranTextRepository {
    interface Local : QuranTextRepositoryAqc {
        suspend fun getAllChaptersLocal(): List<ChapterAqc.Base>
        suspend fun getArabicChapterLocal(chapterNumber: Int): ArabicChapter.Base
    }

    interface Cloud : QuranTextRepositoryAqc{
        suspend fun getAllChaptersCloud(): List<ChapterAqc.Base>
        suspend fun getArabicChapterCloud(chapterNumber: Int): ArabicChapter.Base
    }
}