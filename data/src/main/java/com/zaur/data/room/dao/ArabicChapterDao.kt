package com.zaur.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zaur.data.room.models.ArabicChapterEntity

/**
* @author Zaur
* @since 2025-05-12
*/

@Dao
interface ArabicChapterDao {

    @Query("SELECT * FROM arabic_chapters")
    fun getAll(): List<ArabicChapterEntity.Base>

    @Query("SELECT * FROM arabic_chapters WHERE number=:chapterNumber")
    fun getArabicChapter(chapterNumber: Int): ArabicChapterEntity.Base

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(chapters: List<ArabicChapterEntity.Base>)
}
