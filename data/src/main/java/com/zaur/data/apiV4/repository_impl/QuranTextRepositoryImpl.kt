package com.zaur.data.apiV4.repository_impl

import com.zaur.data.apiV4.api.QuranApiV4
import com.zaur.domain.models.chapter.Chapter
import com.zaur.domain.models.juz.Juz
import com.zaur.domain.repository.QuranTextRepository

class QuranTextRepositoryImpl(
    private val quranApiV4: QuranApiV4
) : QuranTextRepository {
    override suspend fun getAllChapters(language: String): List<Chapter> {
        return quranApiV4.getAllChapters(language)
    }

    override suspend fun getChapter(chapterNumber: Int, language: String): Chapter {
        return quranApiV4.getChapter(chapterNumber, language)
    }

    override suspend fun getAllJuzs(): List<Juz> {
        return quranApiV4.getAllJuzs()
    }
}