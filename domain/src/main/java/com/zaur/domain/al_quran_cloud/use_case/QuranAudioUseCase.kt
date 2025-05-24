package com.zaur.domain.al_quran_cloud.use_case

import com.zaur.domain.al_quran_cloud.models.audiofile.CacheAudio
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudioFile
import com.zaur.domain.al_quran_cloud.models.audiofile.VerseAudioAqc
import com.zaur.domain.al_quran_cloud.repository.OfflineRepository
import com.zaur.domain.al_quran_cloud.repository.QuranAudioRepository
import com.zaur.domain.storage.QuranStorage

/**
* @author Zaur
* @since 2025-05-12
*/

interface QuranAudioUseCase {

    suspend fun downloadToCache(chapterNumber: Int, reciter: String): List<CacheAudio.Base>

    suspend fun getChapterAudioOfReciter(
        chapterNumber: Int,
        reciter: String,
    ): ChapterAudioFile

    suspend fun getAyahAudioByKey(verseKey: String, reciter: String): VerseAudioAqc

    fun getLastPlayedSurah(): Int
    fun setLastPlayedSurah(surahNumber: Int)

    class Base(
        private val quranStorage: QuranStorage,
        private val offlineRepository: OfflineRepository,
        private val quranAudioRepositoryLocal: QuranAudioRepository.Local,
        private val quranAudioRepositoryCloud: QuranAudioRepository.Cloud,
    ) : QuranAudioUseCase {
        override suspend fun downloadToCache(chapterNumber: Int, reciter: String): List<CacheAudio.Base> =
            quranAudioRepositoryCloud.downloadToCache(chapterNumber, reciter)

        override suspend fun getChapterAudioOfReciter(
            chapterNumber: Int,
            reciter: String,
        ): ChapterAudioFile = if (offlineRepository.isOffline()) {
            quranAudioRepositoryLocal.getChapterAudioOfReciterLocal(chapterNumber, reciter)
        } else {
            quranAudioRepositoryCloud.getChapterAudioOfReciterCloud(chapterNumber, reciter)
        }


        override suspend fun getAyahAudioByKey(
            verseKey: String,
            reciter: String,
        ): VerseAudioAqc = if (offlineRepository.isOffline()) {
            quranAudioRepositoryLocal.getAyahAudioByKeyLocal(verseKey, reciter)
        } else {
            quranAudioRepositoryCloud.getAyahAudioByKeyCloud(verseKey, reciter)
        }

        override fun getLastPlayedSurah(): Int = quranStorage.getLastPlayedSurah()

        override fun setLastPlayedSurah(surahNumber: Int) = quranStorage.setLastPlayedSurah(surahNumber)
    }
}