package com.zaur.domain.apiV4.repository

import com.zaur.domain.apiV4.models.chapter.ChapterV4
import com.zaur.domain.apiV4.models.juz.JuzV4
import com.zaur.domain.base.repository.BaseQuranTextRepository

interface QuranTextRepositoryV4 : BaseQuranTextRepository {
    suspend fun getAllChapters(language: String): List<ChapterV4>
    suspend fun getChapter(chapterNumber: Int, language: String): ChapterV4
    suspend fun getAllJuzs(): List<JuzV4>
}