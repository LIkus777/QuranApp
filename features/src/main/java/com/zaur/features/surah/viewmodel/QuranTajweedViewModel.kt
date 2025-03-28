package com.zaur.features.surah.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.zaur.core.BaseViewModel
import com.zaur.core.HandleResult
import com.zaur.domain.models.tajweed.VerseUthmanTajweed
import com.zaur.domain.apiV4.use_case.QuranTajweedUseCaseV4
import com.zaur.features.surah.ui_state.QuranUthmanTajweedUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuranTajweedViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val quranTajweedUseCaseV4: QuranTajweedUseCaseV4
) : BaseViewModel(savedStateHandle) {

    private val _uiState = MutableStateFlow(QuranUthmanTajweedUIState())
    val tajweedUiState: StateFlow<QuranUthmanTajweedUIState> = _uiState

    suspend fun getUthmanTajweedsForChapter(chapterNumber: Int) {
        val result = launchSafely { quranTajweedUseCaseV4.getUthmanTajweedsForChapter(chapterNumber) }
        result.handle(object : HandleResult<List<VerseUthmanTajweed>> {
            override fun handleSuccess(data: List<VerseUthmanTajweed>) {
                viewModelScope.launch {
                    _uiState.emit(_uiState.value.copy(tajweeds = data))
                }
            }

            override fun handleError(e: Exception) {
                super.handleError(e)
            }
        })
    }

}