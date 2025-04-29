package com.zaur.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zaur.data.room.models.VerseAudioEntity

@Dao
interface VerseAudioDao {

    @Query("""SELECT * FROM verse_audio WHERE surah_number = :chapter AND verseNumber = :verse AND reciter = :reciter""")
    suspend fun getAyahAudio(chapter: Long, verse: Long, reciter: String): VerseAudioEntity.Base

    suspend fun getAyahAudioByKey(verseKey: String, reciter: String): VerseAudioEntity.Base {
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
