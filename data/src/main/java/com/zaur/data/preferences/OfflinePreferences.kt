package com.zaur.data.preferences

import android.content.Context
import com.zaur.core.OfflineSharedPrefKeys
import com.zaur.domain.al_quran_cloud.repository.OfflineRepository

class OfflinePreferences(
    context: Context,
) : OfflineRepository {
    private val sharedPreferences =
        context.getSharedPreferences(OfflineSharedPrefKeys.OFFLINE_PREFS, Context.MODE_PRIVATE)

    override fun isOffline(): Boolean =
        sharedPreferences.getBoolean(OfflineSharedPrefKeys.IS_OFFLINE, false)

    override fun setIsOffline(isOffline: Boolean) {
        sharedPreferences.edit().putBoolean(OfflineSharedPrefKeys.IS_OFFLINE, isOffline).apply()
    }
}