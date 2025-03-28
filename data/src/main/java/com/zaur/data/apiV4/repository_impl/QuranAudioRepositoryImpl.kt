package com.zaur.data.apiV4.repository_impl

import com.zaur.data.apiV4.api.QuranApiV4
import com.zaur.domain.models.audiofile.ChaptersAudioFile
import com.zaur.domain.models.audiofile.VerseAudioFile
import com.zaur.domain.models.recitations.Recitations
import com.zaur.domain.repository.QuranAudioRepository

class QuranAudioRepositoryImpl(
    private val quranApiV4: QuranApiV4
) : QuranAudioRepository {
    override suspend fun getRecitations(language: String): List<Recitations> {
        return quranApiV4.getRecitations(language)
    }

    override suspend fun getChaptersAudioOfReciter(
        reciterId: Int,
        chapterNumber: Int
    ): ChaptersAudioFile {
        return quranApiV4.getChaptersAudioOfReciter(reciterId, chapterNumber)
    }

    override suspend fun getVerseAudioFile(
        reciterId: Int,
        verseKey: String
    ): VerseAudioFile {
        return quranApiV4.getVerseAudioFile(reciterId, verseKey)
    }
}