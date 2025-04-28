package com.zaur.domain.al_quran_cloud.models.translate

import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.zaur.domain.base.SajdaAdapter

interface TranslationsChapterAqc {

    fun <T> map(mapper: Mapper<T>): T

    class Base(
        @SerializedName("code") private val code: Long,
        @SerializedName("status") private val status: String,
        @SerializedName("data") private val translations: TranslationAqc,
    ) : TranslationsChapterAqc {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(code, status, translations)
    }

    interface Mapper<T> {
        fun map(
            code: Long,
            status: String,
            translations: TranslationAqc,
        ): T

        class Translations() : Mapper<TranslationAqc> {
            override fun map(
                code: Long,
                status: String,
                translations: TranslationAqc,
            ): TranslationAqc = translations
        }
    }
}

interface TranslationAqc {

    fun <T> map(mapper: Mapper<T>): T

    fun withTranslator(newTranslator: String): Base

    class Base(
        @SerializedName("number") private val number: Long,
        @SerializedName("name") private val name: String,
        @SerializedName("englishName") private val englishName: String,
        @SerializedName("englishNameTranslation") private val englishNameTranslation: String,
        @SerializedName("revelationType") private val revelationType: String,
        @SerializedName("numberOfAyahs") private val numberOfAyahs: Long,
        @SerializedName("ayahs") private val ayahs: List<Ayah>,
        @SerializedName("edition") private val edition: EditionTranslation,
        private val translator: String,
    ) : TranslationAqc {
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
}

interface Ayah {

    fun <T> map(mapper: Mapper<T>): T

    class Base(
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

    fun <T> map(mapper: Mapper<T>): T

    class Base(
        @SerializedName("identifier") private val identifier: String,
        @SerializedName("language") private val language: String,
        @SerializedName("name") private val name: String,
        @SerializedName("englishName") private val englishName: String,
        @SerializedName("format") private val format: String,
        @SerializedName("type") private val type: String,
        @SerializedName("direction") private val direction: String,
    ) : EditionTranslation {
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
}