package com.zaur.features.surah.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.zaur.core.BaseViewModel
import com.zaur.core.HandleResult
import com.zaur.domain.models.chapter.Chapter
import com.zaur.domain.models.juz.Juz
import com.zaur.domain.apiV4.use_case.QuranTextUseCaseV4
import com.zaur.features.surah.ui_state.QuranTextUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuranTextViewModel(
    private val savedStateHandle: SavedStateHandle, private val quranTextUseCaseV4: QuranTextUseCaseV4
) : BaseViewModel(savedStateHandle) {

    private val _uiState = MutableStateFlow(QuranTextUIState())
    val textUiState: StateFlow<QuranTextUIState> = _uiState

    init {
        viewModelScope.launch {
            if (quranTextUseCaseV4.isSurahScreenOpened()) {
                val (lastChapter, lastAyah) = quranTextUseCaseV4.getLastRead()
                getChapter(lastChapter, "ru") //todo СДЕЛАТЬ ДРУГУЮ ЛОГИКУ (ДОБАВИТЬ ДРУГОЙ МЕТОД ДЛЯ ПОЛУЧЕНИЯ ПОСЛЕДНЕЙ СУРЫ И АЯТА)
            } else {
                getAllChapters("ru")
                quranTextUseCaseV4.setSurahScreenOpened()
            }
        }
    }

    suspend fun getAllChapters(language: String) {
        val result = launchSafely<List<Chapter>> { quranTextUseCaseV4.getAllChapters(language) }
        result.handle(object : HandleResult<List<Chapter>> {
            override fun handleSuccess(data: List<Chapter>) {
                viewModelScope.launch {
                    Log.i("TAG", "handleSuccess: getAllChapters data $data")
                    _uiState.emit(_uiState.value.copy(chapters = data))
                }
            }

            override fun handleError(e: Exception) {
                super.handleError(e)
            }
        })
    }

    suspend fun getChapter(chapterNumber: Int, language: String) {
        val result = launchSafely { quranTextUseCaseV4.getChapter(chapterNumber, language) }
        result.handle(object : HandleResult<Chapter> {
            override fun handleSuccess(data: Chapter) {
                viewModelScope.launch {
                    Log.i("TAG", "handleSuccess: getChapter data $data")
                    _uiState.emit(_uiState.value.copy(currentChapter = data))
                }
            }

            override fun handleError(e: Exception) {
                Log.e("TAG", "handleError: $e")
            }
        })
    }

    suspend fun getAllJuzs() {
        val result = launchSafely { quranTextUseCaseV4.getAllJuzs() }
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