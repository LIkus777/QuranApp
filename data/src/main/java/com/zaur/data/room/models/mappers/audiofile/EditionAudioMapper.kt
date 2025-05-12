package com.zaur.data.room.models.mappers.audiofile

import com.zaur.data.room.models.EditionAudioEntity
import com.zaur.domain.al_quran_cloud.models.audiofile.EditionAudio

/**
* @author Zaur
* @since 2025-05-12
*/

interface EditionAudioMapper {

    fun toData(edition: EditionAudio.Base): EditionAudioEntity.Base
    fun fromData(entity: EditionAudioEntity.Base): EditionAudio.Base

    class Base : EditionAudioMapper {
        override fun toData(edition: EditionAudio.Base): EditionAudioEntity.Base = EditionAudioEntity.Base(
            edition.identifier(),
            edition.language(),
            edition.name(),
            edition.englishName(),
            edition.format(),
            edition.direction().toString()
        )

        override fun fromData(entity: EditionAudioEntity.Base): EditionAudio.Base = EditionAudio.Base(
            entity.identifier(),
            entity.language(),
            entity.name(),
            entity.englishName(),
            entity.format(),
            entity.direction().toString()
        )
    }

}