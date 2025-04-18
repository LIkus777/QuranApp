package com.zaur.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.zaur.data.room.models.VerseAudioEntity

@Dao
interface VerseAudioDao {

    @Query("SELECT * FROM verse_audio")
    fun getAll(): List<VerseAudioEntity>

    /*@Query("SELECT * FROM verse_audio WHERE verseKey=:verseKey AND reciter=:reciter")
    fun getVerseAudioFile(
        verseKey: String, reciter: String,
    ): VerseAudioEntity*/

    //todo пока не делаю

}
