package com.zaur.data.room.models.mappers

import com.zaur.data.room.models.EditionVerseEntity
import com.zaur.domain.al_quran_cloud.models.audiofile.EditionVerse

interface EditionVerseMapper {

    fun toData(edition: EditionVerse): EditionVerseEntity
    fun fromData(entity: EditionVerseEntity): EditionVerse

    class Base : EditionVerseMapper {
        override fun toData(edition: EditionVerse): EditionVerseEntity = EditionVerseEntity.Base(
            edition.identifier(),
            edition.language(),
            edition.name(),
            edition.englishName(),
            edition.format(),
            edition.type(),
            edition.direction()
        )

        override fun fromData(entity: EditionVerseEntity): EditionVerse = EditionVerse.Base(
            entity.identifier(),
            entity.language(),
            entity.name(),
            entity.englishName(),
            entity.format(),
            entity.type(),
            entity.direction()
        )

    }

}