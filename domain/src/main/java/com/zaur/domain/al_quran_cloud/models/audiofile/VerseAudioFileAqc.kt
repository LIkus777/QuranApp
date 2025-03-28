package com.zaur.domain.al_quran_cloud.models.audiofile

data class VerseAudioFileAqc(
    val code: Long, val status: String, val data: Data
)

data class Data(
    val number: Long,
    val audio: String,
    val audioSecondary: List<String>,
    val text: String,
    val edition: EditionVerse,
    val surah: Surah,
    val numberInSurah: Long,
    val juz: Long,
    val manzil: Long,
    val page: Long,
    val ruku: Long,
    val hizbQuarter: Long,
    val sajda: Boolean
)

data class EditionVerse(
    val identifier: String,
    val language: String,
    val name: String,
    val englishName: String,
    val format: String,
    val type: String,
    val direction: Any? = null
)

data class Surah(
    val number: Long,
    val name: String,
    val englishName: String,
    val englishNameTranslation: String,
    val numberOfAyahs: Long,
    val revelationType: String
)
