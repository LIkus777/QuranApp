package com.zaur.data.room.repository

import com.zaur.data.room.dao.ArabicChapterDao
import com.zaur.data.room.dao.ChapterAudioDao
import com.zaur.data.room.dao.ChapterDao
import com.zaur.data.room.dao.TranslationChapterDao
import com.zaur.data.room.models.mappers.toData
import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChapter
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudioFile
import com.zaur.domain.al_quran_cloud.models.chapter.ChapterAqc
import com.zaur.domain.al_quran_cloud.models.translate.TranslationAqc
import com.zaur.domain.al_quran_cloud.repository.MainRepository

class MainRepositorySaveImpl(
    private val chapterDao: ChapterDao,
    private val chapterAudioDao: ChapterAudioDao,
    private val arabicChapterDao: ArabicChapterDao,
    private val translationChapterDao: TranslationChapterDao,
) : MainRepository.Save {
    override suspend fun saveChapters(chaptersAqc: List<ChapterAqc>) {
        chapterDao.add(chaptersAqc.map { it.toData() })
    }

    override suspend fun saveChaptersAudio(chaptersAudio: List<ChapterAudioFile>) {
        chapterAudioDao.add(chaptersAudio.map { it.toData() })
    }

    override suspend fun saveChaptersArabic(chaptersArabic: List<ArabicChapter>) {
        arabicChapterDao.add(chaptersArabic.map { it.toData() })
    }

    override suspend fun saveChaptersTranslate(chaptersTranslate: List<TranslationAqc>) {
        translationChapterDao.add(chaptersTranslate.map { it.toData() })
    }
}