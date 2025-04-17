package com.zaur.data.room.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.zaur.domain.al_quran_cloud.models.translate.Ayah
import com.zaur.domain.al_quran_cloud.models.translate.EditionTranslation
import com.zaur.domain.al_quran_cloud.models.translate.TranslationAqc

@Entity(tableName = "translation_chapter")
data class TranslationEntity(
    @PrimaryKey @SerializedName("number") val number: Long,
    @SerializedName("name") val name: String,
    @SerializedName("englishName") val englishName: String,
    @SerializedName("englishNameTranslation") val englishNameTranslation: String,
    @SerializedName("revelationType") val revelationType: String,
    @SerializedName("numberOfAyahs") val numberOfAyahs: Long,
    @SerializedName("ayahs") val translationAyahEntity: List<TranslationAyahEntity>,
    @SerializedName("edition") val edition: EditionTranslationEntity,
)

data class TranslationAyahEntity(
    @PrimaryKey @SerializedName("number") val number: Long,
    @SerializedName("text") val text: String,
    @SerializedName("numberInSurah") val numberInSurah: Long,
    @SerializedName("juz") val juz: Long,
    @SerializedName("manzil") val manzil: Long,
    @SerializedName("page") val page: Long,
    @SerializedName("ruku") val ruku: Long,
    @SerializedName("hizbQuarter") val hizbQuarter: Long,
    @SerializedName("sajda") val sajda: Boolean,
)

data class EditionTranslationEntity(
    @PrimaryKey @SerializedName("identifier") val identifier: String,
    @SerializedName("language") val language: String,
    @SerializedName("name") val name: String,
    @SerializedName("englishName") val englishName: String,
    @SerializedName("format") val format: String,
    @SerializedName("type") val type: String,
    @SerializedName("direction") val direction: String,
)

fun TranslationAqc.toData(): TranslationEntity {
    return TranslationEntity(
        number,
        name,
        englishName,
        englishNameTranslation,
        revelationType,
        numberOfAyahs,
        ayahs.map { it.toData() },
        edition.toData()
    )
}

fun Ayah.toData(): TranslationAyahEntity {
    return TranslationAyahEntity(
        number, text, numberInSurah, juz, manzil, page, ruku, hizbQuarter, sajda
    )
}

fun EditionTranslation.toData(): EditionTranslationEntity {
    return EditionTranslationEntity(
        identifier, language, name, englishName, format, type, direction
    )
}