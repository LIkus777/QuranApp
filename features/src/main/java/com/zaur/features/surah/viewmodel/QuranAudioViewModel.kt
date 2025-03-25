package com.zaur.features.surah.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.zaur.core.BaseViewModel
import com.zaur.features.surah.ui_state.QuranAudioUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class QuranAudioViewModel(
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel(savedStateHandle) {

    private val _uiState = MutableStateFlow(QuranAudioUIState())
    val audioUiState: StateFlow<QuranAudioUIState> = _uiState

}