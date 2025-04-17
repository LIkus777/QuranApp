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
        suspend fun loadChapters(): ChaptersAqc
        suspend fun loadChaptersAudio(): List<ChapterAudiosFileAqc>
        suspend fun loadChaptersArabic(): List<ArabicChaptersAqc>
        suspend fun loadChaptersTranslate(): List<TranslationsChapterAqc>
    }

    interface Save {
        suspend fun saveChapters(chaptersAqc: List<ChapterAqc>)
        suspend fun saveChaptersAudio(chaptersAudio: List<ChapterAudioFile>)
        suspend fun saveChaptersArabic(chaptersArabic: List<ArabicChapter>)
        suspend fun saveChaptersTranslate(chaptersTranslate: List<TranslationAqc>)
    }
}
