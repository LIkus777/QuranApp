package com.zaur.data.room.models.mappers.audiofile

import com.zaur.data.room.models.EditionVerseEntity
import com.zaur.domain.al_quran_cloud.models.audiofile.EditionVerse

/**
* @author Zaur
* @since 2025-05-12
*/

interface EditionVerseMapper {

    fun toData(edition: EditionVerse.Base): EditionVerseEntity.Base
    fun fromData(entity: EditionVerseEntity.Base): EditionVerse.Base

    class Base : EditionVerseMapper {
        override fun toData(edition: EditionVerse.Base): EditionVerseEntity.Base = EditionVerseEntity.Base(
            edition.identifier(),
            edition.language(),
            edition.name(),
            edition.englishName(),
            edition.format(),
            edition.type(),
            edition.direction()
        )

        override fun fromData(entity: EditionVerseEntity.Base): EditionVerse.Base = EditionVerse.Base(
            entity.identifier,
            entity.language,
            entity.name,
            entity.englishName,
            entity.format,
            entity.type,
            entity.direction
        )
    }

}