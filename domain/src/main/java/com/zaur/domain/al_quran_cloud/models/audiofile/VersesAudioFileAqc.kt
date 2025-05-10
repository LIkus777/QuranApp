package com.zaur.domain.al_quran_cloud.models.audiofile

import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.zaur.domain.base.SajdaAdapter

interface VersesAudioFileAqc {

    fun verseAudio(): VerseAudioAqc.Base

    fun <T> map(mapper: Mapper<T>): T

    class Base(
        @SerializedName("code") private val code: Long,
        @SerializedName("status") private val status: String,
        @SerializedName("data") private val verseAudio: VerseAudioAqc.Base,
    ) : VersesAudioFileAqc {
        override fun verseAudio(): VerseAudioAqc.Base = verseAudio
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(code, status, verseAudio)
    }

    interface Mapper<T> {
        fun map(
            code: Long,
            status: String,
            verseAudio: VerseAudioAqc,
        ): T
    }
}

interface VerseAudioAqc {

    fun verseNumber(): Long
    fun reciter(): String
    fun audio(): String
    fun audioSecondary(): List<String>
    fun text(): String
    fun edition(): EditionVerse
    fun surah(): Surah
    fun numberInSurah(): Long
    fun juz(): Long
    fun manzil(): Long
    fun page(): Long
    fun ruku(): Long
    fun hizbQuarter(): Long
    fun sajda(): Boolean

    fun <T> map(mapper: Mapper<T>): T

    data class Base(
        @SerializedName("number") private val verseNumber: Long,
        private val reciter: String,
        @SerializedName("audio") private val audio: String,
        @SerializedName("audioSecondary") private val audioSecondary: List<String>,
        @SerializedName("text") private val text: String,
        @SerializedName("edition") private val edition: EditionVerse.Base,
        @SerializedName("surah") private val surah: Surah.Base,
        @SerializedName("numberInSurah") private val numberInSurah: Long,
        @SerializedName("juz") private val juz: Long,
        @SerializedName("manzil") private val manzil: Long,
        @SerializedName("page") private val page: Long,
        @SerializedName("ruku") private val ruku: Long,
        @SerializedName("hizbQuarter") private val hizbQuarter: Long,
        @JsonAdapter(SajdaAdapter::class) @SerializedName("sajda") private val sajda: Boolean,
    ) : VerseAudioAqc {

        override fun verseNumber(): Long = verseNumber
        override fun reciter(): String = reciter
        override fun audio(): String = audio
        override fun audioSecondary(): List<String> = audioSecondary
        override fun text(): String = text
        override fun edition(): EditionVerse.Base = edition
        override fun surah(): Surah.Base = surah
        override fun numberInSurah(): Long = numberInSurah
        override fun juz(): Long = juz
        override fun manzil(): Long = manzil
        override fun page(): Long = page
        override fun ruku(): Long = ruku
        override fun hizbQuarter(): Long = hizbQuarter
        override fun sajda(): Boolean = sajda

        override fun <T> map(mapper: Mapper<T>): T = mapper.map(
            verseNumber,
            reciter,
            audio,
            audioSecondary,
            text,
            edition,
            surah,
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
            verseNumber: Long,
            reciter: String,
            audio: String,
            audioSecondary: List<String>,
            text: String,
            edition: EditionVerse,
            surah: Surah,
            numberInSurah: Long,
            juz: Long,
            manzil: Long,
            page: Long,
            ruku: Long,
            hizbQuarter: Long,
            sajda: Boolean,
        ): T
    }

    // Дефолтная реализация с выбрасыванием исключений
    object Empty : VerseAudioAqc {
        override fun verseNumber(): Long = 0
        override fun reciter(): String = ""
        override fun audio(): String = ""
        override fun audioSecondary(): List<String> = emptyList()
        override fun text(): String = ""
        override fun edition(): EditionVerse = EditionVerse.Empty
        override fun surah(): Surah = Surah.Empty
        override fun numberInSurah(): Long = 0
        override fun juz(): Long = 0
        override fun manzil(): Long = 0
        override fun page(): Long = 0
        override fun ruku(): Long = 0
        override fun hizbQuarter(): Long = 0
        override fun sajda(): Boolean = false
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(
            verseNumber(), reciter(), audio(), audioSecondary(), text(), edition(), surah(),
            numberInSurah(), juz(), manzil(), page(), ruku(), hizbQuarter(), sajda()
        )
    }
}

interface EditionVerse {

    fun identifier(): String
    fun language(): String
    fun name(): String
    fun englishName(): String
    fun format(): String
    fun type(): String
    fun direction(): String

    fun <T> map(mapper: Mapper<T>): T

    class Base(
        @SerializedName("identifier") private val identifier: String,
        @SerializedName("language") private val language: String,
        @SerializedName("name") private val name: String,
        @SerializedName("englishName") private val englishName: String,
        @SerializedName("format") private val format: String,
        @SerializedName("type") private val type: String,
        @SerializedName("direction") private val direction: Any? = null,
    ) : EditionVerse {
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
            direction: Any?,
        ): T
    }

    object Empty : EditionVerse {
        override fun identifier(): String = ""
        override fun language(): String = ""
        override fun name(): String = ""
        override fun englishName(): String = ""
        override fun format(): String = ""
        override fun type(): String = ""
        override fun direction(): String = ""

        override fun <T> map(mapper: Mapper<T>): T = mapper.map(
            identifier(), language(), name(), englishName(), format(), type(), direction()
        )
    }
}

interface Surah {

    fun number(): Long
    fun name(): String
    fun englishName(): String
    fun englishNameTranslation(): String
    fun numberOfAyahs(): Long
    fun revelationType(): String
    
    fun <T> map(mapper: Mapper<T>): T

    class Base(
        @SerializedName("number") private val number: Long,
        @SerializedName("name") private val name: String,
        @SerializedName("englishName") private val englishName: String,
        @SerializedName("englishNameTranslation") private val englishNameTranslation: String,
        @SerializedName("numberOfAyahs") private val numberOfAyahs: Long,
        @SerializedName("revelationType") private val revelationType: String,
    ) : Surah {
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

    object Empty : Surah {
        override fun number(): Long = -1L
        override fun name(): String = ""
        override fun englishName(): String = ""
        override fun englishNameTranslation(): String = ""
        override fun numberOfAyahs(): Long = 0L
        override fun revelationType(): String = ""

        override fun <T> map(mapper: Mapper<T>): T = mapper.map(
            number(), name(), englishName(), englishNameTranslation(), numberOfAyahs(), revelationType()
        )
    }
}