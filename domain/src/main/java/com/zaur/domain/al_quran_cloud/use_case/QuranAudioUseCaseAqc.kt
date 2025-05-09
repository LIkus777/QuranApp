package com.zaur.domain.al_quran_cloud.use_case

import com.zaur.domain.al_quran_cloud.models.audiofile.CacheAudio
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudioFile
import com.zaur.domain.al_quran_cloud.models.audiofile.VerseAudioAqc
import com.zaur.domain.al_quran_cloud.repository.OfflineRepository
import com.zaur.domain.al_quran_cloud.repository.QuranAudioRepositoryAqc
import java.io.File

interface QuranAudioUseCaseAqc {

    suspend fun downloadToCache(chapterNumber: Int, reciter: String): List<CacheAudio.Base>

    suspend fun getChapterAudioOfReciter(
        chapterNumber: Int,
        reciter: String,
    ): ChapterAudioFile

    suspend fun getAyahAudioByKey(verseKey: String, reciter: String): VerseAudioAqc

    class Base(
        private val offlineRepository: OfflineRepository,
        private val quranAudioRepositoryAqcLocal: QuranAudioRepositoryAqc.Local,
        private val quranAudioRepositoryAqcCloud: QuranAudioRepositoryAqc.Cloud,
    ) : QuranAudioUseCaseAqc {
        override suspend fun downloadToCache(chapterNumber: Int, reciter: String): List<CacheAudio.Base> =
            quranAudioRepositoryAqcCloud.downloadToCache(chapterNumber, reciter)

        override suspend fun getChapterAudioOfReciter(
            chapterNumber: Int,
            reciter: String,
        ): ChapterAudioFile = if (offlineRepository.isOffline()) {
            quranAudioRepositoryAqcLocal.getChapterAudioOfReciterLocal(chapterNumber, reciter)
        } else {
            quranAudioRepositoryAqcCloud.getChapterAudioOfReciterCloud(chapterNumber, reciter)
        }


        override suspend fun getAyahAudioByKey(
            verseKey: String,
            reciter: String,
        ): VerseAudioAqc = if (offlineRepository.isOffline()) {
            quranAudioRepositoryAqcLocal.getAyahAudioByKeyLocal(verseKey, reciter)
        } else {
            quranAudioRepositoryAqcCloud.getAyahAudioByKeyCloud(verseKey, reciter)
        }
    }
}