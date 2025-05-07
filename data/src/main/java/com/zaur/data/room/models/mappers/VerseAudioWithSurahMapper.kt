package com.zaur.data.room.models.mappers

import com.zaur.data.room.models.SurahEntity
import com.zaur.data.room.models.VerseAudioEntity
import com.zaur.data.room.models.VerseAudioWithSurah
import com.zaur.domain.al_quran_cloud.models.audiofile.VerseAudioAqc

interface VerseAudioWithSurahMapper {
    fun toData(verse: VerseAudioAqc): VerseAudioWithSurah
    fun fromData(data: VerseAudioWithSurah): VerseAudioAqc

    class Base(
        private val verseMapper: VerseAudioMapper,
        private val surahMapper: SurahMapper
    ) : VerseAudioWithSurahMapper {

        override fun toData(verse: VerseAudioAqc): VerseAudioWithSurah {
            val surah = verse.surah()
            val surahEntity = surahMapper.toData(surah)
            val verseEntity = verseMapper.toData(verse)
            return VerseAudioWithSurah.Base(
                verse = verseEntity as VerseAudioEntity.Base,
                surah = surahEntity as SurahEntity.Base
            )
        }

        override fun fromData(data: VerseAudioWithSurah): VerseAudioAqc {
            return verseMapper.fromData(data.verse(), data.surah())
        }
    }
}
