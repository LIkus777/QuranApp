package com.zaur.domain.use_case

import com.zaur.domain.models.tafsir.SingleTafsirs
import com.zaur.domain.models.tafsir.Tafsir
import com.zaur.domain.repository.QuranTafsirRepository

class QuranTafsirUseCase(
    private val quranTafsirRepository: QuranTafsirRepository
) {
    suspend fun getTafsirForChapter(tafsirId: Int, chapterNumber: Int): SingleTafsirs =
        quranTafsirRepository.getTafsirForChapter(tafsirId, chapterNumber)

    suspend fun getAvailableTafsirs(language: String): List<Tafsir> =
        quranTafsirRepository.getAvailableTafsirs(language)
}