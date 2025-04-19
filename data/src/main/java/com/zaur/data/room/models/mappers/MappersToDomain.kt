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
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudioFile
import com.zaur.domain.al_quran_cloud.models.audiofile.EditionAudio
import com.zaur.domain.al_quran_cloud.models.chapter.ChapterAqc
import com.zaur.domain.al_quran_cloud.models.translate.EditionTranslation
import com.zaur.domain.al_quran_cloud.models.translate.TranslationAqc

fun TranslationEntity.toDomain(): TranslationAqc {
    return TranslationAqc(
        number = number,
        name = name,
        englishName = englishName,
        englishNameTranslation = englishNameTranslation,
        revelationType = com.zaur.domain.al_quran_cloud.models.chapter.RevelationType.fromValue(
            revelationType
        ).value,
        numberOfAyahs = numberOfAyahs,
        ayahs = translationAyahEntity.map { it.toDomain() },
        edition = edition.toDomain(),
        translator = translator
    )
}

fun TranslationAyahEntity.toDomain(): com.zaur.domain.al_quran_cloud.models.translate.Ayah {
    return com.zaur.domain.al_quran_cloud.models.translate.Ayah(
        number, text, numberInSurah, juz, manzil, page, ruku, hizbQuarter, sajda
    )
}

fun EditionTranslationEntity.toDomain(): EditionTranslation {
    return EditionTranslation(
        identifier, language, name, englishName, format, type, direction
    )
}

fun ChapterEntity.toDomain(): ChapterAqc {
    return ChapterAqc(
        number,
        name,
        englishName,
        englishNameTranslation,
        numberOfAyahs,
        revelationType = com.zaur.domain.al_quran_cloud.models.chapter.RevelationType.fromValue(
            revelationType.value
        )
    )
}

fun ChapterAudioEntity.toDomain(): ChapterAudioFile {
    return ChapterAudioFile(
        number,
        name,
        englishName,
        englishNameTranslation,
        com.zaur.domain.al_quran_cloud.models.chapter.RevelationType.fromValue(revelationType).value,
        numberOfAyahs,
        ayahs = ayahs.map { it.toDomain() },
        edition = edition.toDomain(),
        reciter
    )
}

fun AyahAudioEntity.toDomain(): com.zaur.domain.al_quran_cloud.models.audiofile.Ayah {
    return com.zaur.domain.al_quran_cloud.models.audiofile.Ayah(
        chapterNumber,
        verseNumber,
        reciter,
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

fun EditionAudioEntity.toDomain(): EditionAudio {
    return EditionAudio(
        identifier, language, name, englishName, format, type, direction
    )
}

fun ArabicChapterEntity.toDomain(): ArabicChapter {
    return ArabicChapter(
        number,
        name,
        englishName,
        englishNameTranslation,
        revelationType,
        numberOfAyahs,
        ayahs = ayahs.map { it.toDomain() },
        edition = edition.toDomain()
    )
}

fun ArabicAyahEntity.toDomain(): com.zaur.domain.al_quran_cloud.models.arabic.Ayah {
    return com.zaur.domain.al_quran_cloud.models.arabic.Ayah(
        number, text, numberInSurah, juz, manzil, page, ruku, hizbQuarter, sajda
    )
}

fun EditionArabicEntity.toDomain(): EditionArabic {
    return EditionArabic(
        identifier, language, name, englishName, format, type, direction
    )
}


