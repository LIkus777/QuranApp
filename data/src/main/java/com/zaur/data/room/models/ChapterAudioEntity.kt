package com.zaur.data.room.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.zaur.domain.al_quran_cloud.models.audiofile.Ayah
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudioFile
import com.zaur.domain.al_quran_cloud.models.audiofile.EditionAudio
import com.zaur.domain.base.SajdaAdapter

interface ChapterAudioEntity {

    fun <T> map(mapper: Mapper<T>): T

    @Entity(tableName = "chapter_audio")
    data class Base(
        @PrimaryKey @SerializedName("number") val number: Long,
        @SerializedName("name") val name: String,
        @SerializedName("englishName") val englishName: String,
        @SerializedName("englishNameTranslation") val englishNameTranslation: String,
        @SerializedName("revelationType") val revelationType: String,
        @SerializedName("numberOfAyahs") val numberOfAyahs: Long,
        @SerializedName("ayahs") val ayahs: List<AyahAudioEntity>,
        @SerializedName("edition") val edition: EditionAudioEntity,
        val reciter: String,
    ) : ChapterAudioEntity {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(
            number,
            name,
            englishName,
            englishNameTranslation,
            revelationType,
            numberOfAyahs,
            ayahs,
            edition,
            reciter
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
            ayahs: List<AyahAudioEntity>,
            edition: EditionAudioEntity,
            reciter: String,
        ): T

        class ToDomain : Mapper<ChapterAudioFile> {
            override fun map(
                number: Long,
                name: String,
                englishName: String,
                englishNameTranslation: String,
                revelationType: String,
                numberOfAyahs: Long,
                ayahs: List<AyahAudioEntity>,
                edition: EditionAudioEntity,
                reciter: String,
            ): ChapterAudioFile = ChapterAudioFile.Base(
                number,
                name,
                englishName,
                englishNameTranslation,
                revelationType,
                numberOfAyahs,
                ayahs.map { it.map(AyahAudioEntity.Mapper.ToDomain()) },
                edition.map(EditionAudioEntity.Mapper.ToDomain()),
                reciter
            )
        }
    }
}


interface AyahAudioEntity {

    fun <T> map(mapper: Mapper<T>): T

    data class Base(
        val reciter: String,
        @SerializedName("number") val verseNumber: Long,
        @SerializedName("audio") val audio: String,
        @SerializedName("audioSecondary") val audioSecondary: List<String>,
        @SerializedName("text") val text: String,
        @SerializedName("numberInSurah") val numberInSurah: Long,
        @SerializedName("juz") val juz: Long,
        @SerializedName("manzil") val manzil: Long,
        @SerializedName("page") val page: Long,
        @SerializedName("ruku") val ruku: Long,
        @SerializedName("hizbQuarter") val hizbQuarter: Long,
        @JsonAdapter(SajdaAdapter::class) @SerializedName("sajda") val sajda: Boolean,
    ) : AyahAudioEntity {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(
            reciter,
            verseNumber,
            audio,
            audioSecondary,
            text,
            numberInSurah,
            juz,
            manzil,
            page,
            ruku,
            hizbQuarter,
            sajda
        )
    }

    interface Mapper<T> {
        fun map(
            reciter: String,
            verseNumber: Long,
            audio: String,
            audioSecondary: List<String>,
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
                reciter: String,
                verseNumber: Long,
                audio: String,
                audioSecondary: List<String>,
                text: String,
                numberInSurah: Long,
                juz: Long,
                manzil: Long,
                page: Long,
                ruku: Long,
                hizbQuarter: Long,
                sajda: Boolean,
            ): Ayah = Ayah.Base(
                reciter,
                verseNumber,
                audio,
                audioSecondary,
                text,
                numberInSurah,
                juz,
                manzil,
                page,
                ruku,
                hizbQuarter,
                sajda
            )
        }
    }
}

interface EditionAudioEntity {

    fun <T> map(mapper: Mapper<T>): T

    data class Base(
        @PrimaryKey @SerializedName("identifier") val identifier: String,
        @SerializedName("language") val language: String,
        @SerializedName("name") val name: String,
        @SerializedName("englishName") val englishName: String,
        @SerializedName("format") val format: String,
        @SerializedName("type") val type: String,
        @SerializedName("direction") val direction: String? = null,
    ) : EditionAudioEntity {
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
            direction: String?,
        ): T

        class ToDomain : Mapper<EditionAudio> {
            override fun map(
                identifier: String,
                language: String,
                name: String,
                englishName: String,
                format: String,
                type: String,
                direction: String?,
            ): EditionAudio = EditionAudio.Base(
                identifier, language, name, englishName, format, type, direction
            )
        }
    }
}