package com.zaur.data.al_quran_aqc.repository_impl

import com.zaur.data.room.dao.ArabicChapterDao
import com.zaur.data.room.dao.ChapterDao
import com.zaur.data.room.models.mappers.toDomain
import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChapter
import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChaptersAqc
import com.zaur.domain.al_quran_cloud.models.chapter.ChapterAqc
import com.zaur.domain.al_quran_cloud.models.chapter.ChaptersAqc
import com.zaur.domain.al_quran_cloud.repository.QuranTextRepositoryAqc

class QuranTextRepositoryAqcImpl(
    private val chapterDao: ChapterDao,
    private val arabicDao: ArabicChapterDao
) : QuranTextRepositoryAqc {
    override suspend fun getAllChapters(): List<ChapterAqc> =
        chapterDao.getAllChapters().map { it.toDomain() }

    override suspend fun getArabicChapter(chapterNumber: Int): ArabicChapter =
        arabicDao.getArabicChapter(chapterNumber).toDomain()
}