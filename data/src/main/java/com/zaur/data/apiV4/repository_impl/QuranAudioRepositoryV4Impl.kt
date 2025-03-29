package com.zaur.data.apiV4.repository_impl

import com.zaur.data.apiV4.api.QuranApiV4
import com.zaur.domain.apiV4.models.audiofile.ChapterAudioFileV4
import com.zaur.domain.apiV4.models.audiofile.VerseAudioFileV4
import com.zaur.domain.apiV4.models.recitations.RecitationsV4
import com.zaur.domain.apiV4.repository.QuranAudioRepositoryV4

class QuranAudioRepositoryV4Impl(
    private val quranApiV4: QuranApiV4
) : QuranAudioRepositoryV4 {
    override suspend fun getRecitations(language: String): List<RecitationsV4> {
        return quranApiV4.getRecitations(language)
    }

    override suspend fun getChapterAudioOfReciter(
        reciterId: Int,
        chapterNumber: Int
    ): ChapterAudioFileV4 {
        return quranApiV4.getChaptersAudioOfReciter(reciterId, chapterNumber)
    }

    override suspend fun getVerseAudioFile(
        reciterId: Int,
        verseKey: String
    ): VerseAudioFileV4 {
        return quranApiV4.getVerseAudioFile(reciterId, verseKey)
    }
}