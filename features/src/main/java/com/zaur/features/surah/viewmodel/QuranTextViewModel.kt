package com.zaur.features.surah.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.zaur.core.BaseViewModel
import com.zaur.core.HandleResult
import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChaptersAqc
import com.zaur.domain.al_quran_cloud.models.chapter.ChaptersAqc
import com.zaur.domain.al_quran_cloud.use_case.QuranTextUseCaseAqc
import com.zaur.features.surah.ui_state.aqc.QuranTextAqcUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuranTextViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val quranTextUseCaseAqc: QuranTextUseCaseAqc
) : BaseViewModel(savedStateHandle) {

    private val _uiState = MutableStateFlow(QuranTextAqcUIState())
    val textUiState: StateFlow<QuranTextAqcUIState> = _uiState

    init {
        /*viewModelScope.launch {
            if (quranTextUseCaseAqc.isSurahScreenOpened()) {
                val (lastChapter, lastAyah) = quranTextUseCaseAqc.getLastRead()
                getArabicChapter(lastChapter) //todo СДЕЛАТЬ ДРУГУЮ ЛОГИКУ (ДОБАВИТЬ ДРУГОЙ МЕТОД ДЛЯ ПОЛУЧЕНИЯ ПОСЛЕДНЕЙ СУРЫ И АЯТА)
            } else {
                getAllChapters("ru")
                quranTextUseCaseAqc.setSurahScreenOpened()
            }
        }*/
    }

    suspend fun getAllChapters() {
        val result = launchSafely<ChaptersAqc> { quranTextUseCaseAqc.getAllChapters() }
        result.handle(object : HandleResult<ChaptersAqc> {
            override fun handleSuccess(data: ChaptersAqc) {
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

    suspend fun getArabicChapter(chapterNumber: Int) {
        val result = launchSafely { quranTextUseCaseAqc.getArabicChapter(chapterNumber) }
        result.handle(object : HandleResult<ArabicChaptersAqc> {
            override fun handleSuccess(data: ArabicChaptersAqc) {
                viewModelScope.launch {
                    Log.i("TAG", "handleSuccess: getChapter data $data")
                    _uiState.emit(_uiState.value.copy(currentArabicText = data))
                }
            }

            override fun handleError(e: Exception) {
                Log.e("TAG", "handleError: $e")
            }
        })
    }

}