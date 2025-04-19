package com.zaur.data.room.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.zaur.data.room.converters.GenericConverters
import com.zaur.domain.base.SajdaAdapter

@Entity(tableName = "chapter_audio")
data class ChapterAudioEntity(
    @PrimaryKey @SerializedName("number") val number: Long,
    @SerializedName("name") val name: String,
    @SerializedName("englishName") val englishName: String,
    @SerializedName("englishNameTranslation") val englishNameTranslation: String,
    @SerializedName("revelationType") val revelationType: String,
    @SerializedName("numberOfAyahs") val numberOfAyahs: Long,
    @SerializedName("ayahs") val ayahs: List<AyahAudioEntity>,
    @SerializedName("edition") val edition: EditionAudioEntity,
    val reciter: String,
)

@Entity(tableName = "ayah_audio", primaryKeys = ["chapterNumber", "verseNumber", "reciter"])
data class AyahAudioEntity(
    val chapterNumber: Long,
    val verseNumber: Long,
    val reciter: String,
    @SerializedName("audio") val audio: String,
    @TypeConverters(GenericConverters::class) @SerializedName("audioSecondary") val audioSecondary: List<String>,
    @SerializedName("text") val text: String,
    @SerializedName("numberInSurah") val numberInSurah: Long,
    @SerializedName("juz") val juz: Long,
    @SerializedName("manzil") val manzil: Long,
    @SerializedName("page") val page: Long,
    @SerializedName("ruku") val ruku: Long,
    @SerializedName("hizbQuarter") val hizbQuarter: Long,
    @JsonAdapter(SajdaAdapter::class) @SerializedName("sajda") val sajda: Boolean,
)

data class EditionAudioEntity(
    @PrimaryKey @SerializedName("identifier") val identifier: String,
    @SerializedName("language") val language: String,
    @SerializedName("name") val name: String,
    @SerializedName("englishName") val englishName: String,
    @SerializedName("format") val format: String,
    @SerializedName("type") val type: String,
    @SerializedName("direction") val direction: String? = null,
)