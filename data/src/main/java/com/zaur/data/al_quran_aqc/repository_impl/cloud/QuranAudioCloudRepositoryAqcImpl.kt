package com.zaur.data.al_quran_aqc.repository_impl.cloud

import com.zaur.data.al_quran_aqc.api.QuranApiAqc
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudioFile
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudiosFileAqc
import com.zaur.domain.al_quran_cloud.models.audiofile.VerseAudioAqc
import com.zaur.domain.al_quran_cloud.models.audiofile.VersesAudioFileAqc
import com.zaur.domain.al_quran_cloud.repository.QuranAudioRepositoryAqc

class QuranAudioCloudRepositoryAqcImpl(
    private val quranApiAqc: QuranApiAqc,
) : QuranAudioRepositoryAqc.Cloud {
    override suspend fun getChapterAudioOfReciterCloud(
        chapterNumber: Int,
        reciter: String,
    ): ChapterAudioFile = quranApiAqc.getChapterAudioOfReciter(chapterNumber, reciter).map(
        ChapterAudiosFileAqc.Mapper.ChapterAudio()
    )

    override suspend fun getAyahAudioByKeyCloud(
        verseKey: String,
        reciter: String,
    ): VerseAudioAqc = quranApiAqc.getAyahAudioByKey(verseKey, reciter).map(VersesAudioFileAqc.Mapper.VerseAudio())
}