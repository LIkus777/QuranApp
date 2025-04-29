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
        @PrimaryKey @SerializedName("number") private val number: Long,
        @SerializedName("name") private val name: String,
        @SerializedName("englishName") private val englishName: String,
        @SerializedName("englishNameTranslation") private val englishNameTranslation: String,
        @SerializedName("numberOfAyahs") private val numberOfAyahs: Long,
        @TypeConverters(GenericConverters::class) @SerializedName("revelationType") private val revelationType: RevelationType,
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
    }
}