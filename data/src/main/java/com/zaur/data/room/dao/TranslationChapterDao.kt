package com.zaur.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zaur.data.room.models.TranslationEntity

@Dao
interface TranslationChapterDao {

    @Query("SELECT * FROM translation_chapter")
    fun getAll(): List<TranslationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(chapters: List<TranslationEntity>)

}