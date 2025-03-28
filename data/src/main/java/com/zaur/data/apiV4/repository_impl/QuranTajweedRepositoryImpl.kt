package com.zaur.data.apiV4.repository_impl

import com.zaur.data.apiV4.api.QuranApiV4
import com.zaur.domain.models.tajweed.VerseUthmanTajweed
import com.zaur.domain.repository.QuranTajweedRepository

class QuranTajweedRepositoryImpl(
    private val quranApiV4: QuranApiV4
) : QuranTajweedRepository {
    override suspend fun getUthmanTajweedsForChapter(chapterNumber: Int): List<VerseUthmanTajweed> {
        return quranApiV4.getUthmanTajweedsForChapter(chapterNumber)
    }
}