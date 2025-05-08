package com.zaur.data.room.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.zaur.data.room.converters.GenericConverters
import com.zaur.domain.al_quran_cloud.models.chapter.ChapterAqc
import com.zaur.domain.al_quran_cloud.models.chapter.RevelationType

interface ChapterEntity {

    fun <T> map(mapper: Mapper<T>): T

    @Entity(tableName = "chapters")
    data class Base(
        @PrimaryKey val number: Long,
        @SerializedName("name") val name: String,
        @SerializedName("englishName") val englishName: String,
        @SerializedName("englishNameTranslation") val englishNameTranslation: String,
        @SerializedName("numberOfAyahs") val numberOfAyahs: Long,
        @TypeConverters(GenericConverters::class) @SerializedName("revelationType") val revelationType: RevelationType,
    ) : ChapterEntity {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(
            number, name, englishName, englishNameTranslation, numberOfAyahs, revelationType
        )
    }

    interface Mapper<T> {
        fun map(
            number: Long,
            name: String,
            englishName: String,
            englishNameTranslation: String,
            numberOfAyahs: Long,
            revelationType: RevelationType,
        ): T

        class ToDomain : Mapper<ChapterAqc> {
            override fun map(
                number: Long,
                name: String,
                englishName: String,
                englishNameTranslation: String,
                numberOfAyahs: Long,
                revelationType: RevelationType,
            ): ChapterAqc = ChapterAqc.Base(
                number, name, englishName, englishNameTranslation, numberOfAyahs, revelationType
            )
        }

        class ToData : Mapper<ChapterEntity> {
            override fun map(
                number: Long,
                name: String,
                englishName: String,
                englishNameTranslation: String,
                numberOfAyahs: Long,
                revelationType: RevelationType,
            ): ChapterEntity = Base(
                number, name, englishName, englishNameTranslation, numberOfAyahs, revelationType
            )
        }
    }
}