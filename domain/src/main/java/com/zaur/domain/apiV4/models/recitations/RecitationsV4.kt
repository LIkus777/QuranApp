package com.zaur.domain.apiV4.models.recitations

import com.google.gson.annotations.SerializedName
import com.zaur.domain.apiV4.models.TranslatedNameV4

data class RecitationsV4(
    val id: Long,
    @SerializedName("reciter_name") val reciterName: String,
    val style: String? = null,
    @SerializedName("translated_name") val translatedNameV4: TranslatedNameV4
)

enum class LanguageNameV4(val value: String) {
    English("english"), Russian("russian");

    companion object {
        fun fromValue(value: String): LanguageNameV4 = when (value) {
            "english" -> English
            "russian" -> Russian
            else -> throw IllegalArgumentException()
        }
    }
}