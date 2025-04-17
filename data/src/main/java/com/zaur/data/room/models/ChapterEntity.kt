package com.zaur.data.room.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.zaur.data.room.converters.GenericConverters
import com.zaur.domain.al_quran_cloud.models.chapter.ChapterAqc

@Entity(tableName = "chapters")
data class ChapterEntity(
    @PrimaryKey @SerializedName("number") val number: Long,
    @SerializedName("name") val name: String,
    @SerializedName("englishName") val englishName: String,
    @SerializedName("englishNameTranslation") val englishNameTranslation: String,
    @SerializedName("numberOfAyahs") val numberOfAyahs: Long,
    @TypeConverters(GenericConverters::class) @SerializedName("revelationType") val revelationType: RevelationType
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

//todo переписать на нормальный ооп маппер
fun ChapterAqc.toData(): ChapterEntity {
    return ChapterEntity(
        number,
        name,
        englishName,
        englishNameTranslation,
        numberOfAyahs,
        revelationType = RevelationType.fromValue(revelationType.value)
    )
}
