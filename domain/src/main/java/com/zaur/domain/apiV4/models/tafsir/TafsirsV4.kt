package com.zaur.domain.apiV4.models.tafsir

import com.google.gson.annotations.SerializedName
import com.zaur.domain.apiV4.models.TranslatedNameV4

data class TafsirV4(
    val id: Long,
    val name: String,
    @SerializedName("author_name") val authorName: String,
    val slug: String,
    @SerializedName("language_name") val languageName: String,
    @SerializedName("translated_name") val translatedNameV4: TranslatedNameV4
)

enum class LanguageNameV4(val value: String) {
    English("english");

    companion object {
        fun fromValue(value: String): LanguageNameV4 = when (value) {
            "english" -> English
            else -> throw IllegalArgumentException()
        }
    }
}
