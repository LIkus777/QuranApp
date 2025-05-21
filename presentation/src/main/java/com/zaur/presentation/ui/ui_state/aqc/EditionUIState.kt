package com.zaur.presentation.ui.ui_state.aqc

import com.zaur.domain.al_quran_cloud.models.edition.Editions
import com.zaur.domain.al_quran_cloud.models.edition.Languages
import com.zaur.domain.al_quran_cloud.models.edition.Types


/**
 * @author Zaur
 * @since 21.05.2025
 */

interface EditionUIState {

    fun allTypes(): Types
    fun allEditions(): Editions
    fun allLanguages(): Languages
    fun editionByType(): Editions
    fun editionByLanguage(): Editions
    fun editionByParam(): Editions

    data class Base(
        private val allTypes: Types = Types.Empty,
        private val allEditions: Editions = Editions.Empty,
        private val allLanguages: Languages = Languages.Empty,
        private val editionByType: Editions = Editions.Empty,
        private val editionByLanguage: Editions = Editions.Empty,
        private val editionByParam: Editions = Editions.Empty,
    ) : EditionUIState {
        override fun allTypes(): Types = allTypes
        override fun allEditions(): Editions = allEditions
        override fun allLanguages(): Languages = allLanguages
        override fun editionByType(): Editions = editionByType
        override fun editionByLanguage(): Editions = editionByLanguage
        override fun editionByParam(): Editions = editionByParam
    }

}