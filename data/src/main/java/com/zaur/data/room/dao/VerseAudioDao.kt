package com.zaur.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.zaur.data.room.models.VerseAudioEntity
import com.zaur.data.room.models.VerseAudioWithSurah

/**
* @author Zaur
* @since 2025-05-12
*/

@Dao
interface VerseAudioDao {

    @Transaction
    @Query("SELECT * FROM verse_audio WHERE surahNumber = :chapter AND verseNumber = :verse AND reciter = :reciter")
    suspend fun getAyahAudio(chapter: Long, verse: Long, reciter: String): VerseAudioWithSurah.Base

    suspend fun getAyahAudioByKey(verseKey: String, reciter: String): VerseAudioWithSurah.Base {
        val parts = verseKey.split(":")
        require(parts.size == 2) { "Invalid verseKey format: $verseKey" }

        val chapter = parts[0].toLongOrNull()
            ?: throw IllegalArgumentException("Invalid chapter number in verseKey: $verseKey")
        val verse = parts[1].toLongOrNull()
            ?: throw IllegalArgumentException("Invalid verse number in verseKey: $verseKey")

        return getAyahAudio(chapter, verse, reciter)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(ayahs: List<VerseAudioEntity.Base>)
}
