package com.zaur.domain.al_quran_cloud.models.edition

import com.google.gson.annotations.SerializedName

/**
 * @author Zaur
 * @since 2025-05-12
 */

interface Editions {

    fun code(): Long
    fun status(): String
    fun data(): List<Datum>

    fun <T> map(mapper: Mapper<T>): T

    data class Base(
        @SerializedName("code") private val code: Long,
        @SerializedName("status") private val status: String,
        @SerializedName("data") private val data: List<Datum>,
    ) : Editions {
        override fun code(): Long = code
        override fun status(): String = status
        override fun data(): List<Datum> = data

        override fun <T> map(mapper: Mapper<T>): T = mapper.map(code, status, data)
    }

    interface Mapper<T> {
        fun map(
            code: Long,
            status: String,
            data: List<Datum>,
        ): T
    }

    object Empty : Editions {
        override fun code(): Long = 0L
        override fun status(): String = ""
        override fun data(): List<Datum> = emptyList()

        override fun <T> map(mapper: Mapper<T>): T = mapper.map(code(), status(), data())
    }
}

interface Datum {

    // Геттеры
    fun identifier(): String
    fun language(): String
    fun name(): String
    fun englishName(): String
    fun format(): Format
    fun type(): Type
    fun direction(): Direction?

    fun <T> map(mapper: Mapper<T>): T

    data class Base(
        @SerializedName("identifier") private val identifier: String,
        @SerializedName("language") private val language: String,
        @SerializedName("name") private val name: String,
        @SerializedName("englishName") private val englishName: String,
        @SerializedName("format") private val format: Format,
        @SerializedName("type") private val type: Type,
        @SerializedName("direction") private val direction: Direction? = null,
    ) : Datum {
        override fun identifier(): String = identifier
        override fun language(): String = language
        override fun name(): String = name
        override fun englishName(): String = englishName
        override fun format(): Format = format
        override fun type(): Type = type
        override fun direction(): Direction? = direction

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

    object Empty : Datum {
        override fun identifier(): String = ""
        override fun language(): String = ""
        override fun name(): String = ""
        override fun englishName(): String = ""
        override fun format(): Format = Format.Text
        override fun type(): Type = Type.Quran
        override fun direction(): Direction? = null

        override fun <T> map(mapper: Mapper<T>): T = mapper.map(
            identifier(), language(), name(), englishName(), format(), type(), direction()
        )
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
