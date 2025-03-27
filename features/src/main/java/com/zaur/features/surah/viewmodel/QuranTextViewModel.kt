package com.zaur.features.surah.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.zaur.core.BaseViewModel
import com.zaur.core.HandleResult
import com.zaur.domain.models.chapter.Chapter
import com.zaur.domain.models.juz.Juz
import com.zaur.domain.use_case.QuranTextUseCase
import com.zaur.features.surah.ui_state.QuranTextUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuranTextViewModel(
    private val savedStateHandle: SavedStateHandle, private val quranTextUseCase: QuranTextUseCase
) : BaseViewModel(savedStateHandle) {

    private val _uiState = MutableStateFlow(QuranTextUIState())
    val textUiState: StateFlow<QuranTextUIState> = _uiState

    suspend fun getAllChapters(language: String) {
        val result = launchSafely<List<Chapter>> { quranTextUseCase.getAllChapters(language) }
        result.handle(object : HandleResult<List<Chapter>> {
            override fun handleSuccess(data: List<Chapter>) {
                viewModelScope.launch {
                    _uiState.emit(_uiState.value.copy(chapters = data))
                }
            }

            override fun handleError(e: Exception) {
                super.handleError(e)
            }
        })
    }

    suspend fun getChapter(chapterNumber: Int, language: String) {
        val result = launchSafely { quranTextUseCase.getChapter(chapterNumber, language) }
        result.handle(object : HandleResult<Chapter> {
            override fun handleSuccess(data: Chapter) {
                viewModelScope.launch {
                    _uiState.emit(_uiState.value.copy(currentChapter = data))
                }
            }

            override fun handleError(e: Exception) {
                Log.e("TAG", "handleError: $e")
            }
        })
    }

    suspend fun getAllJuzs() {
        val result = launchSafely { quranTextUseCase.getAllJuzs() }
        result.handle(object : HandleResult<List<Juz>> {
            override fun handleSuccess(data: List<Juz>) {
                viewModelScope.launch {
                    _uiState.emit(_uiState.value.copy(juz = data))
                }
            }

            override fun handleError(e: Exception) {
                Log.e("TAG", "handleError: $e")
            }
        })
    }

}