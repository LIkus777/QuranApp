package com.zaur.domain.al_quran_cloud.models.translate

import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.zaur.domain.base.SajdaAdapter

/**
* @author Zaur
* @since 2025-05-12
*/

interface TranslationsChapterAqc {

    fun translations(): Translation.Base

    fun <T> map(mapper: Mapper<T>): T

    class Base(
        @SerializedName("code") private val code: Long,
        @SerializedName("status") private val status: String,
        @SerializedName("data") private val translations: Translation.Base,
    ) : TranslationsChapterAqc {
        override fun translations(): Translation.Base = translations

        override fun <T> map(mapper: Mapper<T>): T = mapper.map(code, status, translations)
    }

    interface Mapper<T> {
        fun map(
            code: Long,
            status: String,
            translations: Translation,
        ): T
    }
}

interface Translation {

    fun number(): Long
    fun name(): String
    fun englishName(): String
    fun englishNameTranslation(): String
    fun revelationType(): String
    fun numberOfAyahs(): Long
    fun translationAyahs(): List<Ayah.Base>
    fun edition(): EditionTranslation
    fun translator(): String

    fun <T> map(mapper: Mapper<T>): T

    fun withTranslator(newTranslator: String): Base

    data class Base(
        @SerializedName("number") private val number: Long,
        @SerializedName("name") private val name: String,
        @SerializedName("englishName") private val englishName: String,
        @SerializedName("englishNameTranslation") private val englishNameTranslation: String,
        @SerializedName("revelationType") private val revelationType: String,
        @SerializedName("numberOfAyahs") private val numberOfAyahs: Long,
        @SerializedName("ayahs") private val ayahs: List<Ayah.Base>,
        @SerializedName("edition") private val edition: EditionTranslation.Base,
        private val translator: String,
    ) : Translation {

        override fun number() = number
        override fun name() = name
        override fun englishName() = englishName
        override fun englishNameTranslation() = englishNameTranslation
        override fun revelationType() = revelationType
        override fun numberOfAyahs() = numberOfAyahs
        override fun translationAyahs() = ayahs
        override fun edition() = edition
        override fun translator() = translator

        override fun <T> map(mapper: Mapper<T>): T = mapper.map(
            number,
            name,
            englishName,
            englishNameTranslation,
            revelationType,
            numberOfAyahs,
            ayahs,
            edition,
            translator
        )

        override fun withTranslator(newTranslator: String): Base {
            return Base(
                number = this.number,
                name = this.name,
                englishName = this.englishName,
                englishNameTranslation = this.englishNameTranslation,
                revelationType = this.revelationType,
                numberOfAyahs = this.numberOfAyahs,
                ayahs = this.ayahs,
                edition = this.edition,
                translator = newTranslator // заменяем значение translator
            )
        }
    }

    interface Mapper<T> {
        fun map(
            number: Long,
            name: String,
            englishName: String,
            englishNameTranslation: String,
            revelationType: String,
            numberOfAyahs: Long,
            ayahs: List<Ayah>,
            edition: EditionTranslation,
            translator: String,
        ): T
    }

    object Empty : Translation {
        override fun number(): Long = 0
        override fun name(): String = ""
        override fun englishName(): String = ""
        override fun englishNameTranslation(): String = ""
        override fun revelationType(): String = ""
        override fun numberOfAyahs(): Long = 0
        override fun translationAyahs(): List<Ayah.Base> = emptyList()
        override fun edition(): EditionTranslation = EditionTranslation.Empty
        override fun translator(): String = ""
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(
            number(),
            name(),
            englishName(),
            englishNameTranslation(),
            revelationType(),
            numberOfAyahs(),
            translationAyahs(),
            edition(),
            translator()
        )

        override fun withTranslator(newTranslator: String): Base = throw IllegalStateException()
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
    }
}

interface EditionTranslation {

    fun identifier(): String
    fun language(): String
    fun name(): String
    fun englishName(): String
    fun format(): String
    fun type(): String
    fun direction(): String?

    fun <T> map(mapper: Mapper<T>): T

    data class Base(
        @SerializedName("identifier") private val identifier: String,
        @SerializedName("language") private val language: String,
        @SerializedName("name") private val name: String,
        @SerializedName("englishName") private val englishName: String,
        @SerializedName("format") private val format: String,
        @SerializedName("type") private val type: String,
        @SerializedName("direction") private val direction: String,
    ) : EditionTranslation {

        override fun identifier() = identifier
        override fun language() = language
        override fun name() = name
        override fun englishName() = englishName
        override fun format() = format
        override fun type() = type
        override fun direction() = direction

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
    }

    object Empty : EditionTranslation {
        override fun identifier(): String = ""
        override fun language(): String = ""
        override fun name(): String = ""
        override fun englishName(): String = ""
        override fun format(): String = ""
        override fun type(): String = ""
        override fun direction(): String? = null

        override fun <T> map(mapper: Mapper<T>): T = mapper.map(
            identifier(), language(), name(), englishName(), format(), type(), direction().toString()
        )
    }
}