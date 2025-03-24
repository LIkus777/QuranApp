package com.zaur.data.repository_impl

import com.zaur.data.api.QuranApi
import com.zaur.domain.models.translate.SingleTranslation
import com.zaur.domain.repository.QuranTranslationRepository

class QuranTranslationRepositoryImpl(
    private val quranApi: QuranApi
) : QuranTranslationRepository {
    override fun getTranslationForChapter(translationId: Int): SingleTranslation {
        TODO("Not yet implemented")
    }
}