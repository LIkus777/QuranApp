package com.zaur.data.al_quran_aqc.repository_impl.local

import com.zaur.data.room.dao.ChapterAudioDao
import com.zaur.data.room.dao.VerseAudioDao
import com.zaur.data.room.models.mappers.audiofile.ChapterAudioMapper
import com.zaur.data.room.models.mappers.audiofile.VerseAudioMapper
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudioFile
import com.zaur.domain.al_quran_cloud.models.audiofile.VerseAudioAqc
import com.zaur.domain.al_quran_cloud.repository.QuranAudioRepositoryAqc

class QuranAudioLocalRepositoryAqcImpl(
    private val chapterAudioDao: ChapterAudioDao,
    private val verseAudioDao: VerseAudioDao,
    private val chapterAudioMapper: ChapterAudioMapper,
    private val verseMapper: VerseAudioMapper,
) : QuranAudioRepositoryAqc.Local {
    override suspend fun getChapterAudioOfReciterLocal(
        chapterNumber: Int, reciter: String,
    ): ChapterAudioFile.Base = chapterAudioMapper.fromData(
        chapterAudioDao.getChapterAudioOfReciter(
            chapterNumber, reciter
        )
    )

    override suspend fun getAyahAudioByKeyLocal(
        verseKey: String, reciter: String,
    ): VerseAudioAqc.Base = verseMapper.fromData(
        verseAudioDao.getAyahAudioByKey(verseKey, reciter).verse,
        verseAudioDao.getAyahAudioByKey(verseKey, reciter).surah
    )
}