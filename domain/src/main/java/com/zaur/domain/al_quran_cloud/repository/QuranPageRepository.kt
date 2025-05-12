package com.zaur.domain.al_quran_cloud.repository

import com.zaur.domain.al_quran_cloud.models.page.QuranPageAqc


/**
 * @author Zaur
 * @since 12.05.2025
 */

interface QuranPageRepository {
    suspend fun getUthmaniPage(page: Int): QuranPageAqc
    suspend fun getTranslatedPage(page: Int): QuranPageAqc
}