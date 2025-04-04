package com.zaur.features.surah.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.zaur.core.BaseViewModel
import com.zaur.core.HandleResult
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudiosFileAqc
import com.zaur.domain.al_quran_cloud.models.audiofile.VersesAudioFileAqc
import com.zaur.domain.al_quran_cloud.use_case.QuranAudioUseCaseAqc
import com.zaur.features.surah.ui_state.aqc.QuranAudioAqcUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuranAudioViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val quranAudioUseCaseAqc: QuranAudioUseCaseAqc
) : BaseViewModel(savedStateHandle) {

    private val _uiState = MutableStateFlow(QuranAudioAqcUIState())
    val audioUiState: StateFlow<QuranAudioAqcUIState> = _uiState

    fun getChaptersAudioOfReciter(chapterNumber: Int, reciter: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = launchSafely {
                quranAudioUseCaseAqc.getChapterAudioOfReciter(
                    chapterNumber,
                    reciter
                )
            }
            result.handle(object : HandleResult<ChapterAudiosFileAqc> {
                override fun handleSuccess(data: ChapterAudiosFileAqc) {
                    viewModelScope.launch {
                        _uiState.emit(_uiState.value.copy(chaptersAudioFile = data))
                    }
                }

                override fun handleError(e: Exception) {
                    super.handleError(e)
                }
            })
        }
    }

    fun getVerseAudioFile(verseKey: String, reciter: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = launchSafely { quranAudioUseCaseAqc.getVerseAudioFile(verseKey, reciter) }
            result.handle(object : HandleResult<VersesAudioFileAqc> {
                override fun handleSuccess(data: VersesAudioFileAqc) {
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

}