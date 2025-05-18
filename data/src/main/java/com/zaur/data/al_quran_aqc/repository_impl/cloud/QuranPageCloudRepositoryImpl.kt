package com.zaur.data.al_quran_aqc.repository_impl.cloud

import com.zaur.data.al_quran_aqc.api.QuranApiAqc
import com.zaur.data.network.retryWithBackoff
import com.zaur.domain.al_quran_cloud.models.page.QuranPageAqc
import com.zaur.domain.al_quran_cloud.repository.QuranPageRepository


/**
 * @author Zaur
 * @since 14.05.2025
 */

class QuranPageCloudRepositoryImpl(
    private val quranApiAqc: QuranApiAqc,
) : QuranPageRepository.Cloud {
    override suspend fun getUthmaniPage(page: Int): QuranPageAqc =
        retryWithBackoff { quranApiAqc.getUthmaniPage(page) }

    override suspend fun getTranslatedPage(
        page: Int,
        translator: String,
    ): QuranPageAqc = retryWithBackoff { quranApiAqc.getTranslatedPage(page, translator) }
}