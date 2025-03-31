package com.zaur.domain.al_quran_cloud.use_case

import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChaptersAqc
import com.zaur.domain.al_quran_cloud.models.chapter.ChaptersAqc
import com.zaur.domain.al_quran_cloud.repository.QuranTextRepositoryAqc
import com.zaur.domain.storage.QuranStorage
import com.zaur.domain.storage.ReciterStorage

class QuranTextUseCaseAqc(
    private val quranTextRepositoryAqc: QuranTextRepositoryAqc,
    private val quranStorage: QuranStorage,
    private val reciterStorage: ReciterStorage
) {
    suspend fun fetchAllChapters(): ChaptersAqc = quranTextRepositoryAqc.getAllChapters()

    suspend fun fetchArabicChapter(chapterNumber: Int): ArabicChaptersAqc =
        quranTextRepositoryAqc.getArabicChapter(chapterNumber)

    fun saveLastReadPosition(chapterNumber: Int, ayahNumber: Int) {
        quranStorage.saveLastRead(chapterNumber, ayahNumber)
    }

    fun getLastReadPosition(): Pair<Int, Int> = quranStorage.getLastRead()

    fun markSurahScreenAsOpened() {
        quranStorage.setSurahScreenOpened()
    }

    fun isSurahScreenPreviouslyOpened(): Boolean = quranStorage.isSurahScreenOpened()

    fun saveReciter(identifier: String) {
        reciterStorage.saveSelectedReciter(identifier)
    }

    fun getReciter(): String? = reciterStorage.getSelectedReciter()
}
