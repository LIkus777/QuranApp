package com.zaur.data.repository_impl

import com.zaur.data.api.QuranApi
import com.zaur.domain.models.audiofile.ChaptersAudioFile
import com.zaur.domain.models.audiofile.VerseAudioFile
import com.zaur.domain.models.recitations.Recitations
import com.zaur.domain.repository.QuranAudioRepository

class QuranAudioRepositoryImpl(
    private val quranApi: QuranApi
) : QuranAudioRepository {
    override suspend fun getRecitations(language: String): List<Recitations> {
        TODO("Not yet implemented")
    }

    override suspend fun getChaptersAudioOfReciter(
        reciterId: Int,
        chapterNumber: Int
    ): ChaptersAudioFile {
        TODO("Not yet implemented")
    }

    override suspend fun getVerseAudioFile(
        reciterId: Int,
        verseKey: String
    ): VerseAudioFile {
        TODO("Not yet implemented")
    }
}