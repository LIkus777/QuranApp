package com.zaur.domain.al_quran_cloud.repository

import com.zaur.domain.al_quran_cloud.models.mushaf.QuranMushaf


/**
 * @author Zaur
 * @since 12.05.2025
 */

interface QuranMushafRepository {
    suspend fun getMushafImage(surahNumber: Int, ayahNumber: Int): QuranMushaf
}