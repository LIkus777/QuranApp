package com.zaur.domain.repository

import com.zaur.domain.models.audiofile.ChaptersAudioFile
import com.zaur.domain.models.recitations.Recitations
import com.zaur.domain.models.audiofile.VerseAudioFile

interface QuranAudioRepository {
    suspend fun getRecitations(language: String): List<Recitations>
    suspend fun getChaptersAudioOfReciter(reciterId: Int, chapterNumber: Int): ChaptersAudioFile
    suspend fun getVerseAudioFile(reciterId: Int, verseKey: String): VerseAudioFile
}