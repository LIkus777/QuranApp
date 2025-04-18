package com.zaur.domain.al_quran_cloud.models.audiofile

import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.zaur.domain.base.SajdaAdapter

data class VersesAudioFileAqc(
    @SerializedName("code") val code: Long,
    @SerializedName("status") val status: String,
    @SerializedName("data") val versesAudio: VerseAudioAqc
)

data class VerseAudioAqc(
    @SerializedName("number") val number: Long,
    @SerializedName("audio") val audio: String,
    @SerializedName("audioSecondary") val audioSecondary: List<String>,
    @SerializedName("text") val text: String,
    @SerializedName("edition") val edition: EditionVerse,
    @SerializedName("surah") val surah: Surah,
    @SerializedName("numberInSurah") val numberInSurah: Long,
    @SerializedName("juz") val juz: Long,
    @SerializedName("manzil") val manzil: Long,
    @SerializedName("page") val page: Long,
    @SerializedName("ruku") val ruku: Long,
    @SerializedName("hizbQuarter") val hizbQuarter: Long,
    @JsonAdapter(SajdaAdapter::class) @SerializedName("sajda") val sajda: Boolean
)

data class EditionVerse(
    @SerializedName("identifier") val identifier: String,
    @SerializedName("language") val language: String,
    @SerializedName("name") val name: String,
    @SerializedName("englishName") val englishName: String,
    @SerializedName("format") val format: String,
    @SerializedName("type") val type: String,
    @SerializedName("direction") val direction: Any? = null
)

data class Surah(
    @SerializedName("number") val number: Long,
    @SerializedName("name") val name: String,
    @SerializedName("englishName") val englishName: String,
    @SerializedName("englishNameTranslation") val englishNameTranslation: String,
    @SerializedName("numberOfAyahs") val numberOfAyahs: Long,
    @SerializedName("revelationType") val revelationType: String
)