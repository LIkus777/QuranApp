package com.zaur.data.api

import com.zaur.domain.models.audiofile.ChaptersAudioFile
import com.zaur.domain.models.audiofile.VerseAudioFile
import com.zaur.domain.models.chapter.Chapter
import com.zaur.domain.models.juz.Juz
import com.zaur.domain.models.recitations.Recitations
import com.zaur.domain.models.tafsir.SingleTafsirs
import com.zaur.domain.models.tafsir.Tafsir
import com.zaur.domain.models.tajweed.VerseUthmanTajweed
import com.zaur.domain.models.translate.SingleTranslations
import com.zaur.domain.models.translate.Translation
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
    @GET("/tafsirs/{tafsir_id}/by_chapter/{chapter_number}?words=true&fields=text_uthmani")
    suspend fun getTafsirForChapter(
        @Path("tafsir_id") tafsirId: Int,
        @Path("chapter_number") chapterNumber: Int
    ): SingleTafsirs

    @GET("/resources/tafsirs")
    suspend fun getAvailableTafsirs(@Query("language") language: String): List<Tafsir>

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
    @GET("/quran/translations/{translation_id}")
    suspend fun getTranslationForChapter(@Path("translation_id") translationId: Int): SingleTranslations

    @GET("/resources/translations")
    suspend fun getAvailableTranslations(@Query("language") language: String): List<Translation>
}