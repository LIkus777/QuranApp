package com.zaur.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zaur.data.room.models.TranslationEntity
import com.zaur.domain.al_quran_cloud.models.translate.TranslationsChapterAqc
import retrofit2.http.Path

/**
* @author Zaur
* @since 2025-05-12
*/

@Dao
interface TranslationChapterDao {

    @Query("SELECT * FROM translation_chapter")
    fun getAll(): List<TranslationEntity.Base>

    @Query("SELECT * FROM translation_chapter WHERE number=:chapterNumber AND translator=:translator")
    fun getTranslationForChapter(
        chapterNumber: Int,
        translator: String
    ): TranslationEntity.Base

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(chapters: List<TranslationEntity.Base>)

}