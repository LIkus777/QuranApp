package com.zaur.domain.al_quran_cloud.use_case

import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChapterAqc
import com.zaur.domain.al_quran_cloud.models.chapter.ChapterAqc
import com.zaur.domain.al_quran_cloud.repository.QuranTextRepositoryAqc

class QuranTextUseCaseAqc(
    private val quranTextRepositoryAqc: QuranTextRepositoryAqc
) {
    suspend fun getAllChapters(translator: String): List<ChapterAqc> =
        quranTextRepositoryAqc.getAllChapters(translator)

    suspend fun getArabicChapter(chapterNumber: Int, translator: String): ArabicChapterAqc =
        quranTextRepositoryAqc.getArabicChapter(chapterNumber, translator)
}