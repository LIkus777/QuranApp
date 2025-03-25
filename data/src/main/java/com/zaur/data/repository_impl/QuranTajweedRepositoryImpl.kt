package com.zaur.data.repository_impl

import com.zaur.data.api.QuranApi
import com.zaur.domain.models.tajweed.VerseUthmanTajweed
import com.zaur.domain.repository.QuranTajweedRepository

class QuranTajweedRepositoryImpl(
    private val quranApi: QuranApi
) : QuranTajweedRepository {
    override suspend fun getUthmanTajweedsForChapter(chapterNumber: Int): List<VerseUthmanTajweed> {
        return quranApi.getUthmanTajweedsForChapter(chapterNumber)
    }
}