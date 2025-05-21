package com.zaur.data.al_quran_aqc.repository_impl.cloud

import com.zaur.data.al_quran_aqc.api.QuranApiAqc
import com.zaur.data.network.retryWithBackoff
import com.zaur.domain.al_quran_cloud.models.edition.Editions
import com.zaur.domain.al_quran_cloud.models.edition.Languages
import com.zaur.domain.al_quran_cloud.models.edition.Types
import com.zaur.domain.al_quran_cloud.repository.EditionRepository

/**
 * @author Zaur
 * @since 2025-05-12
 */

class EditionCloudRepositoryImpl(
    private val quranApiAqc: QuranApiAqc,
) : EditionRepository.Cloud {
    override suspend fun getAllTypes(): Types.Base = retryWithBackoff { quranApiAqc.getAllTypes() }

    override suspend fun getAllEditions(): Editions.Base =
        retryWithBackoff { quranApiAqc.getAllEditions() }

    override suspend fun getAllLanguages(): Languages.Base =
        retryWithBackoff { quranApiAqc.getAllLanguages() }

    override suspend fun getEditionByType(type: String): Editions.Base =
        retryWithBackoff { quranApiAqc.getEditionByType(type) }

    override suspend fun getEditionByLanguage(language: String): Editions.Base =
        retryWithBackoff { quranApiAqc.getEditionByLanguage(language) }

    override suspend fun getEditionByParam(
        format: String,
        language: String,
        type: String,
    ): Editions.Base = retryWithBackoff { quranApiAqc.getEditionByParam(format, language, type) }
}