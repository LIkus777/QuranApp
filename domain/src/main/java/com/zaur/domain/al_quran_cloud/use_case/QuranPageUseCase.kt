package com.zaur.domain.al_quran_cloud.use_case

import com.zaur.domain.al_quran_cloud.models.page.QuranPageAqc
import com.zaur.domain.al_quran_cloud.repository.OfflineRepository
import com.zaur.domain.al_quran_cloud.repository.QuranPageRepository


/**
 * @author Zaur
 * @since 12.05.2025
 */

interface QuranPageUseCase {

    /// TODO: начать применять. добавь апи 

    suspend fun getUthmaniPage(page: Int): QuranPageAqc
    suspend fun getTranslatedPage(page: Int, translator: String): QuranPageAqc

    class Base(
        private val offlineRepository: OfflineRepository,
        private val quranPageRepositoryCloud: QuranPageRepository.Cloud,
        private val quranPageRepositoryLocal: QuranPageRepository.Local,
    ) : QuranPageUseCase {
        override suspend fun getUthmaniPage(page: Int): QuranPageAqc =
            if (offlineRepository.isOffline()) quranPageRepositoryCloud.getUthmaniPage(page)
            else quranPageRepositoryLocal.getUthmaniPage(page)


        override suspend fun getTranslatedPage(page: Int, translator: String): QuranPageAqc =
            if (offlineRepository.isOffline()) quranPageRepositoryCloud.getTranslatedPage(
                page,
                translator
            )
            else quranPageRepositoryLocal.getTranslatedPage(page, translator)
    }

}