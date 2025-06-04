package com.zaur.features.surah.manager

import com.zaur.data.al_quran_aqc.constans.TranslatorList
import com.zaur.domain.al_quran_cloud.use_case.TranslatorUseCase


/**
 * @author Zaur
 * @since 04.06.2025
 */

interface TranslatorManager {

    fun getTranslator(): String?
    fun saveTranslator(identifier: String)
    fun getTranslatorName(): String?

    class Base(
        private val translatorUseCase: TranslatorUseCase
    ) : TranslatorManager {
        override fun getTranslator(): String? = translatorUseCase.getTranslator()

        override fun saveTranslator(identifier: String) = translatorUseCase.saveTranslator(identifier)
        override fun getTranslatorName(): String? {
            val identifier = getTranslator()
            return TranslatorList.translators[identifier]
        }

    }

}