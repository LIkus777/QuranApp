package com.zaur.domain.apiV4.models.translate

import com.google.gson.annotations.SerializedName

data class TranslationV4 (
    val id: Long,
    val name: String,
    @SerializedName("author_name")
    val authorName: String,
    val slug: String,
    @SerializedName("language_name")
    val languageName: String,
    @SerializedName("translated_name")
    val translatedName: TranslatedNameForTranslation
)

data class TranslatedNameForTranslation (
    val name: String,
    @SerializedName("language_name")
    val languageName: String
)
