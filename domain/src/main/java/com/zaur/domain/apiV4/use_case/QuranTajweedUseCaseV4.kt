package com.zaur.domain.apiV4.use_case

import com.zaur.domain.apiV4.models.tajweed.VerseUthmanTajweedV4
import com.zaur.domain.apiV4.repository.QuranTajweedRepositoryV4

class QuranTajweedUseCaseV4(
    private val quranTajweedRepositoryV4: QuranTajweedRepositoryV4
) {
    suspend fun getUthmanTajweedsForChapter(chapterNumber: Int): List<VerseUthmanTajweedV4> =
        quranTajweedRepositoryV4.getUthmanTajweedsForChapter(chapterNumber)
}