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
import com.zaur.domain.al_quran_cloud.models.audiofile.EditionVerse
import com.zaur.domain.al_quran_cloud.models.audiofile.Surah
import com.zaur.domain.al_quran_cloud.models.audiofile.VerseAudioAqc
import com.zaur.domain.base.SajdaAdapter

interface VerseAudioWithSurah {

    fun verse(): VerseAudioEntity
    fun surah(): SurahEntity

    fun <T> map(mapper: Mapper<T>): T

    class Base(
        @Embedded val verse: VerseAudioEntity.Base,
        @Relation(
            parentColumn = "surahNumber", entityColumn = "number"
        ) val surah: SurahEntity.Base,
    ) : VerseAudioWithSurah {
        override fun verse(): VerseAudioEntity = verse
        override fun surah(): SurahEntity = surah

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

    fun verseNumber(): Long
    fun reciter(): String
    fun audio(): String
    fun audioSecondary(): List<String>
    fun text(): String
    fun edition(): EditionVerseEntity
    fun numberInSurah(): Long
    fun juz(): Long
    fun manzil(): Long
    fun page(): Long
    fun ruku(): Long
    fun hizbQuarter(): Long
    fun sajda(): Boolean

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
        @ColumnInfo(name = "surahNumber") val surahNumber: Long = 0L,
        val verseNumber: Long = 0L,
        val reciter: String = "",
        @SerializedName("audio") val audio: String,
        @SerializedName("audioSecondary") val audioSecondary: List<String>,
        @SerializedName("text") val text: String,
        @TypeConverters(GenericConverters::class) @SerializedName("edition") val edition: EditionVerseEntity,
        @SerializedName("numberInSurah") val numberInSurah: Long,
        @SerializedName("juz") val juz: Long,
        @SerializedName("manzil") val manzil: Long,
        @SerializedName("page") val page: Long,
        @SerializedName("ruku") val ruku: Long,
        @SerializedName("hizbQuarter") val hizbQuarter: Long,
        @JsonAdapter(SajdaAdapter::class) @SerializedName("sajda") val sajda: Boolean,
    ) : VerseAudioEntity {

        override fun verseNumber(): Long = verseNumber
        override fun reciter(): String = reciter
        override fun audio(): String = audio
        override fun audioSecondary(): List<String> = audioSecondary
        override fun text(): String = text
        override fun edition(): EditionVerseEntity = edition
        override fun numberInSurah(): Long = numberInSurah
        override fun juz(): Long = juz
        override fun manzil(): Long = manzil
        override fun page(): Long = page
        override fun ruku(): Long = ruku
        override fun hizbQuarter(): Long = hizbQuarter
        override fun sajda(): Boolean = sajda

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

    fun identifier(): String
    fun language(): String
    fun name(): String
    fun englishName(): String
    fun format(): String
    fun type(): String
    fun direction(): String

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
        override fun identifier(): String = identifier
        override fun language(): String = language
        override fun name(): String = name
        override fun englishName(): String = englishName
        override fun format(): String = format
        override fun type(): String = type
        override fun direction(): String = direction.toString()

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

    fun number(): Long
    fun name(): String
    fun englishName(): String
    fun englishNameTranslation(): String
    fun numberOfAyahs(): Long
    fun revelationType(): String


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
        override fun number(): Long = number
        override fun name(): String = name
        override fun englishName(): String = englishName
        override fun englishNameTranslation(): String = englishNameTranslation
        override fun numberOfAyahs(): Long = numberOfAyahs
        override fun revelationType(): String = revelationType

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