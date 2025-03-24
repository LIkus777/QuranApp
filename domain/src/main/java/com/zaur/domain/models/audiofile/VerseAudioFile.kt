package com.zaur.domain.models.audiofile

import com.google.gson.annotations.SerializedName

data class VerseAudioFile(
    @SerializedName("audio_files") val audioFiles: List<AudioFile>, val pagination: Pagination
)

data class AudioFile(
    @SerializedName("verse_key") val verseKey: String, val url: String
)

data class Pagination(
    @SerializedName("per_page") val perPage: Long,
    @SerializedName("current_page") val currentPage: Long,
    @SerializedName("next_page") val nextPage: Any? = null,
    @SerializedName("total_pages") val totalPages: Long,
    @SerializedName("total_records") val totalRecords: Long
)