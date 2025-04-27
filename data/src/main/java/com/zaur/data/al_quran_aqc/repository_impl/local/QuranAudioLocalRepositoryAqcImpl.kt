package com.zaur.data.al_quran_aqc.repository_impl.local

import com.zaur.data.room.dao.ChapterAudioDao
import com.zaur.data.room.dao.VerseAudioDao
import com.zaur.data.room.models.mappers.toDomain
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudioFile
import com.zaur.domain.al_quran_cloud.models.audiofile.VerseAudioAqc
import com.zaur.domain.al_quran_cloud.repository.QuranAudioRepositoryAqc

class QuranAudioLocalRepositoryAqcImpl(
    private val chapterAudioDao: ChapterAudioDao,
    private val verseAudioDao: VerseAudioDao,
) : QuranAudioRepositoryAqc.Local {
    override suspend fun getChapterAudioOfReciterLocal(
        chapterNumber: Int, reciter: String,
    ): ChapterAudioFile =
        chapterAudioDao.getChapterAudioOfReciter(chapterNumber, reciter).toDomain()

    override suspend fun getAyahAudioByKeyLocal(
        verseKey: String, reciter: String,
    ): VerseAudioAqc = verseAudioDao.getAyahAudioByKey(verseKey, reciter).toDomain()
}