package com.zaur.domain.al_quran_cloud.models.page


/**
 * @author Zaur
 * @since 12.05.2025
 */

interface QuranPageAqc {
    fun code(): Long
    fun status(): String
    fun data(): QuranPage

    data class Base(
        private val code: Long,
        private val status: String,
        private val data: QuranPage,
    ) : QuranPageAqc {
        override fun code() = code
        override fun status() = status
        override fun data() = data
    }
}

interface QuranPage {
    fun number(): Long
    fun ayahs(): List<Ayah>
    fun surahs(): Map<String, Surah>
    fun edition(): Edition

    data class Base(
        private val number: Long,
        private val ayahs: List<Ayah>,
        private val surahs: Map<String, Surah>,
        private val edition: Edition,
    ) : QuranPage {
        override fun number() = number
        override fun ayahs() = ayahs
        override fun surahs() = surahs
        override fun edition() = edition
    }
}

interface Ayah {
    fun number(): Long
    fun text(): String
    fun surah(): Surah
    fun numberInSurah(): Long
    fun juz(): Long
    fun manzil(): Long
    fun page(): Long
    fun ruku(): Long
    fun hizbQuarter(): Long
    fun sajda(): Boolean

    data class Base(
        private val number: Long,
        private val text: String,
        private val surah: Surah,
        private val numberInSurah: Long,
        private val juz: Long,
        private val manzil: Long,
        private val page: Long,
        private val ruku: Long,
        private val hizbQuarter: Long,
        private val sajda: Boolean,
    ) : Ayah {
        override fun number() = number
        override fun text() = text
        override fun surah() = surah
        override fun numberInSurah() = numberInSurah
        override fun juz() = juz
        override fun manzil() = manzil
        override fun page() = page
        override fun ruku() = ruku
        override fun hizbQuarter() = hizbQuarter
        override fun sajda() = sajda
    }
}

interface Surah {
    fun number(): Long
    fun name(): String
    fun englishName(): String
    fun englishNameTranslation(): String
    fun revelationType(): String
    fun numberOfAyahs(): Long

    data class Base(
        private val number: Long,
        private val name: String,
        private val englishName: String,
        private val englishNameTranslation: String,
        private val revelationType: String,
        private val numberOfAyahs: Long,
    ) : Surah {
        override fun number() = number
        override fun name() = name
        override fun englishName() = englishName
        override fun englishNameTranslation() = englishNameTranslation
        override fun revelationType() = revelationType
        override fun numberOfAyahs() = numberOfAyahs
    }
}

interface Edition {
    fun identifier(): String
    fun language(): String
    fun name(): String
    fun englishName(): String
    fun format(): String
    fun type(): String
    fun direction(): String

    data class Base(
        private val identifier: String,
        private val language: String,
        private val name: String,
        private val englishName: String,
        private val format: String,
        private val type: String,
        private val direction: String,
    ) : Edition {
        override fun identifier() = identifier
        override fun language() = language
        override fun name() = name
        override fun englishName() = englishName
        override fun format() = format
        override fun type() = type
        override fun direction() = direction
    }
}