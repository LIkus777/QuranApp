package com.zaur.domain.al_quran_cloud.repository

import com.zaur.domain.al_quran_cloud.models.audiofile.CacheAudio
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudioFile
import com.zaur.domain.al_quran_cloud.models.audiofile.VerseAudio
import com.zaur.domain.base.repository.BaseQuranAudioRepository

/**
* @author Zaur
* @since 2025-05-12
*/

interface QuranAudioRepository : BaseQuranAudioRepository {
    interface Local : QuranAudioRepository {
        suspend fun getChapterAudioOfReciterLocal(
            chapterNumber: Int,
            reciter: String,
        ): ChapterAudioFile.Base

        suspend fun getAyahAudioByKeyLocal(verseKey: String, reciter: String): VerseAudio.Base
    }

    interface Cloud : QuranAudioRepository {
        suspend fun downloadToCache(chapterNumber: Int, reciter: String): List<CacheAudio.Base>

        suspend fun getChapterAudioOfReciterCloud(
            chapterNumber: Int,
            reciter: String,
        ): ChapterAudioFile.Base

        suspend fun getAyahAudioByKeyCloud(verseKey: String, reciter: String): VerseAudio.Base
    }
}