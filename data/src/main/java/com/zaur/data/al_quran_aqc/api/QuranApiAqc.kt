package com.zaur.data.al_quran_aqc.api

import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChaptersAqc
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudiosFileAqc
import com.zaur.domain.al_quran_cloud.models.audiofile.VersesAudioFileAqc
import com.zaur.domain.al_quran_cloud.models.chapter.ChaptersAqc
import com.zaur.domain.al_quran_cloud.models.translate.TranslationsChapterAqc
import retrofit2.http.GET
import retrofit2.http.Path

interface QuranApiAqc {

    @GET("/surah/{chapterNumber}/{reciter}")
    suspend fun getChapterAudioOfReciter(
        @Path("chapterNumber") chapterNumber: Int, @Path("reciter") reciter: String
    ): ChapterAudiosFileAqc

    @GET("/ayah/{verseKey}/{reciter}")
    suspend fun getVerseAudioFile(
        @Path("verseKey") verseKey: String, @Path("reciter") reciter: String
    ): VersesAudioFileAqc

    @GET("/surah")
    suspend fun getAllChapters(): ChaptersAqc

    @GET("/surah/{chapterNumber}")
    suspend fun getArabicChapter(@Path("chapterNumber") chapterNumber: Int): ArabicChaptersAqc

    @GET("/surah/{chapterNumber}/{translator}")
    suspend fun getTranslationForChapter(
        @Path("chapterNumber") chapterNumber: Int,
        @Path("translator") translator: String
    ): TranslationsChapterAqc

}