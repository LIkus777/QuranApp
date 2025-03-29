package com.zaur.data.apiV4.repository_impl

import com.zaur.data.apiV4.api.QuranApiV4
import com.zaur.domain.apiV4.models.tajweed.VerseUthmanTajweedV4
import com.zaur.domain.apiV4.repository.QuranTajweedRepositoryV4

class QuranTajweedRepositoryV4Impl(
    private val quranApiV4: QuranApiV4
) : QuranTajweedRepositoryV4 {
    override suspend fun getUthmanTajweedsForChapter(chapterNumber: Int): List<VerseUthmanTajweedV4> {
        return quranApiV4.getUthmanTajweedsForChapter(chapterNumber)
    }
}