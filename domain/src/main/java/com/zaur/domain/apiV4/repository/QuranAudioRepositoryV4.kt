package com.zaur.domain.apiV4.repository

import com.zaur.domain.apiV4.models.audiofile.ChapterAudioFileV4
import com.zaur.domain.apiV4.models.audiofile.VerseAudioFileV4
import com.zaur.domain.apiV4.models.recitations.RecitationsV4
import com.zaur.domain.base.repository.BaseQuranAudioRepository

interface QuranAudioRepositoryV4 : BaseQuranAudioRepository {
    suspend fun getRecitations(language: String): List<RecitationsV4>
    suspend fun getChapterAudioOfReciter(reciterId: Int, chapterNumber: Int): ChapterAudioFileV4
    suspend fun getVerseAudioFile(reciterId: Int, verseKey: String): VerseAudioFileV4
}