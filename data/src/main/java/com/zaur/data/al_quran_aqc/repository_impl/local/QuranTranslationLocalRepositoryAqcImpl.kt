package com.zaur.data.al_quran_aqc.repository_impl.local

import com.zaur.data.room.dao.TranslationChapterDao
import com.zaur.data.room.models.mappers.toDomain
import com.zaur.domain.al_quran_cloud.models.translate.TranslationAqc
import com.zaur.domain.al_quran_cloud.repository.QuranTranslationRepositoryAqc

class QuranTranslationLocalRepositoryAqcImpl(
    private val translationChapterDao: TranslationChapterDao,
) : QuranTranslationRepositoryAqc.Local {
    override suspend fun getTranslationForChapterLocal(
        chapterNumber: Int, translator: String,
    ): TranslationAqc =
        translationChapterDao.getTranslationForChapter(chapterNumber, translator).toDomain()
}