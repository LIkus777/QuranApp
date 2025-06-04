package com.zaur.domain.al_quran_cloud.models.arabic

import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.zaur.domain.base.SajdaAdapter

/**
 * @author Zaur
 * @since 2025-05-12
 */

interface ArabicChaptersAqc {

    fun arabicChapters(): DataWithSurahs.Base

    class Base(
        @SerializedName("code") private val code: Long,
        @SerializedName("status") private val status: String,
        //@SerializedName("data") private val arabicChapters: ArabicChapter.Base,
        @SerializedName("data") private val arabicChapters: DataWithSurahs.Base,
    ) : ArabicChaptersAqc {
        override fun arabicChapters(): DataWithSurahs.Base = arabicChapters
    }
}

interface DataWithSurahs {

    fun surahs(): List<ArabicChapter.Base>
    fun edition(): EditionArabic.Base

    class Base(
        @SerializedName("surahs") private val surahs: List<ArabicChapter.Base>,

        @SerializedName("edition") private val edition: EditionArabic.Base,
    ) : DataWithSurahs {
        override fun surahs(): List<ArabicChapter.Base> = surahs
        override fun edition(): EditionArabic.Base = edition
    }
}

interface ArabicChapter {

    fun number(): Long
    fun name(): String
    fun englishName(): String
    fun englishNameTranslation(): String
    fun revelationType(): String
    fun ayahs(): List<Ayah.Base>

    fun <T> map(mapper: Mapper<T>): T

    data class Base(
        @SerializedName("number") private val number: Long,
        @SerializedName("name") private val name: String,
        @SerializedName("englishName") private val englishName: String,
        @SerializedName("englishNameTranslation") private val englishNameTranslation: String,
        @SerializedName("revelationType") private val revelationType: String,
        @SerializedName("ayahs") private val ayahs: List<Ayah.Base>,
    ) : ArabicChapter {

        override fun number() = number
        override fun name() = name
        override fun englishName() = englishName
        override fun englishNameTranslation() = englishNameTranslation
        override fun revelationType() = revelationType
        override fun ayahs() = ayahs

        override fun <T> map(mapper: Mapper<T>): T = mapper.map(
            number, name, englishName, englishNameTranslation, revelationType, ayahs
        )
    }

    interface Mapper<T> {
        fun map(
            number: Long,
            name: String,
            englishName: String,
            englishNameTranslation: String,
            revelationType: String,
            ayahs: List<Ayah>,
        ): T
    }

    object Empty : ArabicChapter {
        override fun number(): Long = 0
        override fun name(): String = ""
        override fun englishName(): String = ""
        override fun englishNameTranslation(): String = ""
        override fun revelationType(): String = ""
        override fun ayahs(): List<Ayah.Base> = emptyList()
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(
            number(), name(), englishName(), englishNameTranslation(), revelationType(), ayahs()
        )
    }
}

interface Ayah {

    fun number(): Long
    fun text(): String
    fun numberInSurah(): Long
    fun juz(): Long
    fun manzil(): Long
    fun page(): Long
    fun ruku(): Long
    fun hizbQuarter(): Long
    fun sajda(): Boolean

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
    ) : Ayah {

        override fun number() = number
        override fun text() = text
        override fun numberInSurah() = numberInSurah
        override fun juz() = juz
        override fun manzil() = manzil
        override fun page() = page
        override fun ruku() = ruku
        override fun hizbQuarter() = hizbQuarter
        override fun sajda() = sajda

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
    }

    object Empty : Ayah {
        override fun number(): Long = 0
        override fun text(): String = ""
        override fun numberInSurah(): Long = 0
        override fun juz(): Long = 0
        override fun manzil(): Long = 0
        override fun page(): Long = 0
        override fun ruku(): Long = 0
        override fun hizbQuarter(): Long = 0
        override fun sajda(): Boolean = false

        override fun <T> map(mapper: Mapper<T>): T = mapper.map(
            number(),
            text(),
            numberInSurah(),
            juz(),
            manzil(),
            page(),
            ruku(),
            hizbQuarter(),
            sajda()
        )
    }
}

interface EditionArabic {

    fun identifier(): String
    fun language(): String
    fun name(): String
    fun englishName(): String
    fun format(): String
    fun type(): String

    fun <T> map(mapper: Mapper<T>): T

    data class Base(
        @SerializedName("identifier") private val identifier: String,
        @SerializedName("language") private val language: String,
        @SerializedName("name") private val name: String,
        @SerializedName("englishName") private val englishName: String,
        @SerializedName("format") private val format: String,
        @SerializedName("type") private val type: String,
    ) : EditionArabic {
        override fun identifier() = identifier
        override fun language() = language
        override fun name() = name
        override fun englishName() = englishName
        override fun format() = format
        override fun type() = type

        override fun <T> map(mapper: Mapper<T>): T =
            mapper.map(identifier, language, name, englishName, format, type)
    }

    interface Mapper<T> {
        fun map(
            identifier: String,
            language: String,
            name: String,
            englishName: String,
            format: String,
            type: String,
        ): T
    }

    object Empty : EditionArabic {
        override fun identifier(): String = ""
        override fun language(): String = ""
        override fun name(): String = ""
        override fun englishName(): String = ""
        override fun format(): String = ""
        override fun type(): String = ""

        override fun <T> map(mapper: Mapper<T>): T = mapper.map(
            identifier(), language(), name(), englishName(), format(), type()
        )
    }
}