package com.zaur.data.repository_impl

import com.zaur.data.api.QuranApi
import com.zaur.domain.models.tafsir.SingleTafsirs
import com.zaur.domain.models.tafsir.Tafsir
import com.zaur.domain.repository.QuranTafsirRepository

class QuranTafsirRepositoryImpl(
    private val quranApi: QuranApi
) : QuranTafsirRepository {
    override suspend fun getTafsirForChapter(tafsirId: Int, chapterNumber: Int): SingleTafsirs {
        return quranApi.getTafsirForChapter(tafsirId, chapterNumber)
    }

    override suspend fun getAvailableTafsirs(language: String): List<Tafsir> {
        return quranApi.getAvailableTafsirs(language)
    }
}