package com.zaur.domain.al_quran_cloud.repository

import com.zaur.domain.al_quran_cloud.models.audiofile.AudioFileAqc
import com.zaur.domain.al_quran_cloud.models.audiofile.VerseAudioFileAqc
import com.zaur.domain.base.repository.BaseQuranAudioRepository

interface QuranAudioRepositoryAqc : BaseQuranAudioRepository {
    suspend fun getChapterAudioOfReciter(chapterNumber: Int, reciter: String): AudioFileAqc
    suspend fun getVerseAudioFile(verseKey: String, reciter: String): VerseAudioFileAqc
}