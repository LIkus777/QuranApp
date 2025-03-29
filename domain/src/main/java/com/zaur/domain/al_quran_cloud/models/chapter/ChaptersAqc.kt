package com.zaur.domain.al_quran_cloud.models.chapter

import com.google.gson.annotations.SerializedName

data class ChaptersAqc(
    val code: Long, val status: String, @SerializedName("data") val chapters: List<ChapterAqc>
)

data class ChapterAqc(
    val number: Long,
    val name: String,
    val englishName: String,
    val englishNameTranslation: String,
    val numberOfAyahs: Long,
    val revelationType: RevelationType
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
