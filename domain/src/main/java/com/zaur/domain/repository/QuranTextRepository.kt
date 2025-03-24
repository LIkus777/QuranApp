package com.zaur.domain.repository

import com.zaur.domain.models.chapter.Chapter
import com.zaur.domain.models.juz.Juz

interface QuranTextRepository {
    suspend fun getAllChapters(language: String): List<Chapter>
    suspend fun getChapter(chapterNumber: Int, language: String): Chapter
    suspend fun getAllJuzs(): List<Juz>
}