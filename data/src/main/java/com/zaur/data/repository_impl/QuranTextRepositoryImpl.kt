package com.zaur.data.repository_impl

import com.zaur.data.api.QuranApi
import com.zaur.domain.models.chapter.Chapter
import com.zaur.domain.models.juz.Juz
import com.zaur.domain.repository.QuranTextRepository

class QuranTextRepositoryImpl(
    private val quranApi: QuranApi
) : QuranTextRepository {
    override suspend fun getAllChapters(language: String): List<Chapter> {
        return quranApi.getAllChapters(language)
    }

    override suspend fun getChapter(chapterNumber: Int, language: String): Chapter {
        return quranApi.getChapter(chapterNumber, language)
    }

    override suspend fun getAllJuzs(): List<Juz> {
        return quranApi.getAllJuzs()
    }
}