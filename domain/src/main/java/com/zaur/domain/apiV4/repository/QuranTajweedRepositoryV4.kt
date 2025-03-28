package com.zaur.domain.apiV4.repository

import com.zaur.domain.apiV4.models.tajweed.VerseUthmanTajweedV4
import com.zaur.domain.base.repository.BaseQuranTajweedRepository

interface QuranTajweedRepositoryV4 : BaseQuranTajweedRepository {
    suspend fun getUthmanTajweedsForChapter(chapterNumber: Int): List<VerseUthmanTajweedV4>
}