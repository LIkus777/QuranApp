package com.zaur.domain.al_quran_cloud.models.chapter

import com.google.gson.annotations.SerializedName

interface ChaptersAqc {

    fun <T> map(mapper: Mapper<T>): T

    class Base(
        @SerializedName("code") private val code: Long,
        @SerializedName("status") private val status: String,
        @SerializedName("data") private val chapters: List<ChapterAqc>
    ) : ChaptersAqc {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(code, status, chapters)
    }

    interface Mapper<T> {
        fun map(
            code: Long,
            status: String,
            chapters: List<ChapterAqc>
        ): T

        class Chapters : Mapper<List<ChapterAqc>> {
            override fun map(
                code: Long,
                status: String,
                chapters: List<ChapterAqc>,
            ): List<ChapterAqc> = chapters
        }
    }
}

interface ChapterAqc {

    fun <T> map(mapper: Mapper<T>): T

    class Base(
        @SerializedName("number") private val number: Long,
        @SerializedName("name") private val name: String,
        @SerializedName("englishName") private val englishName: String,
        @SerializedName("englishNameTranslation") private val englishNameTranslation: String,
        @SerializedName("numberOfAyahs") private val numberOfAyahs: Long,
        @SerializedName("revelationType") private val revelationType: RevelationType
    ) : ChapterAqc {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(
            number,
            name,
            englishName,
            englishNameTranslation,
            numberOfAyahs,
            revelationType
        )
    }

    interface Mapper<T> {
        fun map(
            number: Long,
            name: String,
            englishName: String,
            englishNameTranslation: String,
            numberOfAyahs: Long,
            revelationType: RevelationType
        ): T
    }
}

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