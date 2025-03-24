package com.zaur.domain.models.tafsir

import com.google.gson.annotations.SerializedName

data class SingleTafsirs(
    val singleTafsirs: List<SingleTafsir>,
    val metaTafsir: MetaTafsir
)

data class MetaTafsir(
    @SerializedName("tafsir_name") val tafsirName: String,
    @SerializedName("author_name") val authorName: String
)

data class SingleTafsir(
    @SerializedName("verse_id") val verseID: Long,
    @SerializedName("language_id") val languageID: Long,
    val text: String,
    @SerializedName("language_name") val languageName: String,
    @SerializedName("resource_name") val resourceName: String,
    @SerializedName("verse_key") val verseKey: String,
    @SerializedName("chapter_id") val chapterID: Long,
    @SerializedName("verse_number") val verseNumber: Long,
    @SerializedName("juz_number") val juzNumber: Long,
    @SerializedName("hizb_number") val hizbNumber: Long,
    @SerializedName("rub_number") val rubNumber: Long,
    @SerializedName("page_number") val pageNumber: Long
)
