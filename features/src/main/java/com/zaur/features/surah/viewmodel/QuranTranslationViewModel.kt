package com.zaur.features.surah.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.zaur.core.BaseViewModel
import com.zaur.core.HandleResult
import com.zaur.domain.models.translate.SingleTranslations
import com.zaur.domain.models.translate.Translation
import com.zaur.domain.apiV4.use_case.QuranTranslationUseCaseV4
import com.zaur.features.surah.ui_state.QuranTranslationUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuranTranslationViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val quranTranslationUseCaseV4: QuranTranslationUseCaseV4
) : BaseViewModel(savedStateHandle) {

    private val _uiState = MutableStateFlow(QuranTranslationUIState())
    val translationUiState: StateFlow<QuranTranslationUIState> = _uiState


    suspend fun getTranslationForChapter(translationId: Int) {
        val result =
            launchSafely { quranTranslationUseCaseV4.getTranslationForChapter(translationId) }
        result.handle(object : HandleResult<SingleTranslations> {
            override fun handleSuccess(data: SingleTranslations) {
                viewModelScope.launch {
                    _uiState.emit(_uiState.value.copy(singleTranslation = data))
                }
            }

            override fun handleError(e: Exception) {
                super.handleError(e)
            }
        })

    }

    suspend fun getAvailableTranslations(language: String) {
        val result = launchSafely { quranTranslationUseCaseV4.getAvailableTranslations(language) }
        result.handle(object : HandleResult<List<Translation>> {
            override fun handleSuccess(data: List<Translation>) {
                viewModelScope.launch {
                    _uiState.emit(_uiState.value.copy(translations = data))
                }
            }

            override fun handleError(e: Exception) {
                super.handleError(e)
            }
        })
    }

}