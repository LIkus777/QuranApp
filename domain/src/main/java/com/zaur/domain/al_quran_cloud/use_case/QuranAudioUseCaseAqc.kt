package com.zaur.domain.al_quran_cloud.use_case

import com.zaur.domain.al_quran_cloud.models.audiofile.Ayah
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudioFile
import com.zaur.domain.al_quran_cloud.repository.QuranAudioRepositoryAqc
import com.zaur.domain.storage.ReciterStorage

interface QuranAudioUseCaseAqc {

    fun getReciter(): String?
    fun saveReciter(identifier: String)
    suspend fun getChapterAudioOfReciter(chapterNumber: Int, reciter: String): ChapterAudioFile
    suspend fun getAyahAudioByKey(verseKey: String, reciter: String): Ayah

    class Base(
        private val quranAudioRepositoryAqc: QuranAudioRepositoryAqc,
        private val reciterStorage: ReciterStorage,
    ) : QuranAudioUseCaseAqc {
        override suspend fun getChapterAudioOfReciter(
            chapterNumber: Int,
            reciter: String,
        ): ChapterAudioFile =
            quranAudioRepositoryAqc.getChapterAudioOfReciter(chapterNumber, reciter)

        override suspend fun getAyahAudioByKey(
            verseKey: String,
            reciter: String,
        ): Ayah = quranAudioRepositoryAqc.getAyahAudioByKey(verseKey, reciter)

        override fun saveReciter(identifier: String) {
            reciterStorage.saveSelectedReciter(identifier)
        }

        override fun getReciter(): String? = reciterStorage.getSelectedReciter()
    }
}