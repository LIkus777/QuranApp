package com.zaur.domain.al_quran_cloud.repository

import com.zaur.domain.al_quran_cloud.models.edition.Editions
import com.zaur.domain.al_quran_cloud.models.edition.Languages
import com.zaur.domain.al_quran_cloud.models.edition.Types

/**
* @author Zaur
* @since 2025-05-12
*/

interface EditionRepository {

    suspend fun getAllTypes(): Types
    suspend fun getAllEditions(): Editions
    suspend fun getAllLanguages(): Languages
    suspend fun getEditionByType(type: String): Editions
    suspend fun getEditionByLanguage(language: String): Editions
    suspend fun getEditionByParam(format: String, language: String, type: String): Editions

}