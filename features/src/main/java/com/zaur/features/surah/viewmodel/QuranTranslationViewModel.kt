package com.zaur.features.surah.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.zaur.core.BaseViewModel
import com.zaur.features.surah.ui_state.QuranTranslationUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class QuranTranslationViewModel(
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel(savedStateHandle) {

    private val _uiState = MutableStateFlow(QuranTranslationUIState())
    val translationUiState: StateFlow<QuranTranslationUIState> = _uiState

}