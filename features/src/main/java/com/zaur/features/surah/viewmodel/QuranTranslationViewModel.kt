package com.zaur.features.surah.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.zaur.core.BaseViewModel
import com.zaur.core.HandleResult
import com.zaur.domain.al_quran_cloud.models.translate.TranslationsChapterAqc
import com.zaur.domain.al_quran_cloud.use_case.QuranTranslationUseCaseAqc
import com.zaur.features.surah.ui_state.aqc.QuranTranslationAqcUIState
import com.zaur.features.surah.ui_state.v4.QuranTranslationV4UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuranTranslationViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val quranTranslationUseCaseAqc: QuranTranslationUseCaseAqc
) : BaseViewModel(savedStateHandle) {

    private val _uiState = MutableStateFlow(QuranTranslationAqcUIState())
    val translationUiState: StateFlow<QuranTranslationAqcUIState> = _uiState

    fun getTranslationForChapter(chapterNumber: Int, translator: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result =
                launchSafely {
                    quranTranslationUseCaseAqc.getTranslationForChapter(
                        chapterNumber,
                        translator
                    )
                }
            result.handle(object : HandleResult<TranslationsChapterAqc> {
                override fun handleSuccess(data: TranslationsChapterAqc) {
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

}