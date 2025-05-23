package com.zaur.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zaur.data.room.models.ChapterAudioEntity

/**
* @author Zaur
* @since 2025-05-12
*/

@Dao
interface ChapterAudioDao {

    @Query("SELECT * FROM chapter_audio")
    fun getAll(): List<ChapterAudioEntity.Base>

    @Query("SELECT * FROM chapter_audio WHERE number=:chapterNumber AND reciter=:reciter")
    fun getChapterAudioOfReciter(
        chapterNumber: Int, reciter: String,
    ): ChapterAudioEntity.Base

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(chaptersAudio: List<ChapterAudioEntity.Base>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChapterAudio(chapter: ChapterAudioEntity.Base)

}
