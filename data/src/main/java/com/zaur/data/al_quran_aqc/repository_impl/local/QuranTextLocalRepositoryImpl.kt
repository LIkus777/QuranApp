package com.zaur.data.al_quran_aqc.repository_impl.local

import com.zaur.data.room.dao.ArabicChapterDao
import com.zaur.data.room.dao.ChapterDao
import com.zaur.data.room.models.mappers.arabic.ArabicMapper
import com.zaur.data.room.models.mappers.chapter.ChapterMapper
import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChapter
import com.zaur.domain.al_quran_cloud.models.chapter.ChapterAqc
import com.zaur.domain.al_quran_cloud.repository.QuranTextRepository

/**
 * @author Zaur
 * @since 2025-05-12
 */

class QuranTextLocalRepositoryImpl(
    private val chapterMapper: ChapterMapper,
    private val arabicMapper: ArabicMapper,
    private val chapterDao: ChapterDao,
    private val arabicDao: ArabicChapterDao
) : QuranTextRepository.Local {
    override suspend fun getAllChaptersLocal(): List<ChapterAqc.Base> =
        chapterDao.getAllChapters().map { chapterMapper.fromData(it) }

    override suspend fun getArabicChapterLocal(chapterNumber: Int): ArabicChapter.Base =
        arabicMapper.fromData(arabicDao.getArabicChapter(chapterNumber))
}