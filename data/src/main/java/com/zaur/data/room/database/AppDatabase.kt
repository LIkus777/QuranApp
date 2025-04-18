package com.zaur.data.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zaur.data.room.converters.GenericConverters
import com.zaur.data.room.dao.ArabicChapterDao
import com.zaur.data.room.dao.ChapterAudioDao
import com.zaur.data.room.dao.ChapterDao
import com.zaur.data.room.dao.TranslationChapterDao
import com.zaur.data.room.dao.VerseAudioDao
import com.zaur.data.room.models.ArabicChapterEntity
import com.zaur.data.room.models.ChapterAudioEntity
import com.zaur.data.room.models.ChapterEntity
import com.zaur.data.room.models.TranslationEntity
import com.zaur.data.room.models.VerseAudioEntity

@Database(
    entities = [ArabicChapterEntity::class, VerseAudioEntity::class, ChapterAudioEntity::class, ChapterEntity::class, TranslationEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(GenericConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun arabicChapterDao(): ArabicChapterDao
    abstract fun verseAudioDao(): VerseAudioDao
    abstract fun chapterAudioDao(): ChapterAudioDao
    abstract fun chapterDao(): ChapterDao
    abstract fun translationChapterDao(): TranslationChapterDao
}