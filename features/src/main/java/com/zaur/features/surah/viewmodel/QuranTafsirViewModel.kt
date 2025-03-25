package com.zaur.features.surah.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.zaur.core.BaseViewModel
import com.zaur.features.surah.ui_state.QuranTafsirUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class QuranTafsirViewModel(
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel(savedStateHandle) {

    private val _uiState = MutableStateFlow(QuranTafsirUIState())
    val tafsirUiState: StateFlow<QuranTafsirUIState> = _uiState

}