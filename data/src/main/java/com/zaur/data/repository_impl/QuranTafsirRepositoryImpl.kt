package com.zaur.data.repository_impl

import com.zaur.data.api.QuranApi
import com.zaur.domain.models.tafsir.SingleTafsirs
import com.zaur.domain.repository.QuranTafsirRepository

class QuranTafsirRepositoryImpl(
    private val quranApi: QuranApi
) : QuranTafsirRepository {
    override suspend fun getTafsirForChapter(chapterNumber: Int): SingleTafsirs {
        TODO("Not yet implemented")
    }
}