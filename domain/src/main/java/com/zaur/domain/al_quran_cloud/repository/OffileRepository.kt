package com.zaur.domain.al_quran_cloud.repository

interface OfflineRepository {
    fun isOffline(): Boolean
    fun setIsOffline(isOffline: Boolean)
}