package com.zaur.domain.apiV4.models.translate

import com.google.gson.annotations.SerializedName

data class SingleTranslationsV4(
    val singleTranslations: List<SingleTranslation>, val metaTranslation: MetaTranslation
)

data class MetaTranslation(
    @SerializedName("translation_name") val translationName: String,
    @SerializedName("author_name") val authorName: String
)

data class SingleTranslation(
    @SerializedName("resource_id") val resourceID: Long,
    @SerializedName("resource_name") val resourceName: String,
    val id: Long,
    val text: String,
    @SerializedName("verse_id") val verseID: Long,
    @SerializedName("language_id") val languageID: Long,
    @SerializedName("language_name") val languageName: String,
    @SerializedName("verse_key") val verseKey: String,
    @SerializedName("chapter_id") val chapterID: Long,
    @SerializedName("verse_number") val verseNumber: Long,
    @SerializedName("juz_number") val juzNumber: Long,
    @SerializedName("hizb_number") val hizbNumber: Long,
    @SerializedName("rub_number") val rubNumber: Long,
    @SerializedName("page_number") val pageNumber: Long
)

