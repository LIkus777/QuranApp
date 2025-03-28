package com.zaur.domain.apiV4.use_case

import com.zaur.domain.apiV4.models.audiofile.ChapterAudioFileV4
import com.zaur.domain.apiV4.models.audiofile.VerseAudioFileV4
import com.zaur.domain.apiV4.models.recitations.RecitationsV4
import com.zaur.domain.apiV4.repository.QuranAudioRepositoryV4

class QuranAudioUseCaseV4(
    private val quranAudioRepositoryV4: QuranAudioRepositoryV4
) {
    suspend fun getRecitations(language: String): List<RecitationsV4> =
        quranAudioRepositoryV4.getRecitations(language)

    suspend fun getChaptersAudioOfReciter(reciterId: Int, chapterNumber: Int): ChapterAudioFileV4 =
        quranAudioRepositoryV4.getChapterAudioOfReciter(reciterId, chapterNumber)

    suspend fun getVerseAudioFile(reciterId: Int, verseKey: String): VerseAudioFileV4 =
        quranAudioRepositoryV4.getVerseAudioFile(reciterId, verseKey)
}