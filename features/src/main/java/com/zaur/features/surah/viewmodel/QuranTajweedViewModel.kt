package com.zaur.features.surah.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.zaur.core.BaseViewModel
import com.zaur.features.surah.ui_state.QuranUthmanTajweedUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class QuranTajweedViewModel(
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel(savedStateHandle) {

    private val _uiState = MutableStateFlow(QuranUthmanTajweedUIState())
    val tajweedUiState: StateFlow<QuranUthmanTajweedUIState> = _uiState

}