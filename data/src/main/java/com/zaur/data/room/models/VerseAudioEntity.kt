package com.zaur.data.room.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.zaur.data.room.converters.GenericConverters

@Entity(tableName = "verse_audio")
data class VerseAudioEntity(
    @PrimaryKey @SerializedName("number") val number: Long,
    @SerializedName("audio") val audio: String,
    @SerializedName("audioSecondary") val audioSecondary: List<String>,
    @SerializedName("text") val text: String,
    @TypeConverters(GenericConverters::class) @SerializedName("edition") val edition: EditionVerseEntity,
    @TypeConverters(GenericConverters::class) @SerializedName("surah") val surah: SurahEntity,
    @SerializedName("numberInSurah") val numberInSurah: Long,
    @SerializedName("juz") val juz: Long,
    @SerializedName("manzil") val manzil: Long,
    @SerializedName("page") val page: Long,
    @SerializedName("ruku") val ruku: Long,
    @SerializedName("hizbQuarter") val hizbQuarter: Long,
    @SerializedName("sajda") val sajda: Boolean
)

data class EditionVerseEntity(
    @SerializedName("identifier") val identifier: String,
    @SerializedName("language") val language: String,
    @SerializedName("name") val name: String,
    @SerializedName("englishName") val englishName: String,
    @SerializedName("format") val format: String,
    @SerializedName("type") val type: String,
    @SerializedName("direction") val direction: String? = null
)

data class SurahEntity(
    @SerializedName("number") val number: Long,
    @SerializedName("name") val name: String,
    @SerializedName("englishName") val englishName: String,
    @SerializedName("englishNameTranslation") val englishNameTranslation: String,
    @SerializedName("numberOfAyahs") val numberOfAyahs: Long,
    @SerializedName("revelationType") val revelationType: String
)