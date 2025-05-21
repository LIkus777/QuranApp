package com.zaur.domain.al_quran_cloud.use_case

import com.zaur.domain.al_quran_cloud.models.edition.Editions
import com.zaur.domain.al_quran_cloud.models.edition.Languages
import com.zaur.domain.al_quran_cloud.models.edition.Types
import com.zaur.domain.al_quran_cloud.repository.EditionRepository
import com.zaur.domain.al_quran_cloud.repository.OfflineRepository


/**
 * @author Zaur
 * @since 21.05.2025
 */

interface EditionUseCase {

    suspend fun getAllTypes(): Types.Base
    suspend fun getAllEditions(): Editions.Base
    suspend fun getAllLanguages(): Languages.Base
    suspend fun getEditionByType(type: String): Editions.Base
    suspend fun getEditionByLanguage(language: String): Editions.Base
    suspend fun getEditionByParam(format: String, language: String, type: String): Editions.Base

    class Base(
        private val offlineRepository: OfflineRepository,
        private val editionRepositoryLocal: EditionRepository.Local,
        private val editionRepositoryCloud: EditionRepository.Cloud,
    ) : EditionUseCase {

        override suspend fun getAllTypes(): Types.Base =
            if (offlineRepository.isOffline()) editionRepositoryLocal.getAllTypes()
            else editionRepositoryCloud.getAllTypes()

        override suspend fun getAllEditions(): Editions.Base =
            if (offlineRepository.isOffline()) editionRepositoryLocal.getAllEditions()
            else editionRepositoryCloud.getAllEditions()

        override suspend fun getAllLanguages(): Languages.Base =
            if (offlineRepository.isOffline()) editionRepositoryLocal.getAllLanguages()
            else editionRepositoryCloud.getAllLanguages()

        override suspend fun getEditionByType(type: String): Editions.Base =
            if (offlineRepository.isOffline()) editionRepositoryLocal.getEditionByType(type)
            else editionRepositoryCloud.getEditionByType(type)

        override suspend fun getEditionByLanguage(language: String): Editions.Base =
            if (offlineRepository.isOffline()) editionRepositoryLocal.getEditionByLanguage(language)
            else editionRepositoryCloud.getEditionByLanguage(language)

        override suspend fun getEditionByParam(
            format: String,
            language: String,
            type: String,
        ): Editions.Base =
            if (offlineRepository.isOffline()) editionRepositoryLocal.getEditionByParam(
                format,
                language,
                type
            )
            else editionRepositoryCloud.getEditionByParam(format, language, type)
    }
}