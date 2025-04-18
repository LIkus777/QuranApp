package com.zaur.domain.al_quran_cloud.repository

import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChapter
import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChaptersAqc
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudioFile
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudiosFileAqc
import com.zaur.domain.al_quran_cloud.models.chapter.ChapterAqc
import com.zaur.domain.al_quran_cloud.models.chapter.ChaptersAqc
import com.zaur.domain.al_quran_cloud.models.translate.TranslationAqc
import com.zaur.domain.al_quran_cloud.models.translate.TranslationsChapterAqc

interface MainRepository {
    interface Load {
        suspend fun loadChapters(): List<ChapterAqc>
        suspend fun loadChaptersArabic(chaptersNumbers: IntRange): List<ArabicChapter>
        suspend fun loadChaptersAudio(chaptersNumbers: IntRange, reciter: String): List<ChapterAudioFile>
        suspend fun loadChaptersTranslate(chaptersNumbers: IntRange, translator: String): List<TranslationAqc>
    }

    interface Save {
        suspend fun saveChapters(chaptersAqc: List<ChapterAqc>)
        suspend fun saveChaptersArabic(chaptersArabic: List<ArabicChapter>)
        suspend fun saveChaptersAudio(chaptersAudio: List<ChapterAudioFile>)
        suspend fun saveChaptersTranslate(chaptersTranslate: List<TranslationAqc>)
    }
}
