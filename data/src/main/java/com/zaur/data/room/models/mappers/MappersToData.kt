package com.zaur.data.room.models.mappers

import com.zaur.data.room.models.ArabicAyahEntity
import com.zaur.data.room.models.ArabicChapterEntity
import com.zaur.data.room.models.AyahAudioEntity
import com.zaur.data.room.models.ChapterAudioEntity
import com.zaur.data.room.models.ChapterEntity
import com.zaur.data.room.models.EditionArabicEntity
import com.zaur.data.room.models.EditionAudioEntity
import com.zaur.data.room.models.EditionTranslationEntity
import com.zaur.data.room.models.TranslationAyahEntity
import com.zaur.data.room.models.TranslationEntity
import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChapter
import com.zaur.domain.al_quran_cloud.models.arabic.EditionArabic
import com.zaur.domain.al_quran_cloud.models.audiofile.Ayah
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudioFile
import com.zaur.domain.al_quran_cloud.models.audiofile.EditionAudio
import com.zaur.domain.al_quran_cloud.models.chapter.ChapterAqc
import com.zaur.domain.al_quran_cloud.models.chapter.RevelationType
import com.zaur.domain.al_quran_cloud.models.translate.EditionTranslation
import com.zaur.domain.al_quran_cloud.models.translate.TranslationAqc

fun ArabicChapter.toData(): ArabicChapterEntity {
    return ArabicChapterEntity(
        number,
        name,
        englishName,
        englishNameTranslation,
        revelationType,
        numberOfAyahs,
        ayahs = ayahs.map { it.toData() },
        edition = edition.toData()
    )
}

fun com.zaur.domain.al_quran_cloud.models.arabic.Ayah.toData(): ArabicAyahEntity {
    return ArabicAyahEntity(
        number, text, numberInSurah, juz, manzil, page, ruku, hizbQuarter, sajda
    )
}

fun EditionArabic.toData(): EditionArabicEntity {
    return EditionArabicEntity(
        identifier, language, name, englishName, format, type, direction
    )
}

//todo переписать на нормальный ооп маппер
fun ChapterAqc.toData(): ChapterEntity {
    return ChapterEntity(
        number,
        name,
        englishName,
        englishNameTranslation,
        numberOfAyahs,
        revelationType = com.zaur.data.room.models.RevelationType.fromValue(revelationType.value)
    )
}


fun TranslationAqc.toData(): TranslationEntity {
    return TranslationEntity(
        number,
        name,
        englishName,
        englishNameTranslation,
        revelationType,
        numberOfAyahs,
        ayahs.map { it.toData() },
        edition.toData(),
        translator
    )
}

fun com.zaur.domain.al_quran_cloud.models.translate.Ayah.toData(): TranslationAyahEntity {
    return TranslationAyahEntity(
        number, text, numberInSurah, juz, manzil, page, ruku, hizbQuarter, sajda
    )
}

fun EditionTranslation.toData(): EditionTranslationEntity {
    return EditionTranslationEntity(
        identifier, language, name, englishName, format, type, direction
    )
}

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
        edition.toData(),
        reciter
    )
}

fun EditionAudio.toData(): EditionAudioEntity {
    return EditionAudioEntity(
        identifier, language, name, englishName, format, type, direction
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