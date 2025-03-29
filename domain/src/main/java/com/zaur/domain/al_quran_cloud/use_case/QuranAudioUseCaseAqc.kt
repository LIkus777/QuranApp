package com.zaur.domain.al_quran_cloud.use_case

import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudiosFileAqc
import com.zaur.domain.al_quran_cloud.models.audiofile.VersesAudioFileAqc
import com.zaur.domain.al_quran_cloud.repository.QuranAudioRepositoryAqc

data class QuranAudioUseCaseAqc(
    private val quranAudioRepositoryAqc: QuranAudioRepositoryAqc
) {
    suspend fun getChapterAudioOfReciter(chapterNumber: Int, reciter: String): ChapterAudiosFileAqc =
        quranAudioRepositoryAqc.getChapterAudioOfReciter(chapterNumber, reciter)

    suspend fun getVerseAudioFile(verseKey: String, reciter: String): VersesAudioFileAqc =
        quranAudioRepositoryAqc.getVerseAudioFile(verseKey, reciter)
}
