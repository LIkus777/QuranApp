package com.zaur.data.room.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.TypeConverters
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.zaur.data.room.converters.GenericConverters
import com.zaur.domain.base.SajdaAdapter

@Entity(tableName = "verse_audio", primaryKeys = ["number", "verseNumber", "reciter"])
data class VerseAudioEntity(
    @Embedded(prefix = "surah_") val surah: SurahEntity,
    val verseNumber: Long,
    val reciter: String,
    @SerializedName("audio") val audio: String,
    @SerializedName("audioSecondary") val audioSecondary: List<String>,
    @SerializedName("text") val text: String,
    @TypeConverters(GenericConverters::class) @SerializedName("edition") val edition: EditionVerseEntity,
    @SerializedName("numberInSurah") val numberInSurah: Long,
    @SerializedName("juz") val juz: Long,
    @SerializedName("manzil") val manzil: Long,
    @SerializedName("page") val page: Long,
    @SerializedName("ruku") val ruku: Long,
    @SerializedName("hizbQuarter") val hizbQuarter: Long,
    @JsonAdapter(SajdaAdapter::class) @SerializedName("sajda") val sajda: Boolean,
)

data class EditionVerseEntity(
    @SerializedName("identifier") val identifier: String,
    @SerializedName("language") val language: String,
    @SerializedName("name") val name: String,
    @SerializedName("englishName") val englishName: String,
    @SerializedName("format") val format: String,
    @SerializedName("type") val type: String,
    @SerializedName("direction") val direction: String? = null,
)

data class SurahEntity(
    @SerializedName("number") val number: Long,
    @SerializedName("name") val name: String,
    @SerializedName("englishName") val englishName: String,
    @SerializedName("englishNameTranslation") val englishNameTranslation: String,
    @SerializedName("numberOfAyahs") val numberOfAyahs: Long,
    @SerializedName("revelationType") val revelationType: String,
)