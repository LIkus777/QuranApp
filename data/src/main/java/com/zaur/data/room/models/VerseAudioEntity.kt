package com.zaur.data.room.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import androidx.room.TypeConverters
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.zaur.data.room.converters.GenericConverters
import com.zaur.domain.al_quran_cloud.models.audiofile.EditionVerse
import com.zaur.domain.al_quran_cloud.models.audiofile.Surah
import com.zaur.domain.al_quran_cloud.models.audiofile.VerseAudioAqc
import com.zaur.domain.base.SajdaAdapter

interface VerseAudioWithSurah {

    fun <T> map(mapper: Mapper<T>): T

    class Base(
        @Embedded val verse: VerseAudioEntity.Base,
        @Relation(
            parentColumn = "surahNumber", entityColumn = "number"
        ) val surah: SurahEntity.Base,
    ) : VerseAudioWithSurah {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(verse, surah)
    }

    interface Mapper<T> {
        fun map(
            verse: VerseAudioEntity.Base,
            surah: SurahEntity.Base,
        ): T
    }
}

interface VerseAudioEntity {

    fun <T> map(mapper: Mapper<T>): T

    @Entity(
        tableName = "verse_audio",
        foreignKeys = [ForeignKey(
            entity = SurahEntity.Base::class,
            parentColumns = ["number"],
            childColumns = ["surahNumber"],
            onDelete = ForeignKey.CASCADE
        )],
        indices = [Index("surahNumber")],
        primaryKeys = ["surahNumber", "verseNumber", "reciter"]
    )
    data class Base(
        @ColumnInfo(name = "surahNumber") val surahNumber: Long,
        private val verseNumber: Long,
        private val reciter: String,
        @SerializedName("audio") private val audio: String,
        @SerializedName("audioSecondary") private val audioSecondary: List<String>,
        @SerializedName("text") private val text: String,
        @TypeConverters(GenericConverters::class) @SerializedName("edition") private val edition: EditionVerseEntity,
        @SerializedName("numberInSurah") private val numberInSurah: Long,
        @SerializedName("juz") private val juz: Long,
        @SerializedName("manzil") private val manzil: Long,
        @SerializedName("page") private val page: Long,
        @SerializedName("ruku") private val ruku: Long,
        @SerializedName("hizbQuarter") private val hizbQuarter: Long,
        @JsonAdapter(SajdaAdapter::class) @SerializedName("sajda") private val sajda: Boolean,
    ) : VerseAudioEntity {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(
            surahNumber,
            verseNumber,
            reciter,
            audio,
            audioSecondary,
            text,
            edition,
            numberInSurah,
            juz,
            manzil,
            page,
            ruku,
            hizbQuarter,
            sajda
        )
    }

    // Интерфейс для маппинга VerseAudioEntity.Base
    interface Mapper<T> {
        fun map(
            surahNumber: Long,
            verseNumber: Long,
            reciter: String,
            audio: String,
            audioSecondary: List<String>,
            text: String,
            edition: EditionVerseEntity,
            numberInSurah: Long,
            juz: Long,
            manzil: Long,
            page: Long,
            ruku: Long,
            hizbQuarter: Long,
            sajda: Boolean,
        ): T

        // Реализация маппинга в VerseAudioAqc
        class ToDomain(private val surah: Surah) : Mapper<VerseAudioAqc> {
            override fun map(
                surahNumber: Long,
                verseNumber: Long,
                reciter: String,
                audio: String,
                audioSecondary: List<String>,
                text: String,
                edition: EditionVerseEntity,
                numberInSurah: Long,
                juz: Long,
                manzil: Long,
                page: Long,
                ruku: Long,
                hizbQuarter: Long,
                sajda: Boolean,
            ): VerseAudioAqc {
                return VerseAudioAqc.Base(
                    verseNumber,
                    reciter,
                    audio,
                    audioSecondary,
                    text,
                    edition.map(EditionVerseEntity.Mapper.ToDomain()),
                    surah, // Теперь мы используем переданный Surah
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
}

interface EditionVerseEntity {

    fun <T> map(mapper: Mapper<T>): T

    data class Base(
        @SerializedName("identifier") private val identifier: String,
        @SerializedName("language") private val language: String,
        @SerializedName("name") private val name: String,
        @SerializedName("englishName") private val englishName: String,
        @SerializedName("format") private val format: String,
        @SerializedName("type") private val type: String,
        @SerializedName("direction") private val direction: String? = null,
    ) : EditionVerseEntity {
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

        class ToDomain : Mapper<EditionVerse> {
            override fun map(
                identifier: String,
                language: String,
                name: String,
                englishName: String,
                format: String,
                type: String,
                direction: String?,
            ): EditionVerse {
                return EditionVerse.Base(
                    identifier, language, name, englishName, format, type, direction
                )
            }
        }
    }
}

interface SurahEntity {

    fun <T> map(mapper: Mapper<T>): T

    @Entity(tableName = "surah")
    data class Base(
        @PrimaryKey private val number: Long,
        @SerializedName("name") private val name: String,
        @SerializedName("englishName") private val englishName: String,
        @SerializedName("englishNameTranslation") private val englishNameTranslation: String,
        @SerializedName("numberOfAyahs") private val numberOfAyahs: Long,
        @SerializedName("revelationType") private val revelationType: String,
    ) : SurahEntity {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(
            number, name, englishName, englishNameTranslation, numberOfAyahs, revelationType
        )
    }

    interface Mapper<T> {
        fun map(
            number: Long,
            name: String,
            englishName: String,
            englishNameTranslation: String,
            numberOfAyahs: Long,
            revelationType: String,
        ): T

        class ToDomain : Mapper<Surah> {
            override fun map(
                number: Long,
                name: String,
                englishName: String,
                englishNameTranslation: String,
                numberOfAyahs: Long,
                revelationType: String,
            ): Surah {
                return Surah.Base(
                    number, name, englishName, englishNameTranslation, numberOfAyahs, revelationType
                )
            }
        }
    }
}