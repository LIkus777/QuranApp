package com.zaur.features.surah.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.zaur.core.BaseViewModel
import com.zaur.features.surah.ui_state.QuranTextUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class QuranTextViewModel(
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel(savedStateHandle) {

    private val _uiState = MutableStateFlow(QuranTextUIState())
    val textUiState: StateFlow<QuranTextUIState> = _uiState



}