package com.zaur.features.surah.ui_state.offline

/**
* @author Zaur
* @since 2025-05-12
*/

interface OfflineUIState {

    fun isOffline(): Boolean

    data class Base(
        private val isOffline: Boolean = false,
    ) : OfflineUIState {
        override fun isOffline(): Boolean = isOffline
    }

}