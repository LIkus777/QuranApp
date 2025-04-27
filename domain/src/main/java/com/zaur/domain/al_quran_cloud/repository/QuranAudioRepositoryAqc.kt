package com.zaur.domain.al_quran_cloud.repository

import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudioFile
import com.zaur.domain.al_quran_cloud.models.audiofile.VerseAudioAqc
import com.zaur.domain.base.repository.BaseQuranAudioRepository

interface QuranAudioRepositoryAqc : BaseQuranAudioRepository {
    interface Local : QuranAudioRepositoryAqc {
        suspend fun getChapterAudioOfReciterLocal(
            chapterNumber: Int,
            reciter: String,
        ): ChapterAudioFile

        suspend fun getAyahAudioByKeyLocal(verseKey: String, reciter: String): VerseAudioAqc
    }

    interface Cloud : QuranAudioRepositoryAqc {
        suspend fun getChapterAudioOfReciterCloud(
            chapterNumber: Int,
            reciter: String,
        ): ChapterAudioFile

        suspend fun getAyahAudioByKeyCloud(verseKey: String, reciter: String): VerseAudioAqc
    }
}