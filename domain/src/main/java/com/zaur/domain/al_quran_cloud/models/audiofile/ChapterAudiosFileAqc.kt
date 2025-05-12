package com.zaur.domain.al_quran_cloud.models.audiofile

import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.zaur.domain.base.SajdaAdapter

/**
* @author Zaur
* @since 2025-05-12
*/

interface ChapterAudiosFileAqc {

    fun chapterAudio(): ChapterAudioFile.Base

    fun <T> map(mapper: Mapper<T>): T

    class Base(
        @SerializedName("code") private val code: Long,
        @SerializedName("status") private val status: String,
        @SerializedName("data") private val chapterAudio: ChapterAudioFile.Base,
    ) : ChapterAudiosFileAqc {
        override fun chapterAudio(): ChapterAudioFile.Base = chapterAudio
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(code, status, chapterAudio)
    }

    interface Mapper<T> {
        fun map(
            code: Long,
            status: String,
            chapterAudio: ChapterAudioFile,
        ): T
    }
}

interface ChapterAudioFile {

    fun number(): Long
    fun name(): String
    fun englishName(): String
    fun englishNameTranslation(): String
    fun revelationType(): String
    fun numberOfAyahs(): Long
    fun ayahs(): List<Ayah.Base>
    fun edition(): EditionAudio
    fun reciter(): String

    fun <T> map(mapper: Mapper<T>): T

    class Base(
        @SerializedName("number") private val number: Long,
        @SerializedName("name") private val name: String,
        @SerializedName("englishName") private val englishName: String,
        @SerializedName("englishNameTranslation") private val englishNameTranslation: String,
        @SerializedName("revelationType") private val revelationType: String,
        @SerializedName("numberOfAyahs") private val numberOfAyahs: Long,
        @SerializedName("ayahs") private val ayahs: List<Ayah.Base>,
        @SerializedName("edition") private val edition: EditionAudio.Base,
        private val reciter: String,
    ) : ChapterAudioFile {
        override fun number(): Long = number
        override fun name(): String = name
        override fun englishName(): String = englishName
        override fun englishNameTranslation(): String = englishNameTranslation
        override fun revelationType(): String = revelationType
        override fun numberOfAyahs(): Long = numberOfAyahs
        override fun ayahs(): List<Ayah.Base> = ayahs
        override fun edition(): EditionAudio.Base = edition
        override fun reciter(): String = reciter

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
            ayahs: List<Ayah.Base>,
            edition: EditionAudio,
            reciter: String,
        ): T
    }

    object Empty : ChapterAudioFile {
        override fun number(): Long = 0
        override fun name(): String = ""
        override fun englishName(): String = ""
        override fun englishNameTranslation(): String = ""
        override fun revelationType(): String = ""
        override fun numberOfAyahs(): Long = 0
        override fun ayahs(): List<Ayah.Base> = emptyList()
        override fun edition(): EditionAudio = EditionAudio.Empty
        override fun reciter(): String = ""
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(
            number(), name(), englishName(), englishNameTranslation(),
            revelationType(), numberOfAyahs(), ayahs(), edition(), reciter()
        )
    }
}

interface Ayah {

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

    class Base(
        private val reciter: String,
        @SerializedName("number") private val verseNumber: Long,
        @SerializedName("audio") private val audio: String,
        @SerializedName("audioSecondary") private val audioSecondary: List<String>,
        @SerializedName("text") private val text: String,
        @SerializedName("numberInSurah") private val numberInSurah: Long,
        @SerializedName("juz") private val juz: Long,
        @SerializedName("manzil") private val manzil: Long,
        @SerializedName("page") private val page: Long,
        @SerializedName("ruku") private val ruku: Long,
        @SerializedName("hizbQuarter") private val hizbQuarter: Long,
        @JsonAdapter(SajdaAdapter::class) @SerializedName("sajda") private val sajda: Boolean,
    ) : Ayah {

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

interface EditionAudio {

    fun identifier(): String
    fun language(): String
    fun name(): String
    fun englishName(): String
    fun format(): String
    fun type(): String
    fun direction(): String?

    fun <T> map(mapper: Mapper<T>): T

    class Base(
        @SerializedName("identifier") private val identifier: String,
        @SerializedName("language") private val language: String,
        @SerializedName("name") private val name: String,
        @SerializedName("englishName") private val englishName: String,
        @SerializedName("format") private val format: String,
        @SerializedName("type") private val type: String,
        @SerializedName("direction") private val direction: String? = null,
    ) : EditionAudio {

        override fun identifier() = identifier
        override fun language() = language
        override fun name() = name
        override fun englishName() = englishName
        override fun format() = format
        override fun type() = type
        override fun direction() = direction

        override fun <T> map(mapper: Mapper<T>): T =
            mapper.map(identifier, language, name, englishName, format, type, direction)
    }

    interface Mapper<T> {
        fun map(
            identifier: String,
            language: String,
            name: String,
            englishName: String,
            format: String,
            type: String,
            direction: String? = null,
        ): T
    }

    object Empty : EditionAudio {
        override fun identifier(): String = ""
        override fun language(): String = ""
        override fun name(): String = ""
        override fun englishName(): String = ""
        override fun format(): String = ""
        override fun type(): String = ""
        override fun direction(): String? = null

        override fun <T> map(mapper: Mapper<T>): T = mapper.map(
            identifier(), language(), name(), englishName(), format(), type(), direction()
        )
    }
}