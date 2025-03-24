package com.zaur.data.api

import com.zaur.domain.models.audiofile.ChaptersAudioFile
import com.zaur.domain.models.audiofile.VerseAudioFile
import com.zaur.domain.models.chapter.Chapter
import com.zaur.domain.models.juz.Juz
import com.zaur.domain.models.recitations.Recitations
import com.zaur.domain.models.tafsir.SingleTafsirs
import com.zaur.domain.models.tajweed.VerseUthmanTajweed
import com.zaur.domain.models.translate.SingleTranslation
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface QuranApi {

    /* Recitation */

    @GET("/resources/recitations")
    suspend fun getRecitations(@Query("language") language: String): List<Recitations>

    @GET("/chapter_recitations/{id}/{chapter_number}")
    suspend fun getChaptersAudioOfReciter(
        @Path("id") reciterId: Int, @Path("chapter_number") chapterNumber: Int
    ): ChaptersAudioFile

    @GET("/recitations/{recitation_id}/by_ayah/{ayah_key}")
    suspend fun getVerseAudioFile(
        @Path("recitation_id") reciterId: Int, @Path("ayah_key") verseKey: String
    ): VerseAudioFile

    /* Tafsir */
    @GET("") //todo
    suspend fun getTafsirForChapter(@Path("tafsir_id") chapterNumber: Int): SingleTafsirs

    /* Tajweed */
    @GET("/quran/verses/uthmani_tajweed")
    suspend fun getUthmanTajweedsForChapter(@Query("chapter_number") chapterNumber: Int): List<VerseUthmanTajweed>

    /* Text */
    @GET("/chapters")
    suspend fun getAllChapters(@Query("language") language: String): List<Chapter>

    @GET("/chapters/{chapter_number}")
    suspend fun getChapter(
        @Path("chapter_number") chapterNumber: Int, @Query("language") language: String
    ): Chapter

    @GET("/juzs")
    suspend fun getAllJuzs(): List<Juz>

    /* Translation */
    fun getTranslationForChapter(translationId: Int): SingleTranslation
}