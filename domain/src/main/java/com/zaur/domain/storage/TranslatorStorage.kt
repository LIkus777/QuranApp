package com.zaur.domain.storage


/**
 * @author Zaur
 * @since 04.06.2025
 */

interface TranslatorStorage {
    fun saveSelectedTranslator(identifier: String)
    fun getSelectedTranslator(): String?
}