package com.zaur.domain.repository

import com.zaur.domain.models.tajweed.VerseUthmanTajweed

interface QuranTajweedRepository {
    suspend fun getUthmanTajweedsForChapter(chapterNumber: Int): List<VerseUthmanTajweed>
}