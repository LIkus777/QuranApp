package com.zaur.domain.al_quran_cloud.models.chapter

import com.google.gson.annotations.SerializedName

data class ChaptersAqc(
    @SerializedName("code") val code: Long,
    @SerializedName("status") val status: String,
    @SerializedName("data") val chapters: List<ChapterAqc>
)

data class ChapterAqc(
    @SerializedName("number") val number: Long,
    @SerializedName("name") val name: String,
    @SerializedName("englishName") val englishName: String,
    @SerializedName("englishNameTranslation") val englishNameTranslation: String,
    @SerializedName("numberOfAyahs") val numberOfAyahs: Long,
    @SerializedName("revelationType") val revelationType: RevelationType
)

enum class RevelationType(val value: String) {
    Meccan("Meccan"), Medinan("Medinan");

    companion object {
        fun fromValue(value: String): RevelationType = when (value) {
            "Meccan" -> Meccan
            "Medinan" -> Medinan
            else -> throw IllegalArgumentException()
        }
    }
}