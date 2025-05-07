package com.zaur.data.room.models.mappers

import com.zaur.data.room.models.SurahEntity
import com.zaur.data.room.models.VerseAudioEntity
import com.zaur.domain.al_quran_cloud.models.audiofile.VerseAudioAqc

interface VerseAudioMapper {

    fun toData(verse: VerseAudioAqc): VerseAudioEntity
    fun fromData(entity: VerseAudioEntity, surah: SurahEntity): VerseAudioAqc

    class Base(
        private val surahMapper: SurahMapper,
        private val editionVerseMapper: EditionVerseMapper,
    ) : VerseAudioMapper {
        //todo надо передать ещё другие поля такие как reciter и т.д., или нет
        override fun toData(verse: VerseAudioAqc): VerseAudioEntity {
            return VerseAudioEntity.Base(
                verseNumber = verse.verseNumber(),
                reciter = verse.reciter(),
                audio = verse.audio(),
                audioSecondary = verse.audioSecondary(),
                text = verse.text(),
                edition = editionVerseMapper.toData(verse.edition()),
                surahNumber = verse.surah().number(),
                numberInSurah = verse.numberInSurah(),
                juz = verse.juz(),
                manzil = verse.manzil(),
                page = verse.page(),
                ruku = verse.ruku(),
                hizbQuarter = verse.hizbQuarter(),
                sajda = verse.sajda()
            )
        }

        override fun fromData(entity: VerseAudioEntity, surah: SurahEntity): VerseAudioAqc {
            return VerseAudioAqc.Base(
                verseNumber = entity.verseNumber(),
                reciter = entity.reciter(),
                audio = entity.audio(),
                audioSecondary = entity.audioSecondary(),
                text = entity.text(),
                edition = editionVerseMapper.fromData(entity.edition()),
                surah = surahMapper.fromData(surah),
                numberInSurah = entity.numberInSurah(),
                juz = entity.juz(),
                manzil = entity.manzil(),
                page = entity.page(),
                ruku = entity.ruku(),
                hizbQuarter = entity.hizbQuarter(),
                sajda = entity.sajda()
            )
        }
    }
}