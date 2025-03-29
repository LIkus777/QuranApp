package com.zaur.features.surah.ui_state.v4

import com.zaur.domain.apiV4.models.tafsir.SingleTafsirsV4
import com.zaur.domain.apiV4.models.tafsir.TafsirV4

data class QuranTafsirV4UIState(
    val isLoading: Boolean = false,
    val singleTafsirs: SingleTafsirsV4? = null,
    val tafsir: List<TafsirV4> = emptyList(),
    val errorMessage: String? = null,
    val isRefreshing: Boolean = false
)
