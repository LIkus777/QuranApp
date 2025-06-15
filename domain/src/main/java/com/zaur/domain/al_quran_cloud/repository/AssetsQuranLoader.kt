package com.zaur.domain.al_quran_cloud.repository

import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChapter

interface AssetsQuranLoader {
    fun onNextPage(currentPage: Int)
    fun onPreviousPage(currentPage: Int)
    fun getAllChapters(): List<ArabicChapter.Base>
    fun getSurahNameByNumber(chapterNumber: Int): String
    fun getArabicChapter(chapterNumber: Int): ArabicChapter.Base
}