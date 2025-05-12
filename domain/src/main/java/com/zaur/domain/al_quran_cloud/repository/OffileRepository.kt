package com.zaur.domain.al_quran_cloud.repository

/**
* @author Zaur
* @since 2025-05-12
*/

interface OfflineRepository {
    fun isOffline(): Boolean
    fun setIsOffline(isOffline: Boolean)
}