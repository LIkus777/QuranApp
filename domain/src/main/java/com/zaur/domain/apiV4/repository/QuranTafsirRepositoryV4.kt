package com.zaur.domain.apiV4.repository

import com.zaur.domain.apiV4.models.tafsir.SingleTafsirsV4
import com.zaur.domain.apiV4.models.tafsir.TafsirV4
import com.zaur.domain.base.repository.BaseQuranTafsirRepository

interface QuranTafsirRepositoryV4 : BaseQuranTafsirRepository {
    suspend fun getTafsirForChapter(tafsirId: Int, chapterNumber: Int): SingleTafsirsV4
    suspend fun getAvailableTafsirs(language: String): List<TafsirV4>
}