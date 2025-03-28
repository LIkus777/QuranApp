package com.zaur.domain.al_quran_cloud.models.chapter

data class ChapterAqc(
    val code: Long, val status: String, val data: List<Datum>
)

data class Datum(
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
