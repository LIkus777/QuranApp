package com.zaur.data.al_quran_aqc.repository_impl.local

import android.util.Log
import com.zaur.data.downloader.AudioDownloader
import com.zaur.data.room.dao.ArabicChapterDao
import com.zaur.data.room.dao.ChapterAudioDao
import com.zaur.data.room.dao.ChapterDao
import com.zaur.data.room.dao.TranslationChapterDao
import com.zaur.data.room.dao.VerseAudioDao
import com.zaur.data.room.models.mappers.toData
import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChapter
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudioFile
import com.zaur.domain.al_quran_cloud.models.audiofile.VerseAudioAqc
import com.zaur.domain.al_quran_cloud.models.audiofile.VersesAudioFileAqc
import com.zaur.domain.al_quran_cloud.models.chapter.ChapterAqc
import com.zaur.domain.al_quran_cloud.models.translate.TranslationAqc
import com.zaur.domain.al_quran_cloud.repository.MainRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainRepositorySaveImpl(
    private val audioDownloader: AudioDownloader,
    private val chapterDao: ChapterDao,
    private val verseAudioDao: VerseAudioDao,
    private val chapterAudioDao: ChapterAudioDao,
    private val arabicChapterDao: ArabicChapterDao,
    private val translationChapterDao: TranslationChapterDao,
) : MainRepository.Save {
    override suspend fun saveChapters(chaptersAqc: List<ChapterAqc>) {
        chapterDao.add(chaptersAqc.map { it.toData() })
    }

    override suspend fun saveVersesAudio(versesAudio: List<VerseAudioAqc>) {
        // Сохраняем аяты
        val ayahs = versesAudio.map { verse ->
            verse.copy(
                surah = verse.surah.copy(), reciter = verse.reciter
            )
        }
        verseAudioDao.insertAll(ayahs.map { it.toData() })
    }


    override suspend fun saveChaptersArabic(chaptersArabic: List<ArabicChapter>) {
        arabicChapterDao.add(chaptersArabic.map { it.toData() })
    }

    override suspend fun saveChaptersAudio(chaptersAudio: List<ChapterAudioFile>) {
        CoroutineScope(Dispatchers.IO).launch {
            Log.i("TAG", "saveChaptersAudio: chaptersAudio $chaptersAudio")
            val chaptersData = chaptersAudio.map { it.toData() } // ChapterAudioEntity
            Log.i("TAG", "saveChaptersAudio: chaptersData $chaptersData")
            // Сохраняем главы
            chapterAudioDao.add(chaptersData)
            chaptersData.forEach {
                audioDownloader.downloadAndCacheChapter(it)
            }
        }
    }

    override suspend fun saveChaptersTranslate(chaptersTranslate: List<TranslationAqc>) {
        translationChapterDao.add(chaptersTranslate.map { it.toData() })
    }
}