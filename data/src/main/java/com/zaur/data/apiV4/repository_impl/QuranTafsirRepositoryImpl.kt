package com.zaur.data.apiV4.repository_impl

import com.zaur.data.apiV4.api.QuranApiV4
import com.zaur.domain.models.tafsir.SingleTafsirs
import com.zaur.domain.models.tafsir.Tafsir
import com.zaur.domain.repository.QuranTafsirRepository

class QuranTafsirRepositoryImpl(
    private val quranApiV4: QuranApiV4
) : QuranTafsirRepository {
    override suspend fun getTafsirForChapter(tafsirId: Int, chapterNumber: Int): SingleTafsirs {
        return quranApiV4.getTafsirForChapter(tafsirId, chapterNumber)
    }

    override suspend fun getAvailableTafsirs(language: String): List<Tafsir> {
        return quranApiV4.getAvailableTafsirs(language)
    }
}