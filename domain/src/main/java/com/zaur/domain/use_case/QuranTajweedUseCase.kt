package com.zaur.domain.use_case

import com.zaur.domain.models.tajweed.VerseUthmanTajweed
import com.zaur.domain.repository.QuranTajweedRepository

class QuranTajweedUseCase(
    private val quranTajweedRepository: QuranTajweedRepository
) {
    suspend fun getUthmanTajweedsForChapter(chapterNumber: Int): List<VerseUthmanTajweed> =
        quranTajweedRepository.getUthmanTajweedsForChapter(chapterNumber)
}