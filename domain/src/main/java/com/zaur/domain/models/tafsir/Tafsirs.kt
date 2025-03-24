package com.zaur.domain.models.tafsir

import com.google.gson.annotations.SerializedName
import com.zaur.domain.models.TranslatedName

data class Tafsirs(
    val tafsirs: List<Tafsir>
)

data class Tafsir(
    val id: Long,
    val name: String,
    @SerializedName("author_name") val authorName: String,
    val slug: String,
    @SerializedName("language_name") val languageName: String,
    @SerializedName("translated_name") val translatedName: TranslatedName
)

enum class LanguageName(val value: String) {
    English("english");

    companion object {
        fun fromValue(value: String): LanguageName = when (value) {
            "english" -> English
            else -> throw IllegalArgumentException()
        }
    }
}
