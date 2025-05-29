package com.zaur.data.al_quran_aqc.repository_impl.local

import com.zaur.data.room.dao.TranslationChapterDao
import com.zaur.data.room.models.mappers.translate.TranslationMapper
import com.zaur.domain.al_quran_cloud.models.translate.Translation
import com.zaur.domain.al_quran_cloud.repository.QuranTranslationRepository

/**
 * @author Zaur
 * @since 2025-05-12
 */

class QuranTranslationLocalRepositoryImpl(
    private val translationChapterDao: TranslationChapterDao,
    private val translationMapper: TranslationMapper,
) : QuranTranslationRepository.Local {
    override suspend fun getTranslationForChapterLocal(
        chapterNumber: Int, translator: String,
    ): Translation.Base = translationMapper.fromData(
        translationChapterDao.getTranslationForChapter(
            chapterNumber, translator
        )
    )
}