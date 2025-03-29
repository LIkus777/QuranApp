package com.zaur.data.apiV4.repository_impl

import com.zaur.data.apiV4.api.QuranApiV4
import com.zaur.domain.apiV4.models.chapter.ChapterV4
import com.zaur.domain.apiV4.models.juz.JuzV4
import com.zaur.domain.apiV4.repository.QuranTextRepositoryV4

class QuranTextRepositoryV4Impl(
    private val quranApiV4: QuranApiV4
) : QuranTextRepositoryV4 {
    override suspend fun getAllChapters(language: String): List<ChapterV4> {
        return quranApiV4.getAllChapters(language)
    }

    override suspend fun getChapter(chapterNumber: Int, language: String): ChapterV4 {
        return quranApiV4.getChapter(chapterNumber, language)
    }

    override suspend fun getAllJuzs(): List<JuzV4> {
        return quranApiV4.getAllJuzs()
    }
}