package com.zaur.features.surah.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.zaur.core.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface QuranTafsirViewModel {/*
    class Base(
        private val savedStateHandle: SavedStateHandle,
        private val quranTafsirUseCaseV4: QuranTafsirUseCaseV4,
    ) : BaseViewModel(savedStateHandle), QuranTafsirViewModel {

        private val _uiState = MutableStateFlow(QuranTafsirV4UIState())
        val tafsirUiState: StateFlow<QuranTafsirV4UIState> = _uiState

        *//*suspend fun getTafsirForChapter(tafsirId: Int, chapterNumber: Int) {
            val result =
                launchSafely { quranTafsirUseCaseV4.getTafsirForChapter(tafsirId, chapterNumber) }
            result.handle(object : HandleResult<SingleTafsirs> {
                override fun handleSuccess(data: SingleTafsirs) {
                    viewModelScope.launch {
                        _uiState.emit(_uiState.value.copy(singleTafsirs = data))
                    }
                }

                override fun handleError(e: Exception) {
                    super.handleError(e)
                }
            })
        }

        suspend fun getAvailableTafsirs(language: String) {
            val result = launchSafely { quranTafsirUseCaseV4.getAvailableTafsirs(language) }
            result.handle(object : HandleResult<List<Tafsir>> {
                override fun handleSuccess(data: List<Tafsir>) {
                    viewModelScope.launch {
                        _uiState.emit(_uiState.value.copy(tafsir = data))
                    }
                }

                override fun handleError(e: Exception) {
                    super.handleError(e)
                }
            })
        }*//*


    }
*/
}