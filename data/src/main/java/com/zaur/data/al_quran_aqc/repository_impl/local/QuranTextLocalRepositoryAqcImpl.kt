package com.zaur.data.al_quran_aqc.repository_impl.local

import com.zaur.data.room.dao.ArabicChapterDao
import com.zaur.data.room.dao.ChapterDao
import com.zaur.data.room.models.mappers.toDomain
import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChapter
import com.zaur.domain.al_quran_cloud.models.chapter.ChapterAqc
import com.zaur.domain.al_quran_cloud.repository.QuranTextRepositoryAqc

class QuranTextLocalRepositoryAqcImpl(
    private val chapterDao: ChapterDao,
    private val arabicDao: ArabicChapterDao
) : QuranTextRepositoryAqc.Local {
    override suspend fun getAllChaptersLocal(): List<ChapterAqc> =
        chapterDao.getAllChapters().map { it.toDomain() }

    override suspend fun getArabicChapterLocal(chapterNumber: Int): ArabicChapter =
        arabicDao.getArabicChapter(chapterNumber).toDomain()
}