package com.zaur.data.al_quran_aqc.api

import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChaptersAqc
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudiosFileAqc
import com.zaur.domain.al_quran_cloud.models.audiofile.VersesAudioFileAqc
import com.zaur.domain.al_quran_cloud.models.chapter.ChaptersAqc
import com.zaur.domain.al_quran_cloud.models.edition.Editions
import com.zaur.domain.al_quran_cloud.models.edition.Languages
import com.zaur.domain.al_quran_cloud.models.edition.Types
import com.zaur.domain.al_quran_cloud.models.page.QuranPageAqc
import com.zaur.domain.al_quran_cloud.models.translate.TranslationsChapterAqc
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author Zaur
 * @since 2025-05-12
 */

interface QuranApiAqc {

    @GET("/surah")
    suspend fun getAllChapters(): ChaptersAqc.Base

    @GET("/ayah/{verseKey}/{reciter}")
    suspend fun getAyahAudioByKey(
        @Path("verseKey") verseKey: String, @Path("reciter") reciter: String,
    ): VersesAudioFileAqc.Base

    @GET("/surah/{chapterNumber}/{reciter}")
    suspend fun getChapterAudioOfReciter(
        @Path("chapterNumber") chapterNumber: Int, @Path("reciter") reciter: String,
    ): ChapterAudiosFileAqc.Base

    @GET("/surah/{chapterNumber}")
    suspend fun getArabicChapter(@Path("chapterNumber") chapterNumber: Int): ArabicChaptersAqc.Base

    @GET("/surah/{chapterNumber}/{translator}")
    suspend fun getTranslationForChapter(
        @Path("chapterNumber") chapterNumber: Int,
        @Path("translator") translator: String,
    ): TranslationsChapterAqc.Base

    @GET("/type")
    suspend fun getAllTypes(): Types.Base

    @GET("/edition")
    suspend fun getAllEditions(): Editions.Base

    @GET("/language")
    suspend fun getAllLanguages(): Languages.Base

    @GET("/type/{type}")
    suspend fun getEditionByType(@Path("type") type: String): Editions.Base

    @GET("/language/{language}")
    suspend fun getEditionByLanguage(@Path("language") language: String): Editions.Base

    @GET("/edition?format={format}&language={language}&type={type}")
    suspend fun getEditionByParam(
        @Path("format") format: String,
        @Path("language") language: String,
        @Path("type") type: String,
    ): Editions.Base

    @GET("/page/{page}/quran-uthmani")
    suspend fun getUthmaniPage(@Path("page") page: Int): QuranPageAqc.Base

    @GET("/page/{page}/{translator}")
    suspend fun getTranslatedPage(
        @Path("page") page: Int,
        @Path("translator") translator: String,
    ): QuranPageAqc.Base

}