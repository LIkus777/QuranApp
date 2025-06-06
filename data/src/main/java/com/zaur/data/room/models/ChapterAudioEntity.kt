package com.zaur.data.room.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.zaur.data.room.converters.GenericConverters
import com.zaur.domain.base.SajdaAdapter

/**
* @author Zaur
* @since 2025-05-12
*/

interface ChapterAudioEntity {

    fun <T> map(mapper: Mapper<T>): T

    @Entity(tableName = "chapter_audio")
    data class Base(
        @PrimaryKey val number: Long,
        @SerializedName("name") val name: String,
        @SerializedName("englishName") val englishName: String,
        @SerializedName("englishNameTranslation") val englishNameTranslation: String,
        @SerializedName("revelationType") val revelationType: String,
        @SerializedName("numberOfAyahs") val numberOfAyahs: Long,
        @TypeConverters(GenericConverters::class) @SerializedName("ayahs") val ayahs: List<AyahAudioEntity.Base>,
        @TypeConverters(GenericConverters::class) @SerializedName("edition") val edition: EditionAudioEntity.Base,
        val reciter: String,
    ) : ChapterAudioEntity {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(
            number,
            name,
            englishName,
            englishNameTranslation,
            revelationType,
            numberOfAyahs,
            ayahs,
            edition,
            reciter
        )
    }

    interface Mapper<T> {
        fun map(
            number: Long,
            name: String,
            englishName: String,
            englishNameTranslation: String,
            revelationType: String,
            numberOfAyahs: Long,
            ayahs: List<AyahAudioEntity>,
            edition: EditionAudioEntity,
            reciter: String,
        ): T
    }
}


interface AyahAudioEntity {

    fun reciter(): String
    fun verseNumber(): Long
    fun audio(): String
    fun audioSecondary(): List<String>
    fun text(): String
    fun numberInSurah(): Long
    fun juz(): Long
    fun manzil(): Long
    fun page(): Long
    fun ruku(): Long
    fun hizbQuarter(): Long
    fun sajda(): Boolean

    fun <T> map(mapper: Mapper<T>): T

    data class Base(
        val reciter: String,
        @SerializedName("number") val verseNumber: Long,
        @SerializedName("audio") val audio: String,
        @SerializedName("audioSecondary") val audioSecondary: List<String>,
        @SerializedName("text") val text: String,
        @SerializedName("numberInSurah") val numberInSurah: Long,
        @SerializedName("juz") val juz: Long,
        @SerializedName("manzil") val manzil: Long,
        @SerializedName("page") val page: Long,
        @SerializedName("ruku") val ruku: Long,
        @SerializedName("hizbQuarter") val hizbQuarter: Long,
        @JsonAdapter(SajdaAdapter::class) @SerializedName("sajda") val sajda: Boolean,
    ) : AyahAudioEntity {

        override fun reciter() = reciter
        override fun verseNumber() = verseNumber
        override fun audio() = audio
        override fun audioSecondary() = audioSecondary
        override fun text() = text
        override fun numberInSurah() = numberInSurah
        override fun juz() = juz
        override fun manzil() = manzil
        override fun page() = page
        override fun ruku() = ruku
        override fun hizbQuarter() = hizbQuarter
        override fun sajda() = sajda

        override fun <T> map(mapper: Mapper<T>): T = mapper.map(
            reciter,
            verseNumber,
            audio,
            audioSecondary,
            text,
            numberInSurah,
            juz,
            manzil,
            page,
            ruku,
            hizbQuarter,
            sajda
        )
    }

    interface Mapper<T> {
        fun map(
            reciter: String,
            verseNumber: Long,
            audio: String,
            audioSecondary: List<String>,
            text: String,
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

interface EditionAudioEntity {

    fun identifier(): String
    fun language(): String
    fun name(): String
    fun englishName(): String
    fun format(): String
    fun type(): String
    fun direction(): String?

    fun <T> map(mapper: Mapper<T>): T

    data class Base(
        @PrimaryKey val identifier: String,
        @SerializedName("language") val language: String,
        @SerializedName("name") val name: String,
        @SerializedName("englishName") val englishName: String,
        @SerializedName("format") val format: String,
        @SerializedName("type") val type: String,
        @SerializedName("direction") val direction: String? = null,
    ) : EditionAudioEntity {

        override fun identifier() = identifier
        override fun language() = language
        override fun name() = name
        override fun englishName() = englishName
        override fun format() = format
        override fun type() = type
        override fun direction() = direction

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