package com.zaur.domain.al_quran_cloud.models.page

import com.google.gson.annotations.SerializedName

/**
 * @author Zaur
 * @since 12.05.2025
 */

interface QuranPageAqc {
    fun code(): Long
    fun status(): String
    fun page(): QuranPage

    data class Base(
        @SerializedName("code") private val code: Long,
        @SerializedName("status") private val status: String,
        @SerializedName("data") private val page: QuranPage.Base,
    ) : QuranPageAqc {
        override fun code() = code
        override fun status() = status
        override fun page() = page
    }

    object Empty : QuranPageAqc {
        override fun code(): Long = 0L
        override fun status(): String = ""
        override fun page(): QuranPage = QuranPage.Empty
    }
}

interface QuranPage {
    fun number(): Long
    fun ayahs(): List<Ayah>
    fun surahs(): Map<String, Surah>
    fun edition(): Edition

    data class Base(
        @SerializedName("number") private val number: Long,
        @SerializedName("ayahs") private val ayahs: List<Ayah.Base>,
        @SerializedName("surahs") private val surahs: Map<String, Surah.Base>,
        @SerializedName("edition") private val edition: Edition.Base,
    ) : QuranPage {
        override fun number() = number
        override fun ayahs() = ayahs
        override fun surahs() = surahs
        override fun edition() = edition
    }

    object Empty : QuranPage {
        override fun number(): Long = 0L
        override fun ayahs(): List<Ayah> = emptyList()
        override fun surahs(): Map<String, Surah> = emptyMap()
        override fun edition(): Edition = Edition.Empty
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
        @SerializedName("number") private val number: Long,
        @SerializedName("text") private val text: String,
        @SerializedName("surah") private val surah: Surah.Base,
        @SerializedName("numberInSurah") private val numberInSurah: Long,
        @SerializedName("juz") private val juz: Long,
        @SerializedName("manzil") private val manzil: Long,
        @SerializedName("page") private val page: Long,
        @SerializedName("ruku") private val ruku: Long,
        @SerializedName("hizbQuarter") private val hizbQuarter: Long,
        @SerializedName("sajda") private val sajda: Boolean,
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

    object Empty : Ayah {
        override fun number(): Long = 0L
        override fun text(): String = ""
        override fun surah(): Surah = Surah.Empty
        override fun numberInSurah(): Long = 0L
        override fun juz(): Long = 0L
        override fun manzil(): Long = 0L
        override fun page(): Long = 0L
        override fun ruku(): Long = 0L
        override fun hizbQuarter(): Long = 0L
        override fun sajda(): Boolean = false
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
        @SerializedName("number") private val number: Long,
        @SerializedName("name") private val name: String,
        @SerializedName("englishName") private val englishName: String,
        @SerializedName("englishNameTranslation") private val englishNameTranslation: String,
        @SerializedName("revelationType") private val revelationType: String,
        @SerializedName("numberOfAyahs") private val numberOfAyahs: Long,
    ) : Surah {
        override fun number() = number
        override fun name() = name
        override fun englishName() = englishName
        override fun englishNameTranslation() = englishNameTranslation
        override fun revelationType() = revelationType
        override fun numberOfAyahs() = numberOfAyahs
    }

    object Empty : Surah {
        override fun number(): Long = 0L
        override fun name(): String = ""
        override fun englishName(): String = ""
        override fun englishNameTranslation(): String = ""
        override fun revelationType(): String = ""
        override fun numberOfAyahs(): Long = 0L
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
        @SerializedName("identifier") private val identifier: String,
        @SerializedName("language") private val language: String,
        @SerializedName("name") private val name: String,
        @SerializedName("englishName") private val englishName: String,
        @SerializedName("format") private val format: String,
        @SerializedName("type") private val type: String,
        @SerializedName("direction") private val direction: String,
    ) : Edition {
        override fun identifier() = identifier
        override fun language() = language
        override fun name() = name
        override fun englishName() = englishName
        override fun format() = format
        override fun type() = type
        override fun direction() = direction
    }

    object Empty : Edition {
        override fun identifier(): String = ""
        override fun language(): String = ""
        override fun name(): String = ""
        override fun englishName(): String = ""
        override fun format(): String = ""
        override fun type(): String = ""
        override fun direction(): String = ""
    }
}