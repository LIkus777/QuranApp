package com.zaur.domain.al_quran_cloud.repository

import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChaptersAqc
import com.zaur.domain.al_quran_cloud.models.chapter.ChaptersAqc
import com.zaur.domain.base.repository.BaseQuranTextRepository

interface QuranTextRepositoryAqc : BaseQuranTextRepository {
    suspend fun getAllChapters(): ChaptersAqc
    suspend fun getArabicChapter(chapterNumber: Int): ArabicChaptersAqc
}