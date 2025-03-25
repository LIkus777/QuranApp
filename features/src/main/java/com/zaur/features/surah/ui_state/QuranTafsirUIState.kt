package com.zaur.features.surah.ui_state

import com.zaur.domain.models.tafsir.SingleTafsirs
import com.zaur.domain.models.tafsir.Tafsir

data class QuranTafsirUIState(
    val isLoading: Boolean = false,
    val singleTafsirs: SingleTafsirs? = null,
    val tafsir: List<Tafsir> = emptyList(),
    val errorMessage: String? = null,
    val isRefreshing: Boolean = false
)
