package com.zaur.data.room.models.mappers.translate

import com.zaur.data.room.models.TranslationEntity
import com.zaur.domain.al_quran_cloud.models.translate.EditionTranslation
import com.zaur.domain.al_quran_cloud.models.translate.Translation

/**
* @author Zaur
* @since 2025-05-12
*/

interface TranslationMapper {

    fun toData(translation: Translation.Base): TranslationEntity.Base
    fun fromData(entity: TranslationEntity.Base): Translation.Base

    class Base(
        private val translationAyahMapper: TranslationAyahMapper,
        private val editionTranslationMapper: EditionTranslationMapper,
    ) : TranslationMapper {
        override fun toData(translation: Translation.Base): TranslationEntity.Base =
            TranslationEntity.Base(
                translation.number(),
                translation.name(),
                translation.englishName(),
                translation.englishNameTranslation(),
                translation.revelationType(),
                translation.numberOfAyahs(),
                translation.translationAyahs().map { translationAyahMapper.toData(it) },
                editionTranslationMapper.toData(translation.edition() as EditionTranslation.Base),
                translation.translator()
            )

        override fun fromData(entity: TranslationEntity.Base): Translation.Base = Translation.Base(
            entity.number,
            entity.name,
            entity.englishName,
            entity.englishNameTranslation,
            entity.revelationType,
            entity.numberOfAyahs,
            entity.translationAyahEntity.map { translationAyahMapper.fromData(it) },
            editionTranslationMapper.fromData(entity.edition),
            entity.translator
        )
    }

}