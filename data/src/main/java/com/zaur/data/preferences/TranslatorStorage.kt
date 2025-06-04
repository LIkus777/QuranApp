package com.zaur.data.preferences

import android.content.Context
import com.zaur.core.TranslatorSharedPrefKeys
import com.zaur.domain.storage.TranslatorStorage


/**
 * @author Zaur
 * @since 04.06.2025
 */

class TranslatorPreferences(context: Context) : TranslatorStorage {
    private val sharedPreferences =
        context.getSharedPreferences(TranslatorSharedPrefKeys.TRANSLATOR_PREFS, Context.MODE_PRIVATE)

    override fun saveSelectedTranslator(identifier: String) = sharedPreferences.edit().putString(TranslatorSharedPrefKeys.SELECTED_TRANSLATOR, identifier).apply()

    override fun getSelectedTranslator(): String? = sharedPreferences.getString(TranslatorSharedPrefKeys.SELECTED_TRANSLATOR, "")
}