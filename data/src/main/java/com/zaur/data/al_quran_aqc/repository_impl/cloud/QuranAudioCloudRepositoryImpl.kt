package com.zaur.data.al_quran_aqc.repository_impl.cloud

import com.zaur.data.al_quran_aqc.api.QuranApiAqc
import com.zaur.data.downloader.AudioDownloader
import com.zaur.data.network.retryWithBackoff
import com.zaur.domain.al_quran_cloud.models.audiofile.CacheAudio
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudioFile
import com.zaur.domain.al_quran_cloud.models.audiofile.VerseAudioAqc
import com.zaur.domain.al_quran_cloud.repository.QuranAudioRepository

/**
 * @author Zaur
 * @since 2025-05-12
 */

class QuranAudioCloudRepositoryImpl(
    private val quranApiAqc: QuranApiAqc,
    private val audioDownloader: AudioDownloader,
) : QuranAudioRepository.Cloud {

    override suspend fun downloadToCache(
        chapterNumber: Int,
        reciter: String,
    ): List<CacheAudio.Base> {
        val cachedAudios: MutableList<CacheAudio.Base> = mutableListOf()
        val surahAudio = getChapterAudioOfReciterCloud(chapterNumber, reciter)

        retryWithBackoff {
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
        }

        return cachedAudios.toList()
    }

    override suspend fun getChapterAudioOfReciterCloud(
        chapterNumber: Int,
        reciter: String,
    ): ChapterAudioFile.Base = retryWithBackoff {
        quranApiAqc.getChapterAudioOfReciter(chapterNumber, reciter).chapterAudio()
    }


    override suspend fun getAyahAudioByKeyCloud(
        verseKey: String,
        reciter: String,
    ): VerseAudioAqc.Base =
        retryWithBackoff { quranApiAqc.getAyahAudioByKey(verseKey, reciter).verseAudio() }
}