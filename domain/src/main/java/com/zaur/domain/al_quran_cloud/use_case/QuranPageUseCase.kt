package com.zaur.domain.al_quran_cloud.use_case

import com.zaur.domain.al_quran_cloud.models.page.QuranPageAqc
import com.zaur.domain.al_quran_cloud.repository.QuranPageRepository


/**
 * @author Zaur
 * @since 12.05.2025
 */

interface QuranPageUseCase {

    /// TODO: начать применять. добавь апи 
    
    suspend fun getUthmaniPage(page: Int): QuranPageAqc
    suspend fun getTranslatedPage(page: Int): QuranPageAqc

    class Base(
        private val quranPageRepository: QuranPageRepository
    ) : QuranPageUseCase {
        override suspend fun getUthmaniPage(page: Int): QuranPageAqc =
            quranPageRepository.getUthmaniPage(page)

        override suspend fun getTranslatedPage(page: Int): QuranPageAqc =
            quranPageRepository.getTranslatedPage(page)
    }

}