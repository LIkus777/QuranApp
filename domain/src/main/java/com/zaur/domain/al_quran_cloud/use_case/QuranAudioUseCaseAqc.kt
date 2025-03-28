package com.zaur.domain.al_quran_cloud.use_case

import com.zaur.domain.al_quran_cloud.models.audiofile.AudioFileAqc
import com.zaur.domain.al_quran_cloud.models.audiofile.VerseAudioFileAqc
import com.zaur.domain.al_quran_cloud.repository.QuranAudioRepositoryAqc

data class QuranAudioUseCaseAqc(
    private val quranAudioRepositoryAqc: QuranAudioRepositoryAqc
) {
    suspend fun getChapterAudioOfReciter(chapterNumber: Int, reciter: String): AudioFileAqc =
        quranAudioRepositoryAqc.getChapterAudioOfReciter(chapterNumber, reciter)

    suspend fun getVerseAudioFile(verseKey: String, reciter: String): VerseAudioFileAqc =
        quranAudioRepositoryAqc.getVerseAudioFile(verseKey, reciter)
}
