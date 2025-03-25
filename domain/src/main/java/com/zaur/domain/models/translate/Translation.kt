package com.zaur.domain.models.translate

import com.google.gson.annotations.SerializedName

data class Translation (
    val id: Long,
    val name: String,
    @SerializedName("author_name")
    val authorName: String,
    val slug: String,
    @SerializedName("language_name")
    val languageName: String,
    @SerializedName("translated_name")
    val translatedName: TranslatedName
)

data class TranslatedName (
    val name: String,
    @SerializedName("language_name")
    val languageName: String
)
