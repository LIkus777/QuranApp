package com.zaur.data.room.models.mappers.translate

import com.zaur.data.room.models.EditionTranslationEntity
import com.zaur.domain.al_quran_cloud.models.translate.EditionTranslation

/**
* @author Zaur
* @since 2025-05-12
*/

interface EditionTranslationMapper {

    fun toData(edition: EditionTranslation.Base): EditionTranslationEntity.Base
    fun fromData(entity: EditionTranslationEntity.Base): EditionTranslation.Base

    class Base : EditionTranslationMapper {
        override fun toData(edition: EditionTranslation.Base): EditionTranslationEntity.Base =
            EditionTranslationEntity.Base(
                edition.identifier(),
                edition.language(),
                edition.name(),
                edition.englishName(),
                edition.format(),
                edition.type(),
                edition.direction()
            )
        override fun fromData(entity: EditionTranslationEntity.Base): EditionTranslation.Base =
            EditionTranslation.Base(
                entity.identifier,
                entity.language,
                entity.name,
                entity.englishName,
                entity.format,
                entity.type,
                entity.direction.toString()
            )
    }

}