package com.zaur.domain.al_quran_cloud.repository

import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChapter
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudioFile
import com.zaur.domain.al_quran_cloud.models.audiofile.VerseAudio
import com.zaur.domain.al_quran_cloud.models.chapter.ChapterAqc
import com.zaur.domain.al_quran_cloud.models.translate.Translation

/**
 * @author Zaur
 * @since 2025-05-12
 */

interface MainRepository {
    interface Cloud {
        suspend fun loadChapters(): List<ChapterAqc.Base>
        suspend fun loadChaptersArabic(chaptersNumbers: IntRange): List<ArabicChapter.Base>
        suspend fun loadVersesAudio(
            chaptersNumbers: IntRange,
            reciter: String,
        ): List<VerseAudio.Base>

        suspend fun loadChaptersAudio(
            chaptersNumbers: IntRange,
            reciter: String,
        ): List<ChapterAudioFile.Base>

        suspend fun loadChaptersTranslate(
            chaptersNumbers: IntRange,
            translator: String,
        ): List<Translation.Base>
    }

    interface Local {
        suspend fun saveChapters(chaptersAqc: List<ChapterAqc.Base>)
        suspend fun saveVersesAudio(versesAudio: List<VerseAudio.Base>)
        suspend fun saveChaptersArabic(chaptersArabic: List<ArabicChapter.Base>)
        suspend fun saveChaptersAudio(chaptersAudio: List<ChapterAudioFile.Base>)
        suspend fun saveChaptersTranslate(chaptersTranslate: List<Translation.Base>)
    }
}
