package com.zaur.features.surah.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.zaur.core.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
* @author Zaur
* @since 2025-05-12
*/

interface QuranTajweedViewModel {
/*
    class Base(
        private val savedStateHandle: SavedStateHandle,
        //private val quranTajweedUseCaseAqc: QuranTajweedUseCaseAqc todo
    ) : BaseViewModel(savedStateHandle), QuranTajweedViewModel {

        private val _uiState = MutableStateFlow(QuranUthmanTajweedUIState())
        val tajweedUiState: StateFlow<QuranUthmanTajweedUIState> = _uiState

        suspend fun getUthmanTajweedsForChapter(chapterNumber: Int) {*/
/*val result = launchSafely { quranTextUseCaseAqc.getUthmanTajweedsForChapter(chapterNumber) }
        result.handle(object : HandleResult<List<VerseUthmanTajweed>> {
            override fun handleSuccess(data: List<VerseUthmanTajweed>) {
                viewModelScope.launch {
                    _uiState.emit(_uiState.value.copy(tajweeds = data))
                }
            }

            override fun handleError(e: Exception) {
                super.handleError(e)
            }
        })*//*

        }

    }
*/
}