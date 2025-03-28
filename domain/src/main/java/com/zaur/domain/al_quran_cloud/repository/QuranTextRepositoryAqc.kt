package com.zaur.domain.al_quran_cloud.repository

import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChapterAqc
import com.zaur.domain.al_quran_cloud.models.chapter.ChapterAqc
import com.zaur.domain.base.repository.BaseQuranTextRepository

interface QuranTextRepositoryAqc : BaseQuranTextRepository {
    suspend fun getAllChapters(translator: String): List<ChapterAqc>
    suspend fun getArabicChapter(chapterNumber: Int): ArabicChapterAqc
}