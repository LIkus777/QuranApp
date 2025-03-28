package com.zaur.features.surah.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.zaur.core.BaseViewModel
import com.zaur.core.HandleResult
import com.zaur.domain.models.audiofile.ChaptersAudioFile
import com.zaur.domain.models.audiofile.VerseAudioFile
import com.zaur.domain.models.recitations.Recitations
import com.zaur.domain.apiV4.use_case.QuranAudioUseCaseV4
import com.zaur.features.surah.ui_state.QuranAudioUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuranAudioViewModel(
    private val savedStateHandle: SavedStateHandle, private val quranAudioUseCaseV4: QuranAudioUseCaseV4
) : BaseViewModel(savedStateHandle) {

    private val _uiState = MutableStateFlow(QuranAudioUIState())
    val audioUiState: StateFlow<QuranAudioUIState> = _uiState

    suspend fun getRecitations(language: String) {
        val result = launchSafely { quranAudioUseCaseV4.getRecitations(language) }
        result.handle(object : HandleResult<List<Recitations>> {
            override fun handleSuccess(data: List<Recitations>) {
                viewModelScope.launch {
                    _uiState.emit(_uiState.value.copy(recitations = data))
                }
            }

            override fun handleError(e: Exception) {
                super.handleError(e)
            }
        })
    }

    suspend fun getChaptersAudioOfReciter(reciterId: Int, chapterNumber: Int) {
        val result =
            launchSafely { quranAudioUseCaseV4.getChaptersAudioOfReciter(reciterId, chapterNumber) }
        result.handle(object : HandleResult<ChaptersAudioFile> {
            override fun handleSuccess(data: ChaptersAudioFile) {
                viewModelScope.launch {
                    _uiState.emit(_uiState.value.copy(chaptersAudioFile = data))
                }
            }

            override fun handleError(e: Exception) {
                super.handleError(e)
            }
        })
    }

    suspend fun getVerseAudioFile(reciterId: Int, verseKey: String) {
        val result = launchSafely { quranAudioUseCaseV4.getVerseAudioFile(reciterId, verseKey) }
        result.handle(object : HandleResult<VerseAudioFile> {
            override fun handleSuccess(data: VerseAudioFile) {
                viewModelScope.launch {
                    _uiState.emit(_uiState.value.copy(verseAudioFile = data))
                }
            }

            override fun handleError(e: Exception) {
                super.handleError(e)
            }
        })
    }

}