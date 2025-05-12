package com.zaur.data.room.models.mappers.chapter

import com.zaur.data.room.models.ChapterEntity
import com.zaur.domain.al_quran_cloud.models.chapter.ChapterAqc

/**
* @author Zaur
* @since 2025-05-12
*/

interface ChapterMapper {
    fun toData(chapterAqc: ChapterAqc.Base): ChapterEntity.Base
    fun fromData(chapterEntity: ChapterEntity.Base): ChapterAqc.Base

    class Base : ChapterMapper {
        override fun toData(chapterAqc: ChapterAqc.Base): ChapterEntity.Base {
            return ChapterEntity.Base(
                number = chapterAqc.number(),
                name = chapterAqc.name(),
                englishName = chapterAqc.englishName(),
                englishNameTranslation = chapterAqc.englishNameTranslation(),
                numberOfAyahs = chapterAqc.numberOfAyahs(),
                revelationType = chapterAqc.revelationType()
            )
        }

        override fun fromData(chapterEntity: ChapterEntity.Base): ChapterAqc.Base {
            return ChapterAqc.Base(
                number = chapterEntity.number,
                name = chapterEntity.name,
                englishName = chapterEntity.englishName,
                englishNameTranslation = chapterEntity.englishNameTranslation,
                numberOfAyahs = chapterEntity.numberOfAyahs,
                revelationType = chapterEntity.revelationType
            )
        }
    }

}