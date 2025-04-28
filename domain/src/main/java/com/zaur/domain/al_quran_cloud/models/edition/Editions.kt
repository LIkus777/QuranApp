package com.zaur.domain.al_quran_cloud.models.edition

import com.google.gson.annotations.SerializedName

interface Editions {

    fun <T> map(mapper: Mapper<T>): T

    class Base(
        @SerializedName("code") private val code: Long,
        @SerializedName("status") private val status: String,
        @SerializedName("data") private val data: List<Datum>,
    ) : Editions {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(code, status, data)
    }

    interface Mapper<T> {
        fun map(
            code: Long,
            status: String,
            data: List<Datum>,
        ): T
    }
}

interface Datum {

    fun <T> map(mapper: Mapper<T>): T

    class Base(
        @SerializedName("identifier") private val identifier: String,
        @SerializedName("language") private val language: String,
        @SerializedName("name") private val name: String,
        @SerializedName("englishName") private val englishName: String,
        @SerializedName("format") private val format: Format,
        @SerializedName("type") private val type: Type,
        @SerializedName("direction") private val direction: Direction? = null,
    ) : Datum {
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
            format: Format,
            type: Type,
            direction: Direction?,
        ): T
    }
}

enum class Direction(val value: String) {
    LTR("ltr"), RTL("rtl");

    companion object {
        fun fromValue(value: String): Direction = when (value) {
            "ltr" -> LTR
            "rtl" -> RTL
            else -> throw IllegalArgumentException()
        }
    }
}

enum class Format(val value: String) {
    Audio("audio"), Text("text");

    companion object {
        fun fromValue(value: String): Format = when (value) {
            "audio" -> Audio
            "text" -> Text
            else -> throw IllegalArgumentException()
        }
    }
}

enum class Type(val value: String) {
    Quran("quran"), Tafsir("tafsir"), Translation("translation"), Transliteration("transliteration"), Versebyverse(
        "versebyverse"
    );

    companion object {
        fun fromValue(value: String): Type = when (value) {
            "quran" -> Quran
            "tafsir" -> Tafsir
            "translation" -> Translation
            "transliteration" -> Transliteration
            "versebyverse" -> Versebyverse
            else -> throw IllegalArgumentException()
        }
    }
}
