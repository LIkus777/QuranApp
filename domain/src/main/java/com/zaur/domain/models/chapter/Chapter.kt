package com.zaur.domain.models.chapter

import com.google.gson.annotations.SerializedName
import com.zaur.domain.models.TranslatedName

data class Chapter(
    val id: Long,
    @SerializedName("revelation_place") val revelationPlace: String,
    @SerializedName("revelation_order") val revelationOrder: Long,
    @SerializedName("bismillah_pre") val bismillahPre: Boolean,
    @SerializedName("name_simple") val nameSimple: String,
    @SerializedName("name_complex") val nameComplex: String,
    @SerializedName("name_arabic") val nameArabic: String,
    @SerializedName("verses_count") val versesCount: Long,
    val pages: List<Long>,
    @SerializedName("translated_name") val translatedName: TranslatedName
)

enum class RevelationPlace(val value: String) {
    Madinah("madinah"),
    Makkah("makkah");

    companion object {
        fun fromValue(value: String): RevelationPlace = when (value) {
            "madinah" -> Madinah
            "makkah"  -> Makkah
            else      -> throw IllegalArgumentException()
        }
    }
}