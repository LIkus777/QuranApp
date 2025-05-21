package com.zaur.domain.al_quran_cloud.repository

import com.zaur.domain.al_quran_cloud.models.edition.Editions
import com.zaur.domain.al_quran_cloud.models.edition.Languages
import com.zaur.domain.al_quran_cloud.models.edition.Types

/**
* @author Zaur
* @since 2025-05-12
*/

interface EditionRepository {

    interface Local {
        suspend fun getAllTypes(): Types.Base
        suspend fun getAllEditions(): Editions.Base
        suspend fun getAllLanguages(): Languages.Base
        suspend fun getEditionByType(type: String): Editions.Base
        suspend fun getEditionByLanguage(language: String): Editions.Base
        suspend fun getEditionByParam(format: String, language: String, type: String): Editions.Base

    }

    interface Cloud {
        suspend fun getAllTypes(): Types.Base
        suspend fun getAllEditions(): Editions.Base
        suspend fun getAllLanguages(): Languages.Base
        suspend fun getEditionByType(type: String): Editions.Base
        suspend fun getEditionByLanguage(language: String): Editions.Base
        suspend fun getEditionByParam(format: String, language: String, type: String): Editions.Base
    }
}