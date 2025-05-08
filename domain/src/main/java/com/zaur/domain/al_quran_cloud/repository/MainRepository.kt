package com.zaur.domain.al_quran_cloud.repository

import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChapter
import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChaptersAqc
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudioFile
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudiosFileAqc
import com.zaur.domain.al_quran_cloud.models.audiofile.VerseAudioAqc
import com.zaur.domain.al_quran_cloud.models.chapter.ChapterAqc
import com.zaur.domain.al_quran_cloud.models.chapter.ChaptersAqc
import com.zaur.domain.al_quran_cloud.models.translate.TranslationAqc
import com.zaur.domain.al_quran_cloud.models.translate.TranslationsChapterAqc

interface MainRepository {
    interface Load {
        suspend fun loadChapters(): List<ChapterAqc.Base>
        suspend fun loadChaptersArabic(chaptersNumbers: IntRange): List<ArabicChapter.Base>
        suspend fun loadVersesAudio(chaptersNumbers: IntRange, reciter: String): List<VerseAudioAqc.Base>
        suspend fun loadChaptersAudio(chaptersNumbers: IntRange, reciter: String): List<ChapterAudioFile.Base>
        suspend fun loadChaptersTranslate(chaptersNumbers: IntRange, translator: String): List<TranslationAqc.Base>
    }

    interface Save {
        suspend fun saveChapters(chaptersAqc: List<ChapterAqc.Base>)
        suspend fun saveVersesAudio(versesAudio: List<VerseAudioAqc.Base>)
        suspend fun saveChaptersArabic(chaptersArabic: List<ArabicChapter.Base>)
        suspend fun saveChaptersAudio(chaptersAudio: List<ChapterAudioFile.Base>)
        suspend fun saveChaptersTranslate(chaptersTranslate: List<TranslationAqc.Base>)
    }
}
