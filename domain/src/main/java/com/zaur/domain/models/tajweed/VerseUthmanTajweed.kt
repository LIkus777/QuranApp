package com.zaur.domain.models.tajweed

import com.google.gson.annotations.SerializedName

data class VerseUthmanTajweed(
    val id: Long,
    @SerializedName("verse_key") val verseKey: String,
    @SerializedName("text_uthman") val textUthman: String
)