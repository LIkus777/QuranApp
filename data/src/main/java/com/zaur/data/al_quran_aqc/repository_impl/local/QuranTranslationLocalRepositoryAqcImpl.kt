package com.zaur.data.al_quran_aqc.repository_impl.local

import com.zaur.data.room.dao.TranslationChapterDao
import com.zaur.data.room.models.mappers.translate.TranslationMapper
import com.zaur.domain.al_quran_cloud.models.translate.TranslationAqc
import com.zaur.domain.al_quran_cloud.repository.QuranTranslationRepositoryAqc

class QuranTranslationLocalRepositoryAqcImpl(
    private val translationChapterDao: TranslationChapterDao,
    private val translationMapper: TranslationMapper,
) : QuranTranslationRepositoryAqc.Local {
    override suspend fun getTranslationForChapterLocal(
        chapterNumber: Int, translator: String,
    ): TranslationAqc.Base = translationMapper.fromData(
        translationChapterDao.getTranslationForChapter(
            chapterNumber,
            translator
        )
    )
}