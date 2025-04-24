package com.zaur.domain.al_quran_cloud.models.edition

import com.google.gson.annotations.SerializedName

data class Editions(
    @SerializedName("code") val code: Long,
    @SerializedName("status") val status: String,
    @SerializedName("data") val data: List<Datum>,
)

data class Datum(
    @SerializedName("identifier") val identifier: String,
    @SerializedName("language") val language: String,
    @SerializedName("name") val name: String,
    @SerializedName("englishName") val englishName: String,
    @SerializedName("format") val format: Format,
    @SerializedName("type") val type: Type,
    @SerializedName("direction") val direction: Direction? = null,
)

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
