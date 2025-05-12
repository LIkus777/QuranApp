package com.zaur.data.room.models.mappers.arabic

import com.zaur.data.room.models.ArabicChapterEntity
import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChapter

/**
* @author Zaur
* @since 2025-05-12
*/

interface ArabicMapper {

    fun toData(arabicChapter: ArabicChapter.Base): ArabicChapterEntity.Base
    fun fromData(entity: ArabicChapterEntity.Base): ArabicChapter.Base

    class Base(
        private val arabicAyahMapper: ArabicAyahMapper,
        private val editionArabicMapper: EditionArabicMapper,
    ) : ArabicMapper {
        override fun toData(arabicChapter: ArabicChapter.Base): ArabicChapterEntity.Base =
            ArabicChapterEntity.Base(
                arabicChapter.number(),
                arabicChapter.name(),
                arabicChapter.englishName(),
                arabicChapter.englishNameTranslation(),
                arabicChapter.revelationType(),
                arabicChapter.numberOfAyahs(),
                arabicChapter.ayahs().map { arabicAyahMapper.toData(it) },
                editionArabicMapper.toData(arabicChapter.edition()),
            )

        override fun fromData(entity: ArabicChapterEntity.Base): ArabicChapter.Base =
            ArabicChapter.Base(
                entity.number,
                entity.name,
                entity.englishName,
                entity.englishNameTranslation,
                entity.revelationType,
                entity.numberOfAyahs,
                entity.ayahs.map { arabicAyahMapper.fromData(it) },
                editionArabicMapper.fromData(entity.edition),
            )
    }

}