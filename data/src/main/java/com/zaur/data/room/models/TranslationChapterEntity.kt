package com.zaur.data.room.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.zaur.data.room.converters.GenericConverters
import com.zaur.domain.al_quran_cloud.models.translate.Ayah
import com.zaur.domain.al_quran_cloud.models.translate.EditionTranslation
import com.zaur.domain.al_quran_cloud.models.translate.TranslationAqc
import com.zaur.domain.base.SajdaAdapter

/**
* @author Zaur
* @since 2025-05-12
*/

interface TranslationEntity {

    fun <T> map(mapper: Mapper<T>): T

    @Entity(tableName = "translation_chapter")
    data class Base(
        @PrimaryKey val number: Long,
        @SerializedName("name") val name: String,
        @SerializedName("englishName") val englishName: String,
        @SerializedName("englishNameTranslation") val englishNameTranslation: String,
        @SerializedName("revelationType") val revelationType: String,
        @SerializedName("numberOfAyahs") val numberOfAyahs: Long,
        @TypeConverters(GenericConverters::class) @SerializedName("ayahs") val translationAyahEntity: List<TranslationAyahEntity.Base>,
        @TypeConverters(GenericConverters::class) @SerializedName("edition") val edition: EditionTranslationEntity.Base,
        val translator: String,
    ) : TranslationEntity {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(
            number,
            name,
            englishName,
            englishNameTranslation,
            revelationType,
            numberOfAyahs,
            translationAyahEntity,
            edition,
            translator
        )
    }

    interface Mapper<T> {
        fun map(
            number: Long,
            name: String,
            englishName: String,
            englishNameTranslation: String,
            revelationType: String,
            numberOfAyahs: Long,
            translationAyahEntity: List<TranslationAyahEntity>,
            edition: EditionTranslationEntity,
            translator: String,
        ): T
    }
}

interface TranslationAyahEntity {

    fun <T> map(mapper: Mapper<T>): T

    data class Base(
        @PrimaryKey val number: Long,
        @SerializedName("text") val text: String,
        @SerializedName("numberInSurah") val numberInSurah: Long,
        @SerializedName("juz") val juz: Long,
        @SerializedName("manzil") val manzil: Long,
        @SerializedName("page") val page: Long,
        @SerializedName("ruku") val ruku: Long,
        @SerializedName("hizbQuarter") val hizbQuarter: Long,
        @JsonAdapter(SajdaAdapter::class) @SerializedName("sajda") val sajda: Boolean,
    ) : TranslationAyahEntity {
        override fun <T> map(mapper: Mapper<T>): T =
            mapper.map(number, text, numberInSurah, juz, manzil, page, ruku, hizbQuarter, sajda)
    }

    interface Mapper<T> {
        fun map(
            number: Long,
            text: String,
            numberInSurah: Long,
            juz: Long,
            manzil: Long,
            page: Long,
            ruku: Long,
            hizbQuarter: Long,
            sajda: Boolean,
        ): T

        class ToDomain : Mapper<Ayah> {
            override fun map(
                number: Long,
                text: String,
                numberInSurah: Long,
                juz: Long,
                manzil: Long,
                page: Long,
                ruku: Long,
                hizbQuarter: Long,
                sajda: Boolean,
            ): Ayah {
                return Ayah.Base(
                    number, text, numberInSurah, juz, manzil, page, ruku, hizbQuarter, sajda
                )
            }
        }
    }

}


interface EditionTranslationEntity {

    fun <T> map(mapper: Mapper<T>): T

    data class Base(
        @PrimaryKey val identifier: String,
        @SerializedName("language") val language: String,
        @SerializedName("name") val name: String,
        @SerializedName("englishName") val englishName: String,
        @SerializedName("format") val format: String,
        @SerializedName("type") val type: String,
        @SerializedName("direction") val direction: String?,
    ) : EditionTranslationEntity {

        override fun <T> map(mapper: Mapper<T>): T =
            mapper.map(identifier, language, name, englishName, format, type, direction)

    }

    interface Mapper<T> {
        fun map(
            identifier: String,
            language: String,
            name: String,
            englishName: String,
            format: String,
            type: String,
            direction: String?,
        ): T

        class ToDomain : Mapper<EditionTranslation> {
            override fun map(
                identifier: String,
                language: String,
                name: String,
                englishName: String,
                format: String,
                type: String,
                direction: String?,
            ): EditionTranslation {
                return EditionTranslation.Base(
                    identifier, language, name, englishName, format, type, direction.toString()
                )
            }
        }
    }
}