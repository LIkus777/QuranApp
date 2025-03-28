package com.zaur.domain.apiV4.models.audiofile

import com.google.gson.annotations.SerializedName

data class ChapterAudioFileV4(
    val id: Long,
    @SerializedName("chapter_id") val chapterID: Long,
    @SerializedName("file_size") val fileSize: Long,
    val format: String,
    @SerializedName("total_files") val totalFiles: Long,
    @SerializedName("audio_url") val audioURL: String
)