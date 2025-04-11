package com.zaur.domain.al_quran_cloud.models.audiofile

import com.google.gson.annotations.SerializedName

data class ChapterAudiosFileAqc(
    val code: Long, val status: String, @SerializedName("data") val chapterAudio: ChapterAudioFile
)

data class ChapterAudioFile(
    val number: Long,
    val name: String,
    val englishName: String,
    val englishNameTranslation: String,
    val revelationType: String,
    val numberOfAyahs: Long,
    val ayahs: List<Ayah>,
    val edition: EditionAudio
)

data class Ayah(
    val number: Long,
    val audio: String,
    val audioSecondary: List<String>,
    val text: String,
    val numberInSurah: Long,
    val juz: Long,
    val manzil: Long,
    val page: Long,
    val ruku: Long,
    val hizbQuarter: Long,
    val sajda: Boolean
)

data class EditionAudio(
    val identifier: String,
    val language: String,
    val name: String,
    val englishName: String,
    val format: String,
    val type: String,
    val direction: Any? = null
)
