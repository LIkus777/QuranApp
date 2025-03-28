package com.zaur.domain.apiV4.use_case

import com.zaur.domain.apiV4.models.tafsir.SingleTafsirsV4
import com.zaur.domain.apiV4.models.tafsir.TafsirV4
import com.zaur.domain.apiV4.repository.QuranTafsirRepositoryV4

class QuranTafsirUseCaseV4(
    private val quranTafsirRepositoryV4: QuranTafsirRepositoryV4
) {
    suspend fun getTafsirForChapter(tafsirId: Int, chapterNumber: Int): SingleTafsirsV4 =
        quranTafsirRepositoryV4.getTafsirForChapter(tafsirId, chapterNumber)

    suspend fun getAvailableTafsirs(language: String): List<TafsirV4> =
        quranTafsirRepositoryV4.getAvailableTafsirs(language)
}