package com.zaur.data.al_quran_aqc.api

import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChaptersAqc
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudiosFileAqc
import com.zaur.domain.al_quran_cloud.models.audiofile.VersesAudioFileAqc
import com.zaur.domain.al_quran_cloud.models.chapter.ChaptersAqc
import com.zaur.domain.al_quran_cloud.models.edition.Editions
import com.zaur.domain.al_quran_cloud.models.edition.Languages
import com.zaur.domain.al_quran_cloud.models.edition.Types
import com.zaur.domain.al_quran_cloud.models.translate.TranslationsChapterAqc
import retrofit2.http.GET
import retrofit2.http.Path

interface QuranApiAqc {

    @GET("/surah")
    suspend fun getAllChapters(): ChaptersAqc

    @GET("/ayah/{verseKey}/{reciter}")
    suspend fun getAyahAudioByKey(
        @Path("verseKey") verseKey: String, @Path("reciter") reciter: String,
    ): VersesAudioFileAqc

    @GET("/surah/{chapterNumber}/{reciter}")
    suspend fun getChapterAudioOfReciter(
        @Path("chapterNumber") chapterNumber: Int, @Path("reciter") reciter: String,
    ): ChapterAudiosFileAqc

    @GET("/surah/{chapterNumber}")
    suspend fun getArabicChapter(@Path("chapterNumber") chapterNumber: Int): ArabicChaptersAqc

    @GET("/surah/{chapterNumber}/{translator}")
    suspend fun getTranslationForChapter(
        @Path("chapterNumber") chapterNumber: Int,
        @Path("translator") translator: String,
    ): TranslationsChapterAqc

    @GET("/type")
    suspend fun getAllTypes(): Types

    @GET("/edition")
    suspend fun getAllEditions(): Editions

    @GET("/language")
    suspend fun getAllLanguages(): Languages

    @GET("/type/{type}")
    suspend fun getEditionByType(@Path("type") type: String): Editions

    @GET("/language/{language}")
    suspend fun getEditionByLanguage(@Path("language") language: String): Editions

    @GET("/edition?format={format}&language={language}&type={type}")
    suspend fun getEditionByParam(
        @Path("format") format: String,
        @Path("language") language: String,
        @Path("type") type: String,
    ): Editions

}