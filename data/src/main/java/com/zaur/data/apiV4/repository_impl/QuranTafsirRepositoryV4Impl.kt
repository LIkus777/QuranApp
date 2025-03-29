package com.zaur.data.apiV4.repository_impl

import com.zaur.data.apiV4.api.QuranApiV4
import com.zaur.domain.apiV4.models.tafsir.SingleTafsirsV4
import com.zaur.domain.apiV4.models.tafsir.TafsirV4
import com.zaur.domain.apiV4.repository.QuranTafsirRepositoryV4

class QuranTafsirRepositoryV4Impl(
    private val quranApiV4: QuranApiV4
) : QuranTafsirRepositoryV4 {
    override suspend fun getTafsirForChapter(tafsirId: Int, chapterNumber: Int): SingleTafsirsV4 {
        return quranApiV4.getTafsirForChapter(tafsirId, chapterNumber)
    }

    override suspend fun getAvailableTafsirs(language: String): List<TafsirV4> {
        return quranApiV4.getAvailableTafsirs(language)
    }
}