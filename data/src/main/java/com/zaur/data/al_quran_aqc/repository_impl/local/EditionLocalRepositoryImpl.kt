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

) : EditionRepository {
    override suspend fun getAllTypes(): Types {
        TODO("Not yet implemented")
    }

    override suspend fun getAllEditions(): Editions {
        TODO("Not yet implemented")
    }

    override suspend fun getAllLanguages(): Languages {
        TODO("Not yet implemented")
    }

    override suspend fun getEditionByType(type: String): Editions {
        TODO("Not yet implemented")
    }

    override suspend fun getEditionByLanguage(language: String): Editions {
        TODO("Not yet implemented")
    }

    override suspend fun getEditionByParam(
        format: String,
        language: String,
        type: String,
    ): Editions {
        TODO("Not yet implemented")
    }
}