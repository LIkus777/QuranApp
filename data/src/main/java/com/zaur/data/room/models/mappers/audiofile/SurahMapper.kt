package com.zaur.data.room.models.mappers.audiofile

import com.zaur.data.room.models.SurahEntity
import com.zaur.domain.al_quran_cloud.models.audiofile.Surah

interface SurahMapper {

    fun toData(surah: Surah.Base): SurahEntity.Base
    fun fromData(entity: SurahEntity.Base): Surah.Base

    class Base : SurahMapper {
        override fun toData(surah: Surah.Base): SurahEntity.Base = SurahEntity.Base(
            number = surah.number(),
            name = surah.name(),
            englishName = surah.englishName(),
            englishNameTranslation = surah.englishNameTranslation(),
            numberOfAyahs = surah.numberOfAyahs(),
            revelationType = surah.revelationType()
        )

        override fun fromData(entity: SurahEntity.Base): Surah.Base = Surah.Base(
            number = entity.number,
            name = entity.name,
            englishName = entity.englishName,
            englishNameTranslation = entity.englishNameTranslation,
            numberOfAyahs = entity.numberOfAyahs,
            revelationType = entity.revelationType
        )
    }

}