package com.zaur.domain.models.recitations

import com.google.gson.annotations.SerializedName
import com.zaur.domain.models.TranslatedName

data class Recitations(
    val id: Long,
    @SerializedName("reciter_name") val reciterName: String,
    val style: String? = null,
    @SerializedName("translated_name") val translatedName: TranslatedName
)

enum class LanguageName(val value: String) {
    English("english"), Russian("russian");

    companion object {
        fun fromValue(value: String): LanguageName = when (value) {
            "english" -> English
            "russian" -> Russian
            else -> throw IllegalArgumentException()
        }
    }
}