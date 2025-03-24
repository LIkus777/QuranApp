package com.zaur.domain.repository

import com.zaur.domain.models.tafsir.SingleTafsirs

interface QuranTafsirRepository {
    suspend fun getTafsirForChapter(chapterNumber: Int): SingleTafsirs
}