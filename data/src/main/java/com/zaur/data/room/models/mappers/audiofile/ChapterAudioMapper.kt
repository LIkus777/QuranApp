package com.zaur.data.room.models.mappers.audiofile

import com.zaur.data.room.models.ChapterAudioEntity
import com.zaur.data.room.models.mappers.audiofile.EditionAudioMapper
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudioFile

interface ChapterAudioMapper {

    fun toData(chapterAudioFile: ChapterAudioFile.Base): ChapterAudioEntity.Base
    fun fromData(entity: ChapterAudioEntity.Base): ChapterAudioFile.Base

    class Base(
        private val ayahAudioMapper: AyahAudioMapper,
        private val editionAudioMapper: EditionAudioMapper,
    ) : ChapterAudioMapper {
        override fun toData(chapterAudioFile: ChapterAudioFile.Base): ChapterAudioEntity.Base =
            ChapterAudioEntity.Base(
                chapterAudioFile.number(),
                chapterAudioFile.name(),
                chapterAudioFile.englishName(),
                chapterAudioFile.englishNameTranslation(),
                chapterAudioFile.revelationType(),
                chapterAudioFile.numberOfAyahs(),
                ayahs = chapterAudioFile.ayahs().map { ayahAudioMapper.toData(it) },
                edition = editionAudioMapper.toData(chapterAudioFile.edition()),
                chapterAudioFile.reciter(),
            )

        override fun fromData(entity: ChapterAudioEntity.Base): ChapterAudioFile.Base =
            ChapterAudioFile.Base(
                entity.number,
                entity.name,
                entity.englishName,
                entity.englishNameTranslation,
                entity.revelationType,
                entity.numberOfAyahs,
                entity.ayahs.map { ayahAudioMapper.fromData(it) },
                editionAudioMapper.fromData(entity.edition),
                entity.reciter,
            )
    }

}