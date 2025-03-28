package com.zaur.data.al_quran_aqc.api

import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChapterAqc
import com.zaur.domain.al_quran_cloud.models.audiofile.AudioFileAqc
import com.zaur.domain.al_quran_cloud.models.audiofile.VerseAudioFileAqc
import com.zaur.domain.al_quran_cloud.models.chapter.ChapterAqc
import com.zaur.domain.al_quran_cloud.models.translate.TranslationChapterAqc
import retrofit2.http.GET
import retrofit2.http.Path

interface QuranApiAqc {

    @GET("")
    suspend fun getChapterAudioOfReciter(chapterNumber: Int, reciter: String): AudioFileAqc

    @GET("")
    suspend fun getVerseAudioFile(verseKey: String, reciter: String): VerseAudioFileAqc

    @GET("")
    suspend fun getAllChapters(translator: String): List<ChapterAqc>

    @GET("surah/{chapterNumber}")
    suspend fun getArabicChapter(@Path("chapterNumber") chapterNumber: Int): ArabicChapterAqc

    @GET("")
    suspend fun getTranslationForChapter(translator: String): TranslationChapterAqc

}