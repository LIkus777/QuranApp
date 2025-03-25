package com.zaur.domain.use_case

import com.zaur.domain.models.chapter.Chapter
import com.zaur.domain.models.juz.Juz
import com.zaur.domain.repository.QuranTextRepository

class QuranTextUseCase(
    private val quranTextRepository: QuranTextRepository
) {
    suspend fun getAllChapters(language: String): List<Chapter> =
        quranTextRepository.getAllChapters(language)

    suspend fun getChapter(chapterNumber: Int, language: String): Chapter =
        quranTextRepository.getChapter(chapterNumber, language)

    suspend fun getAllJuzs(): List<Juz> = quranTextRepository.getAllJuzs()
}