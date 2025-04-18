package com.zaur.domain.al_quran_cloud.repository

import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudioFile
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudiosFileAqc
import com.zaur.domain.al_quran_cloud.models.audiofile.VersesAudioFileAqc
import com.zaur.domain.base.repository.BaseQuranAudioRepository

interface QuranAudioRepositoryAqc : BaseQuranAudioRepository {
    suspend fun getChapterAudioOfReciter(chapterNumber: Int, reciter: String): ChapterAudioFile
    suspend fun getVerseAudioFile(verseKey: String, reciter: String): VersesAudioFileAqc
}