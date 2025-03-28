package com.zaur.domain.apiV4.models.tajweed

import com.google.gson.annotations.SerializedName

data class VerseUthmanTajweedV4(
    val id: Long,
    @SerializedName("verse_key") val verseKey: String,
    @SerializedName("text_uthman") val textUthman: String
)