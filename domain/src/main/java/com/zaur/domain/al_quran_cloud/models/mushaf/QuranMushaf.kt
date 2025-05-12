package com.zaur.domain.al_quran_cloud.models.mushaf


/**
 * @author Zaur
 * @since 12.05.2025
 */

interface QuranMushaf {
    fun imageUrl(): String

    data class Base(
        private val imageUrl: String
    ) : QuranMushaf {
        override fun imageUrl(): String = imageUrl
    }
}
