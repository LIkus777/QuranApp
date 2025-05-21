package com.zaur.data.al_quran_aqc.repository_impl.local

import com.zaur.domain.al_quran_cloud.models.edition.Editions
import com.zaur.domain.al_quran_cloud.models.edition.Languages
import com.zaur.domain.al_quran_cloud.models.edition.Types
import com.zaur.domain.al_quran_cloud.repository.EditionRepository

/**
* @author Zaur
* @since 2025-05-12
*/

class EditionLocalRepositoryImpl(

) : EditionRepository.Local {
    override suspend fun getAllTypes(): Types.Base {
        TODO("Not yet implemented")
    }

    override suspend fun getAllEditions(): Editions.Base {
        TODO("Not yet implemented")
    }

    override suspend fun getAllLanguages(): Languages.Base {
        TODO("Not yet implemented")
    }

    override suspend fun getEditionByType(type: String): Editions.Base {
        TODO("Not yet implemented")
    }

    override suspend fun getEditionByLanguage(language: String): Editions.Base {
        TODO("Not yet implemented")
    }

    override suspend fun getEditionByParam(
        format: String,
        language: String,
        type: String,
    ): Editions.Base {
        TODO("Not yet implemented")
    }
}