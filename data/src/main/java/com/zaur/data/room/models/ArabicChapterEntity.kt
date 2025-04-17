package com.zaur.data.room.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.zaur.data.room.converters.GenericConverters
import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChapter
import com.zaur.domain.al_quran_cloud.models.arabic.Ayah
import com.zaur.domain.al_quran_cloud.models.arabic.EditionArabic

@Entity(tableName = "arabic_chapters")
data class ArabicChapterEntity(
    @PrimaryKey @SerializedName("number") val number: Long,
    @SerializedName("name") val name: String,
    @SerializedName("englishName") val englishName: String,
    @SerializedName("englishNameTranslation") val englishNameTranslation: String,
    @SerializedName("revelationType") val revelationType: String,
    @SerializedName("numberOfAyahs") val numberOfAyahs: Long,
    @TypeConverters(GenericConverters::class) @SerializedName("ayahs") val ayahs: List<ArabicAyahEntity>,
    @TypeConverters(GenericConverters::class) @SerializedName("edition") val edition: EditionArabicEntity,
)

data class ArabicAyahEntity(
    @SerializedName("number") val number: Long,
    @SerializedName("text") val text: String,
    @SerializedName("numberInSurah") val numberInSurah: Long,
    @SerializedName("juz") val juz: Long,
    @SerializedName("manzil") val manzil: Long,
    @SerializedName("page") val page: Long,
    @SerializedName("ruku") val ruku: Long,
    @SerializedName("hizbQuarter") val hizbQuarter: Long,
    @SerializedName("sajda") val sajda: Boolean,
)

data class EditionArabicEntity(
    @SerializedName("identifier") val identifier: String,
    @SerializedName("language") val language: String,
    @SerializedName("name") val name: String,
    @SerializedName("englishName") val englishName: String,
    @SerializedName("format") val format: String,
    @SerializedName("type") val type: String,
    @SerializedName("direction") val direction: String,
)

fun ArabicChapter.toData(): ArabicChapterEntity {
    return ArabicChapterEntity(
        number,
        name,
        englishName,
        englishNameTranslation,
        revelationType,
        numberOfAyahs,
        ayahs = ayahs.map { it.toData() },
        edition = edition.toData()
    )
}

fun Ayah.toData(): ArabicAyahEntity {
    return ArabicAyahEntity(
        number, text, numberInSurah, juz, manzil, page, ruku, hizbQuarter, sajda
    )
}

fun EditionArabic.toData(): EditionArabicEntity {
    return EditionArabicEntity(
        identifier, language, name, englishName, format, type, direction
    )
}