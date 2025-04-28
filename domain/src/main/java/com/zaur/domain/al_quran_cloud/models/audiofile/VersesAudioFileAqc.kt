package com.zaur.domain.al_quran_cloud.models.audiofile

import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.zaur.domain.base.SajdaAdapter

interface VersesAudioFileAqc {

    fun <T> map(mapper: Mapper<T>): T

    class Base(
        @SerializedName("code") private val code: Long,
        @SerializedName("status") private val status: String,
        @SerializedName("data") private val verseAudio: VerseAudioAqc,
    ) : VersesAudioFileAqc {
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

    fun <T> map(mapper: Mapper<T>): T

    class Base(
        @SerializedName("number") private val verseNumber: Long,
        private val reciter: String,
        @SerializedName("audio") private val audio: String,
        @SerializedName("audioSecondary") private val audioSecondary: List<String>,
        @SerializedName("text") private val text: String,
        @SerializedName("edition") private val edition: EditionVerse,
        @SerializedName("surah") private val surah: Surah,
        @SerializedName("numberInSurah") private val numberInSurah: Long,
        @SerializedName("juz") private val juz: Long,
        @SerializedName("manzil") private val manzil: Long,
        @SerializedName("page") private val page: Long,
        @SerializedName("ruku") private val ruku: Long,
        @SerializedName("hizbQuarter") private val hizbQuarter: Long,
        @JsonAdapter(SajdaAdapter::class) @SerializedName("sajda") private val sajda: Boolean,
    ) : VerseAudioAqc {
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
}

interface EditionVerse {

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
}

interface Surah {

    fun <T> map(mapper: Mapper<T>): T

    class Base(
        @SerializedName("number") private val number: Long,
        @SerializedName("name") private val name: String,
        @SerializedName("englishName") private val englishName: String,
        @SerializedName("englishNameTranslation") private val englishNameTranslation: String,
        @SerializedName("numberOfAyahs") private val numberOfAyahs: Long,
        @SerializedName("revelationType") private val revelationType: String,
    ) : Surah {
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