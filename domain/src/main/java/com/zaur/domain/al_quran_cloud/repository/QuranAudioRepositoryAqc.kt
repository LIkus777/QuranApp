package com.zaur.domain.al_quran_cloud.repository

import com.zaur.domain.al_quran_cloud.models.audiofile.CacheAudio
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudioFile
import com.zaur.domain.al_quran_cloud.models.audiofile.VerseAudioAqc
import com.zaur.domain.base.repository.BaseQuranAudioRepository
import java.io.File

interface QuranAudioRepositoryAqc : BaseQuranAudioRepository {
    interface Local : QuranAudioRepositoryAqc {
        suspend fun getChapterAudioOfReciterLocal(
            chapterNumber: Int,
            reciter: String,
        ): ChapterAudioFile.Base

        suspend fun getAyahAudioByKeyLocal(verseKey: String, reciter: String): VerseAudioAqc.Base
    }

    interface Cloud : QuranAudioRepositoryAqc {
        suspend fun downloadToCache(chapterNumber: Int, reciter: String): List<CacheAudio.Base>

        suspend fun getChapterAudioOfReciterCloud(
            chapterNumber: Int,
            reciter: String,
        ): ChapterAudioFile.Base

        suspend fun getAyahAudioByKeyCloud(verseKey: String, reciter: String): VerseAudioAqc.Base
    }
}