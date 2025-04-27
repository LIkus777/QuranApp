package com.zaur.domain.al_quran_cloud.use_case

import com.zaur.domain.al_quran_cloud.repository.OfflineRepository

interface OfflineUseCase {

    fun isOffline(): Boolean
    fun setIsOffline(isOffline: Boolean)

    class Base(private val offlineRepository: OfflineRepository) : OfflineUseCase {
        override fun isOffline(): Boolean = offlineRepository.isOffline()

        override fun setIsOffline(isOffline: Boolean) = offlineRepository.setIsOffline(isOffline)
    }

}