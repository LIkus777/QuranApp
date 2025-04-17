package com.zaur.data.room.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.zaur.domain.al_quran_cloud.models.audiofile.Ayah
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudioFile
import com.zaur.domain.al_quran_cloud.models.audiofile.EditionAudio
import com.zaur.domain.al_quran_cloud.models.chapter.RevelationType

@Entity(tableName = "chapter_audio")
data class ChapterAudioEntity(
    @PrimaryKey @SerializedName("number") val number: Long,
    @SerializedName("name") val name: String,
    @SerializedName("englishName") val englishName: String,
    @SerializedName("englishNameTranslation") val englishNameTranslation: String,
    @SerializedName("revelationType") val revelationType: String,
    @SerializedName("numberOfAyahs") val numberOfAyahs: Long,
    @SerializedName("ayahs") val ayahs: List<AyahAudioEntity>,
    @SerializedName("edition") val edition: EditionAudioEntity,
)

data class AyahAudioEntity(
    @PrimaryKey @SerializedName("number") val number: Long,
    @SerializedName("audio") val audio: String,
    @SerializedName("audioSecondary") val audioSecondary: List<String>,
    @SerializedName("text") val text: String,
    @SerializedName("numberInSurah") val numberInSurah: Long,
    @SerializedName("juz") val juz: Long,
    @SerializedName("manzil") val manzil: Long,
    @SerializedName("page") val page: Long,
    @SerializedName("ruku") val ruku: Long,
    @SerializedName("hizbQuarter") val hizbQuarter: Long,
    @SerializedName("sajda") val sajda: Boolean,
)

data class EditionAudioEntity(
    @PrimaryKey @SerializedName("identifier") val identifier: String,
    @SerializedName("language") val language: String,
    @SerializedName("name") val name: String,
    @SerializedName("englishName") val englishName: String,
    @SerializedName("format") val format: String,
    @SerializedName("type") val type: String,
    @SerializedName("direction") val direction: String? = null,
)

//todo переписать на нормальный ооп маппер
fun ChapterAudioFile.toData(): ChapterAudioEntity {
    return ChapterAudioEntity(
        number,
        name,
        englishName,
        englishNameTranslation,
        revelationType = RevelationType.fromValue(revelationType).value,
        numberOfAyahs,
        ayahs = ayahs.map { it.toData() },
        edition.toData()
    )
}

fun EditionAudio.toData(): EditionAudioEntity {
    return EditionAudioEntity(
        identifier,
        language,
        name,
        englishName,
        format,
        type,
        direction
    )
}

fun Ayah.toData(): AyahAudioEntity {
    return AyahAudioEntity(
        number,
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
