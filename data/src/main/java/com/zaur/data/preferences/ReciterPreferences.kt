package com.zaur.data.preferences

import android.content.Context
import com.zaur.core.ReciterSharedPrefKeys
import com.zaur.domain.storage.ReciterStorage

/**
* @author Zaur
* @since 2025-05-12
*/

class ReciterPreferences(context: Context) : ReciterStorage {
    private val sharedPreferences =
        context.getSharedPreferences(ReciterSharedPrefKeys.RECITER_PREFS, Context.MODE_PRIVATE)

    override fun saveSelectedReciter(identifier: String) {
        sharedPreferences.edit().putString(ReciterSharedPrefKeys.SELECTED_RECITER, identifier)
            .apply()
    }

    override fun getSelectedReciter(): String? {
        return sharedPreferences.getString(ReciterSharedPrefKeys.SELECTED_RECITER, null)
    }
}