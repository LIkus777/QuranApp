package com.zaur.domain.apiV4.models.chapter

import com.google.gson.annotations.SerializedName
import com.zaur.domain.apiV4.models.TranslatedNameV4

data class ChapterV4(
    val id: Long,
    @SerializedName("revelation_place") val revelationPlace: String,
    @SerializedName("revelation_order") val revelationOrder: Long,
    @SerializedName("bismillah_pre") val bismillahPre: Boolean,
    @SerializedName("name_simple") val nameSimple: String,
    @SerializedName("name_complex") val nameComplex: String,
    @SerializedName("name_arabic") val nameArabic: String,
    @SerializedName("verses_count") val versesCount: Long,
    val pages: List<Long>,
    @SerializedName("translated_name") val translatedNameV4: TranslatedNameV4
)

enum class RevelationPlaceV4(val value: String) {
    Madinah("madinah"),
    Makkah("makkah");

    companion object {
        fun fromValue(value: String): RevelationPlaceV4 = when (value) {
            "madinah" -> Madinah
            "makkah"  -> Makkah
            else      -> throw IllegalArgumentException()
        }
    }
}