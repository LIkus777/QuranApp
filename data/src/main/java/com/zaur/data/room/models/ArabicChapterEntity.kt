package com.zaur.data.room.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.zaur.data.room.converters.GenericConverters
import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChapter
import com.zaur.domain.al_quran_cloud.models.arabic.Ayah
import com.zaur.domain.al_quran_cloud.models.arabic.EditionArabic
import com.zaur.domain.base.SajdaAdapter

interface ArabicChapterEntity {

    fun <T> map(mapper: Mapper<T>): T

    @Entity(tableName = "arabic_chapters")
    data class Base(
        @PrimaryKey @SerializedName("number") private val number: Long,
        @SerializedName("name") private val name: String,
        @SerializedName("englishName") private val englishName: String,
        @SerializedName("englishNameTranslation") private val englishNameTranslation: String,
        @SerializedName("revelationType") private val revelationType: String,
        @SerializedName("numberOfAyahs") private val numberOfAyahs: Long,
        @TypeConverters(GenericConverters::class) @SerializedName("ayahs") private val ayahs: List<ArabicAyahEntity>,
        @TypeConverters(GenericConverters::class) @SerializedName("edition") private val edition: EditionArabicEntity,
    ) : ArabicChapterEntity {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(
            number,
            name,
            englishName,
            englishNameTranslation,
            revelationType,
            numberOfAyahs,
            ayahs,
            edition
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
            ayahs: List<ArabicAyahEntity>,
            edition: EditionArabicEntity,
        ): T

        class ToDomain : Mapper<ArabicChapter> {
            override fun map(
                number: Long,
                name: String,
                englishName: String,
                englishNameTranslation: String,
                revelationType: String,
                numberOfAyahs: Long,
                ayahs: List<ArabicAyahEntity>,
                edition: EditionArabicEntity,
            ): ArabicChapter {
                return ArabicChapter.Base(
                    number,
                    name,
                    englishName,
                    englishNameTranslation,
                    revelationType,
                    numberOfAyahs,
                    ayahs.map { it.map(ArabicAyahEntity.Mapper.ToDomain()) },
                    edition.map(EditionArabicEntity.Mapper.ToDomain())
                )
            }
        }
    }

}

interface ArabicAyahEntity {

    fun <T> map(mapper: Mapper<T>): T

    data class Base(
        @SerializedName("number") private val number: Long,
        @SerializedName("text") private val text: String,
        @SerializedName("numberInSurah") private val numberInSurah: Long,
        @SerializedName("juz") private val juz: Long,
        @SerializedName("manzil") private val manzil: Long,
        @SerializedName("page") private val page: Long,
        @SerializedName("ruku") private val ruku: Long,
        @SerializedName("hizbQuarter") private val hizbQuarter: Long,
        @JsonAdapter(SajdaAdapter::class) @SerializedName("sajda") private val sajda: Boolean,
    ) : ArabicAyahEntity {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(
            number, text, numberInSurah, juz, manzil, page, ruku, hizbQuarter, sajda
        )
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

interface EditionArabicEntity {

    fun <T> map(mapper: Mapper<T>): T

    data class Base(
        @SerializedName("identifier") private val identifier: String,
        @SerializedName("language") private val language: String,
        @SerializedName("name") private val name: String,
        @SerializedName("englishName") private val englishName: String,
        @SerializedName("format") private val format: String,
        @SerializedName("type") private val type: String,
        @SerializedName("direction") private val direction: String,
    ) : EditionArabicEntity {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(
            identifier, language, name, englishName, format, type, direction
        )
    }

    interface Mapper<T> {
        fun map(
            identifier: String,
            language: String,
            name: String,
            englishName: String,
            format: String,
            type: String,
            direction: String,
        ): T

        class ToDomain : Mapper<EditionArabic> {
            override fun map(
                identifier: String,
                language: String,
                name: String,
                englishName: String,
                format: String,
                type: String,
                direction: String,
            ): EditionArabic {
                return EditionArabic.Base(
                    identifier, language, name, englishName, format, type, direction
                )
            }
        }
    }
}