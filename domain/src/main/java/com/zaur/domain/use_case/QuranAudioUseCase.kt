package com.zaur.domain.use_case

import com.zaur.domain.models.audiofile.ChaptersAudioFile
import com.zaur.domain.models.audiofile.VerseAudioFile
import com.zaur.domain.models.recitations.Recitations
import com.zaur.domain.repository.QuranAudioRepository

class QuranAudioUseCase(
    private val quranAudioRepository: QuranAudioRepository
) {
    suspend fun getRecitations(language: String): List<Recitations> =
        quranAudioRepository.getRecitations(language)

    suspend fun getChaptersAudioOfReciter(reciterId: Int, chapterNumber: Int): ChaptersAudioFile =
        quranAudioRepository.getChaptersAudioOfReciter(reciterId, chapterNumber)

    suspend fun getVerseAudioFile(reciterId: Int, verseKey: String): VerseAudioFile =
        quranAudioRepository.getVerseAudioFile(reciterId, verseKey)
}