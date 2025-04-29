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
        @PrimaryKey @SerializedName("number") private val number: Long,
        @SerializedName("name") private val name: String,
        @SerializedName("englishName") private val englishName: String,
        @SerializedName("englishNameTranslation") private val englishNameTranslation: String,
        @SerializedName("revelationType") private val revelationType: String,
        @SerializedName("numberOfAyahs") private val numberOfAyahs: Long,
        @SerializedName("ayahs") private val ayahs: List<AyahAudioEntity>,
        @SerializedName("edition") private val edition: EditionAudioEntity,
        private val reciter: String,
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
        private val reciter: String,
        @SerializedName("number") private val verseNumber: Long,
        @SerializedName("audio") private val audio: String,
        @SerializedName("audioSecondary") private val audioSecondary: List<String>,
        @SerializedName("text") private val text: String,
        @SerializedName("numberInSurah") private val numberInSurah: Long,
        @SerializedName("juz") private val juz: Long,
        @SerializedName("manzil") private val manzil: Long,
        @SerializedName("page") private val page: Long,
        @SerializedName("ruku") private val ruku: Long,
        @SerializedName("hizbQuarter") private val hizbQuarter: Long,
        @JsonAdapter(SajdaAdapter::class) @SerializedName("sajda") private val sajda: Boolean,
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
        @PrimaryKey @SerializedName("identifier") private val identifier: String,
        @SerializedName("language") private val language: String,
        @SerializedName("name") private val name: String,
        @SerializedName("englishName") private val englishName: String,
        @SerializedName("format") private val format: String,
        @SerializedName("type") private val type: String,
        @SerializedName("direction") private val direction: String? = null,
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