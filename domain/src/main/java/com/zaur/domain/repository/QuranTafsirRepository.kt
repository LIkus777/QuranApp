package com.zaur.domain.repository

import com.zaur.domain.models.tafsir.SingleTafsirs
import com.zaur.domain.models.tafsir.Tafsir

interface QuranTafsirRepository {
    suspend fun getTafsirForChapter(tafsirId: Int, chapterNumber: Int): SingleTafsirs
    suspend fun getAvailableTafsirs(language: String): List<Tafsir>
}