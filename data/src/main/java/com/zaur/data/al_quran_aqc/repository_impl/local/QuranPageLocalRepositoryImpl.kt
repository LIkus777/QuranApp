package com.zaur.data.al_quran_aqc.repository_impl.local

import com.zaur.data.al_quran_aqc.api.QuranApiAqc
import com.zaur.domain.al_quran_cloud.models.page.QuranPageAqc
import com.zaur.domain.al_quran_cloud.repository.QuranPageRepository


/**
 * @author Zaur
 * @since 14.05.2025
 */

class QuranPageLocalRepositoryImpl(
    private val quranApi: QuranApiAqc,
) : QuranPageRepository.Local {
    override suspend fun getUthmaniPage(page: Int): QuranPageAqc = quranApi.getUthmaniPage(page)

    override suspend fun getTranslatedPage(
        page: Int,
        translator: String,
    ): QuranPageAqc = quranApi.getTranslatedPage(page, translator)
}