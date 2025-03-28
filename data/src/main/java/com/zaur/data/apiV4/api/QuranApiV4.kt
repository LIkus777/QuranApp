package com.zaur.data.apiV4.api

import com.zaur.domain.apiV4.models.audiofile.ChapterAudioFileV4
import com.zaur.domain.apiV4.models.audiofile.VerseAudioFileV4
import com.zaur.domain.apiV4.models.chapter.ChapterV4
import com.zaur.domain.apiV4.models.juz.JuzV4
import com.zaur.domain.apiV4.models.recitations.RecitationsV4
import com.zaur.domain.apiV4.models.tafsir.SingleTafsirsV4
import com.zaur.domain.apiV4.models.tafsir.TafsirV4
import com.zaur.domain.apiV4.models.tajweed.VerseUthmanTajweedV4
import com.zaur.domain.apiV4.models.translate.SingleTranslationsV4
import com.zaur.domain.apiV4.models.translate.TranslationV4
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface QuranApiV4 {

    /* Recitation */
    @GET("/resources/recitations")
    suspend fun getRecitations(@Query("language") language: String): List<RecitationsV4>

    @GET("/chapter_recitations/{id}/{chapter_number}")
    suspend fun getChaptersAudioOfReciter(
        @Path("id") reciterId: Int, @Path("chapter_number") chapterNumber: Int
    ): ChapterAudioFileV4

    @GET("/recitations/{recitation_id}/by_ayah/{ayah_key}")
    suspend fun getVerseAudioFile(
        @Path("recitation_id") reciterId: Int, @Path("ayah_key") verseKey: String
    ): VerseAudioFileV4

    /* Tafsir */
    @GET("/tafsirs/{tafsir_id}/by_chapter/{chapter_number}?words=true&fields=text_uthmani")
    suspend fun getTafsirForChapter(
        @Path("tafsir_id") tafsirId: Int, @Path("chapter_number") chapterNumber: Int
    ): SingleTafsirsV4

    @GET("/resources/tafsirs")
    suspend fun getAvailableTafsirs(@Query("language") language: String): List<TafsirV4>

    /* Tajweed */
    @GET("/quran/verses/uthmani_tajweed")
    suspend fun getUthmanTajweedsForChapter(@Query("chapter_number") chapterNumber: Int): List<VerseUthmanTajweedV4>

    /* Text */
    @GET("/chapters")
    suspend fun getAllChapters(@Query("language") language: String): List<ChapterV4>

    @GET("/chapters/{chapter_number}")
    suspend fun getChapter(
        @Path("chapter_number") chapterNumber: Int, @Query("language") language: String
    ): ChapterV4

    @GET("/juzs")
    suspend fun getAllJuzs(): List<JuzV4>

    /* Translation */
    @GET("/quran/translations/{translation_id}")
    suspend fun getTranslationForChapter(@Path("translation_id") translationId: Int): SingleTranslationsV4

    @GET("/resources/translations")
    suspend fun getAvailableTranslations(@Query("language") language: String): List<TranslationV4>
}