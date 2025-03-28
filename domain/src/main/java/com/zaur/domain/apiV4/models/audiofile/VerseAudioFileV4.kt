package com.zaur.domain.apiV4.models.audiofile

import com.google.gson.annotations.SerializedName

data class VerseAudioFileV4(
    @SerializedName("audio_files") val audioFileV4s: List<AudioFileV4>, val paginationV4: PaginationV4
)

data class AudioFileV4(
    @SerializedName("verse_key") val verseKey: String, val url: String
)

data class PaginationV4(
    @SerializedName("per_page") val perPage: Long,
    @SerializedName("current_page") val currentPage: Long,
    @SerializedName("next_page") val nextPage: Any? = null,
    @SerializedName("total_pages") val totalPages: Long,
    @SerializedName("total_records") val totalRecords: Long
)