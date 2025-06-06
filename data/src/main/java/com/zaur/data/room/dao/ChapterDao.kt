package com.zaur.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zaur.data.room.models.ChapterEntity

/**
* @author Zaur
* @since 2025-05-12
*/

@Dao
interface ChapterDao {

    @Query("SELECT * FROM chapters")
    fun getAllChapters(): List<ChapterEntity.Base>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(chapters: List<ChapterEntity.Base>)

}
