package com.zaur.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zaur.data.room.models.ChapterAudioEntity

@Dao
interface ChapterAudioDao {

    @Query("SELECT * FROM chapter_audio")
    fun getAll(): List<ChapterAudioEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(chaptersAudio: List<ChapterAudioEntity>)

}
