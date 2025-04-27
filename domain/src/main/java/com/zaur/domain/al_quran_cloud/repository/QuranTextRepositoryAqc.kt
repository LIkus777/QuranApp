package com.zaur.domain.al_quran_cloud.repository

import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChapter
import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChaptersAqc
import com.zaur.domain.al_quran_cloud.models.chapter.ChapterAqc
import com.zaur.domain.al_quran_cloud.models.chapter.ChaptersAqc
import com.zaur.domain.base.repository.BaseQuranTextRepository

interface QuranTextRepositoryAqc : BaseQuranTextRepository {
    interface Local : QuranTextRepositoryAqc {
        suspend fun getAllChaptersLocal(): List<ChapterAqc>
        suspend fun getArabicChapterLocal(chapterNumber: Int): ArabicChapter
    }

    interface Cloud : QuranTextRepositoryAqc{
        suspend fun getAllChaptersCloud(): List<ChapterAqc>
        suspend fun getArabicChapterCloud(chapterNumber: Int): ArabicChapter
    }
}