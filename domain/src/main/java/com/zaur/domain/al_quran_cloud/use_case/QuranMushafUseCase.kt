package com.zaur.domain.al_quran_cloud.use_case

import com.zaur.domain.al_quran_cloud.models.mushaf.QuranMushaf


/**
 * @author Zaur
 * @since 12.05.2025
 */

interface QuranMushafUseCase {

    suspend fun getMushafImage(surahNumber: Int, ayahNumber: Int): QuranMushaf



}