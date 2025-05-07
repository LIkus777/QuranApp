package com.zaur.data.room.models.mappers

import com.zaur.data.room.models.SurahEntity
import com.zaur.domain.al_quran_cloud.models.audiofile.Surah

interface SurahMapper {

    fun toData(surah: Surah): SurahEntity
    fun fromData(entity: SurahEntity): Surah

    class Base : SurahMapper {
        override fun toData(surah: Surah): SurahEntity = SurahEntity.Base(
            number = surah.number(),
            name = surah.name(),
            englishName = surah.englishName(),
            englishNameTranslation = surah.englishNameTranslation(),
            numberOfAyahs = surah.numberOfAyahs(),
            revelationType = surah.revelationType()
        )

        override fun fromData(entity: SurahEntity): Surah = Surah.Base(
            number = entity.number(),
            name = entity.name(),
            englishName = entity.englishName(),
            englishNameTranslation = entity.englishNameTranslation(),
            numberOfAyahs = entity.numberOfAyahs(),
            revelationType = entity.revelationType()
        )
    }

}