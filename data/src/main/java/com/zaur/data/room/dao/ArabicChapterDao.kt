package com.zaur.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zaur.data.room.models.ArabicChapterEntity

@Dao
interface ArabicChapterDao {

    @Query("SELECT * FROM arabic_chapters")
    fun getAll(): List<ArabicChapterEntity>

    @Query("SELECT * FROM arabic_chapters WHERE number=:chapterNumber")
    fun getArabicChapter(chapterNumber: Int): ArabicChapterEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(chapters: List<ArabicChapterEntity>)

}
