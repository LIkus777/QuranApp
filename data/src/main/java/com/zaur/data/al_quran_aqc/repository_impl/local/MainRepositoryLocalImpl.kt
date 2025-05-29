package com.zaur.data.al_quran_aqc.repository_impl.local

import android.util.Log
import com.zaur.data.downloader.AudioDownloader
import com.zaur.data.room.dao.ArabicChapterDao
import com.zaur.data.room.dao.ChapterAudioDao
import com.zaur.data.room.dao.ChapterDao
import com.zaur.data.room.dao.TranslationChapterDao
import com.zaur.data.room.dao.VerseAudioDao
import com.zaur.data.room.models.mappers.arabic.ArabicMapper
import com.zaur.data.room.models.mappers.audiofile.ChapterAudioMapper
import com.zaur.data.room.models.mappers.audiofile.VerseAudioMapper
import com.zaur.data.room.models.mappers.chapter.ChapterMapper
import com.zaur.data.room.models.mappers.translate.TranslationMapper
import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChapter
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudioFile
import com.zaur.domain.al_quran_cloud.models.audiofile.VerseAudio
import com.zaur.domain.al_quran_cloud.models.chapter.ChapterAqc
import com.zaur.domain.al_quran_cloud.models.translate.Translation
import com.zaur.domain.al_quran_cloud.repository.MainRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author Zaur
 * @since 2025-05-12
 */

class MainRepositoryLocalImpl(
    private val audioDownloader: AudioDownloader,
    private val chapterMapper: ChapterMapper,
    private val chapterAudioMapper: ChapterAudioMapper,
    private val verseAudioMapper: VerseAudioMapper,
    private val translationMapper: TranslationMapper,
    private val arabicMapper: ArabicMapper,
    private val chapterDao: ChapterDao,
    private val verseAudioDao: VerseAudioDao,
    private val chapterAudioDao: ChapterAudioDao,
    private val arabicChapterDao: ArabicChapterDao,
    private val translationChapterDao: TranslationChapterDao,
) : MainRepository.Local {

    override suspend fun saveChapters(chaptersAqc: List<ChapterAqc.Base>) {
        val chaptersEntities = chaptersAqc.map { chapterMapper.toData(it) }
        chapterDao.add(chaptersEntities)
    }

    override suspend fun saveVersesAudio(versesAudio: List<VerseAudio.Base>) {
        // Сохраняем аяты
        verseAudioDao.insertAll(versesAudio.map { verseAudioMapper.toData(it) })
    }

    override suspend fun saveChaptersArabic(chaptersArabic: List<ArabicChapter.Base>) {
        arabicChapterDao.add(chaptersArabic.map { arabicMapper.toData(it) })
    }

    override suspend fun saveChaptersAudio(chaptersAudio: List<ChapterAudioFile.Base>) {
        CoroutineScope(Dispatchers.IO).launch {
            Log.i("TAG", "saveChaptersAudio: chaptersAudio $chaptersAudio")
            val chaptersData =
                chaptersAudio.map { chapterAudioMapper.toData(it) } // ChapterAudioEntity
            Log.i("TAG", "saveChaptersAudio: chaptersData $chaptersData")
            // Сохраняем главы
            chapterAudioDao.add(chaptersData)
            chaptersData.forEach {
                audioDownloader.downloadAndSaveChapter(it)
            }
        }
    }

    override suspend fun saveChaptersTranslate(chaptersTranslate: List<Translation.Base>) {
        translationChapterDao.add(chaptersTranslate.map { translationMapper.toData(it) })
    }
}