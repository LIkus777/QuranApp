package com.zaur.domain.al_quran_cloud.models.translate

data class TranslationChapterAqc(
    val code: Long, val status: String, val data: Data
)

data class Data(
    val number: Long,
    val name: String,
    val englishName: String,
    val englishNameTranslation: String,
    val revelationType: String,
    val numberOfAyahs: Long,
    val ayahs: List<Ayah>,
    val edition: EditionTranslation
)

data class Ayah(
    val number: Long,
    val text: String,
    val numberInSurah: Long,
    val juz: Long,
    val manzil: Long,
    val page: Long,
    val ruku: Long,
    val hizbQuarter: Long,
    val sajda: Boolean
)

data class EditionTranslation(
    val identifier: String,
    val language: String,
    val name: String,
    val englishName: String,
    val format: String,
    val type: String,
    val direction: String
)