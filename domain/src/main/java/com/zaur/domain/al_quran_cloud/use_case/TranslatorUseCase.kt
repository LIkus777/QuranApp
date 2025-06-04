package com.zaur.domain.al_quran_cloud.use_case

import com.zaur.domain.storage.TranslatorStorage


/**
 * @author Zaur
 * @since 04.06.2025
 */

interface TranslatorUseCase {

    fun getTranslator(): String?
    fun saveTranslator(identifier: String)

    class Base(
        private val translatorStorage: TranslatorStorage,
    ) : TranslatorUseCase {
        override fun saveTranslator(identifier: String) =
            translatorStorage.saveSelectedTranslator(identifier)

        override fun getTranslator(): String? = translatorStorage.getSelectedTranslator()
    }

}