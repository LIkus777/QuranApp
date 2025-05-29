package com.zaur.data.room.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import androidx.room.TypeConverters
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.zaur.data.room.converters.GenericConverters
import com.zaur.domain.base.SajdaAdapter

/**
* @author Zaur
* @since 2025-05-12
*/

interface VerseAudioWithSurah {

    fun <T> map(mapper: Mapper<T>): T

    class Base(
        @Embedded val verse: VerseAudioEntity.Base,
        @Relation(
            parentColumn = "surahNumber", entityColumn = "number"
        ) val surah: SurahEntity.Base,
    ) : VerseAudioWithSurah {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(verse, surah)
    }

    interface Mapper<T> {
        fun map(
            verse: VerseAudioEntity.Base,
            surah: SurahEntity.Base,
        ): T
    }
}

interface VerseAudioEntity {

    fun <T> map(mapper: Mapper<T>): T

    @Entity(
        tableName = "verse_audio",
        foreignKeys = [ForeignKey(
            entity = SurahEntity.Base::class,
            parentColumns = ["number"],
            childColumns = ["surahNumber"],
            onDelete = ForeignKey.CASCADE
        )],
        indices = [Index("surahNumber")],
        primaryKeys = ["surahNumber", "verseNumber", "reciter"]
    )
    data class Base(
        @ColumnInfo(name = "surahNumber") val surahNumber: Long,
        val verseNumber: Long,
        val reciter: String,
        @SerializedName("audio") val audio: String,
        @SerializedName("audioSecondary") val audioSecondary: List<String>,
        @SerializedName("text") val text: String,
        @TypeConverters(GenericConverters::class) @SerializedName("edition") val edition: EditionVerseEntity.Base,
        @SerializedName("numberInSurah") val numberInSurah: Long,
        @SerializedName("juz") val juz: Long,
        @SerializedName("manzil") val manzil: Long,
        @SerializedName("page") val page: Long,
        @SerializedName("ruku") val ruku: Long,
        @SerializedName("hizbQuarter") val hizbQuarter: Long,
        @JsonAdapter(SajdaAdapter::class) @SerializedName("sajda") val sajda: Boolean,
    ) : VerseAudioEntity {

        override fun <T> map(mapper: Mapper<T>): T = mapper.map(
            surahNumber,
            verseNumber,
            reciter,
            audio,
            audioSecondary,
            text,
            edition,
            numberInSurah,
            juz,
            manzil,
            page,
            ruku,
            hizbQuarter,
            sajda
        )
    }

    // Интерфейс для маппинга VerseAudioEntity.Base
    interface Mapper<T> {
        fun map(
            surahNumber: Long,
            verseNumber: Long,
            reciter: String,
            audio: String,
            audioSecondary: List<String>,
            text: String,
            edition: EditionVerseEntity,
            numberInSurah: Long,
            juz: Long,
            manzil: Long,
            page: Long,
            ruku: Long,
            hizbQuarter: Long,
            sajda: Boolean,
        ): T
    }
}

interface EditionVerseEntity {

    fun <T> map(mapper: Mapper<T>): T

    data class Base(
        @SerializedName("identifier") val identifier: String,
        @SerializedName("language") val language: String,
        @SerializedName("name") val name: String,
        @SerializedName("englishName") val englishName: String,
        @SerializedName("format") val format: String,
        @SerializedName("type") val type: String,
        @SerializedName("direction") val direction: String? = null,
    ) : EditionVerseEntity {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(
            identifier, language, name, englishName, format, type, direction
        )
    }

    interface Mapper<T> {
        fun map(
            identifier: String,
            language: String,
            name: String,
            englishName: String,
            format: String,
            type: String,
            direction: String?,
        ): T
    }
}

interface SurahEntity {

    fun <T> map(mapper: Mapper<T>): T

    @Entity(tableName = "surah")
    data class Base(
        @PrimaryKey val number: Long,
        @SerializedName("name") val name: String,
        @SerializedName("englishName") val englishName: String,
        @SerializedName("englishNameTranslation") val englishNameTranslation: String,
        @SerializedName("numberOfAyahs") val numberOfAyahs: Long,
        @SerializedName("revelationType") val revelationType: String,
    ) : SurahEntity {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(
            number, name, englishName, englishNameTranslation, numberOfAyahs, revelationType
        )
    }

    interface Mapper<T> {
        fun map(
            number: Long,
            name: String,
            englishName: String,
            englishNameTranslation: String,
            numberOfAyahs: Long,
            revelationType: String,
        ): T
    }
}