package com.zaur.data.room.models.mappers.arabic

import com.zaur.data.room.models.EditionArabicEntity
import com.zaur.domain.al_quran_cloud.models.arabic.EditionArabic

/**
* @author Zaur
* @since 2025-05-12
*/

interface EditionArabicMapper {

    fun toData(edition: EditionArabic.Base): EditionArabicEntity.Base
    fun fromData(entity: EditionArabicEntity.Base): EditionArabic.Base

    class Base : EditionArabicMapper {
        override fun toData(edition: EditionArabic.Base): EditionArabicEntity.Base =
            EditionArabicEntity.Base(
                edition.identifier(),
                edition.language(),
                edition.name(),
                edition.englishName(),
                edition.format(),
                edition.type(),
            )

        override fun fromData(entity: EditionArabicEntity.Base): EditionArabic.Base = EditionArabic.Base(
            entity.identifier(),
            entity.language(),
            entity.name(),
            entity.englishName(),
            entity.format(),
            entity.type(),
        )
    }

}