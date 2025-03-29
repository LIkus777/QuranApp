package com.zaur.data.al_quran_aqc.repository_impl

import com.zaur.data.al_quran_aqc.api.QuranApiAqc
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudiosFileAqc
import com.zaur.domain.al_quran_cloud.models.audiofile.VersesAudioFileAqc
import com.zaur.domain.al_quran_cloud.repository.QuranAudioRepositoryAqc

class QuranAudioRepositoryAqcImpl(
    private val quranApiAqc: QuranApiAqc
) : QuranAudioRepositoryAqc {
    override suspend fun getChapterAudioOfReciter(
        chapterNumber: Int, reciter: String
    ): ChapterAudiosFileAqc = quranApiAqc.getChapterAudioOfReciter(chapterNumber, reciter)

    override suspend fun getVerseAudioFile(
        verseKey: String, reciter: String
    ): VersesAudioFileAqc = quranApiAqc.getVerseAudioFile(verseKey, reciter)
}