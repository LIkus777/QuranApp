package com.zaur.data.al_quran_aqc.repository_impl

import com.zaur.data.room.dao.ChapterAudioDao
import com.zaur.data.room.dao.VerseAudioDao
import com.zaur.data.room.models.mappers.toDomain
import com.zaur.domain.al_quran_cloud.models.audiofile.Ayah
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudioFile
import com.zaur.domain.al_quran_cloud.repository.QuranAudioRepositoryAqc

class QuranAudioRepositoryAqcImpl(
    private val chapterAudioDao: ChapterAudioDao,
    private val verseAudioDao: VerseAudioDao,
) : QuranAudioRepositoryAqc {
    override suspend fun getChapterAudioOfReciter(
        chapterNumber: Int, reciter: String,
    ): ChapterAudioFile =
        chapterAudioDao.getChapterAudioOfReciter(chapterNumber, reciter).toDomain()

    override suspend fun getAyahAudioByKey(
        verseKey: String, reciter: String,
    ): Ayah = verseAudioDao.getAyahAudioByKey(verseKey, reciter).toDomain()
}