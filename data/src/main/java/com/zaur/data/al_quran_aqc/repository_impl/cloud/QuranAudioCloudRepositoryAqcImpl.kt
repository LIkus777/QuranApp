package com.zaur.data.al_quran_aqc.repository_impl.cloud

import com.zaur.data.al_quran_aqc.api.QuranApiAqc
import com.zaur.data.downloader.AudioDownloader
import com.zaur.domain.al_quran_cloud.models.audiofile.CacheAudio
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudioFile
import com.zaur.domain.al_quran_cloud.models.audiofile.VerseAudioAqc
import com.zaur.domain.al_quran_cloud.repository.QuranAudioRepositoryAqc

class QuranAudioCloudRepositoryAqcImpl(
    private val quranApiAqc: QuranApiAqc,
    private val audioDownloader: AudioDownloader,
) : QuranAudioRepositoryAqc.Cloud {

    override suspend fun downloadToCache(chapterNumber: Int, reciter: String): List<CacheAudio.Base> {
        val cachedAudios: MutableList<CacheAudio.Base> = mutableListOf()
        val surahAudio = getChapterAudioOfReciterCloud(chapterNumber, reciter)

        surahAudio.ayahs().forEach { ayah ->
            val file = audioDownloader.downloadToCache(
                ayah.audio(), "surah_${chapterNumber}_${ayah.numberInSurah()}.mp3"
            )
            cachedAudios.add(
                CacheAudio.Base(
                    chapterNumber = chapterNumber,
                    verseNumber = ayah.numberInSurah().toInt(),
                    path = file.absolutePath
                )
            )
        }

        return cachedAudios.toList()
    }

    override suspend fun getChapterAudioOfReciterCloud(
        chapterNumber: Int,
        reciter: String,
    ): ChapterAudioFile.Base =
        quranApiAqc.getChapterAudioOfReciter(chapterNumber, reciter).chapterAudio()

    override suspend fun getAyahAudioByKeyCloud(
        verseKey: String,
        reciter: String,
    ): VerseAudioAqc.Base {
        return quranApiAqc.getAyahAudioByKey(verseKey, reciter).verseAudio()
    }
}