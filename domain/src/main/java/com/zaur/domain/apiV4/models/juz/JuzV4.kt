package com.zaur.domain.apiV4.models.juz

import com.google.gson.annotations.SerializedName

data class JuzV4(
    val id: Long,
    @SerializedName("juz_number") val juzNumber: Long,
    @SerializedName("verse_mapping") val verseMapping: Map<String, String>,
    @SerializedName("first_verse_id") val firstVerseID: Long,
    @SerializedName("last_verse_id") val lastVerseID: Long,
    @SerializedName("verses_count") val versesCount: Long
)