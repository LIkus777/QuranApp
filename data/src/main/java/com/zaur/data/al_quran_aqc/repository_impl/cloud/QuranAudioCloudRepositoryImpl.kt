package com.zaur.data.al_quran_aqc.repository_impl.cloud

import android.util.Log
import com.zaur.data.al_quran_aqc.api.QuranApiAqc
import com.zaur.data.downloader.AudioDownloader
import com.zaur.data.network.retryWithBackoff
import com.zaur.domain.al_quran_cloud.models.audiofile.CacheAudio
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudioFile
import com.zaur.domain.al_quran_cloud.models.audiofile.VerseAudio
import com.zaur.domain.al_quran_cloud.repository.QuranAudioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit

/**
 * @author Zaur
 * @since 2025-05-12
 */

class QuranAudioCloudRepositoryImpl(
    private val quranApiAqc: QuranApiAqc,
    private val audioDownloader: AudioDownloader,
) : QuranAudioRepository.Cloud {

    private var chapterAudio: ChapterAudioFile = ChapterAudioFile.Empty

    override suspend fun downloadToCache(
        chapterNumber: Int,
        reciter: String,
    ): List<CacheAudio.Base> = coroutineScope {
        val surahAudio = if (chapterAudio is ChapterAudioFile.Base) chapterAudio
        else getChapterAudioOfReciterCloud(chapterNumber, reciter)
        val semaphore = Semaphore(4) // Ограничим до 4 одновременных загрузок

        val deferreds = surahAudio.ayahs().mapNotNull { ayah ->
            Log.d("AudioDownloader", "аят ${ayah}")
            val audioUrl = ayah.audio()
            if (audioUrl.isNullOrBlank()) {
                Log.w("AudioDownloader", "Пропускаем аят ${ayah.numberInSurah()} с пустым URL аудио")
                null // не будем загружать этот аят
            } else {
                async(Dispatchers.IO) {
                    semaphore.withPermit {
                        val file = audioDownloader.downloadToCache(
                            audioUrl, "surah_${chapterNumber}_${ayah.numberInSurah()}_${reciter}.mp3"
                        )
                        CacheAudio.Base(
                            chapterNumber = chapterNumber,
                            verseNumber = ayah.numberInSurah().toInt(),
                            path = file.absolutePath
                        )
                    }
                }
            }
        }

        deferreds.awaitAll()
    }

    override suspend fun getChapterAudioOfReciterCloud(
        chapterNumber: Int,
        reciter: String,
    ): ChapterAudioFile.Base = retryWithBackoff {
        chapterAudio = quranApiAqc.getChapterAudioOfReciter(chapterNumber, reciter).chapterAudio()
        chapterAudio as ChapterAudioFile.Base
    }


    override suspend fun getAyahAudioByKeyCloud(
        verseKey: String,
        reciter: String,
    ): VerseAudio.Base =
        retryWithBackoff { quranApiAqc.getAyahAudioByKey(verseKey, reciter).verseAudio() }
}