package com.zaur.data.room.models.mappers.audiofile

import com.zaur.data.room.models.SurahEntity
import com.zaur.data.room.models.VerseAudioEntity
import com.zaur.data.room.models.VerseAudioWithSurah
import com.zaur.domain.al_quran_cloud.models.audiofile.VerseAudio

/**
* @author Zaur
* @since 2025-05-12
*/

interface VerseAudioWithSurahMapper {
    fun toData(verse: VerseAudio.Base): VerseAudioWithSurah.Base
    fun fromData(data: VerseAudioWithSurah.Base): VerseAudio.Base

    class Base(
        private val verseMapper: VerseAudioMapper,
        private val surahMapper: SurahMapper
    ) : VerseAudioWithSurahMapper {

        override fun toData(verse: VerseAudio.Base): VerseAudioWithSurah.Base {
            val surah = verse.surah()
            val surahEntity = surahMapper.toData(surah)
            val verseEntity = verseMapper.toData(verse)
            return VerseAudioWithSurah.Base(
                verse = verseEntity as VerseAudioEntity.Base,
                surah = surahEntity as SurahEntity.Base
            )
        }

        override fun fromData(data: VerseAudioWithSurah.Base): VerseAudio.Base {
            return verseMapper.fromData(data.verse, data.surah)
        }
    }
}
