package com.zaur.features.surah.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.zaur.core.BaseViewModel
import com.zaur.core.HandleResult
import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChapter
import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChaptersAqc
import com.zaur.domain.al_quran_cloud.models.chapter.ChapterAqc
import com.zaur.domain.al_quran_cloud.models.chapter.ChaptersAqc
import com.zaur.domain.al_quran_cloud.use_case.QuranTextUseCaseAqc
import com.zaur.features.surah.observables.QuranTextObservable
import com.zaur.features.surah.ui_state.aqc.QuranTextAqcUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

interface QuranTextViewModel : QuranTextObservable.Read {

    fun getAllChapters()
    fun getArabicChapter(chapterNumber: Int)

    class Base(
        private val observable: QuranTextObservable.Mutable,
        private val quranTextUseCaseAqc: QuranTextUseCaseAqc
    ) : BaseViewModel(), QuranTextViewModel {

        override fun textState(): StateFlow<QuranTextAqcUIState> = observable.textState()

        override fun getAllChapters() {
            viewModelScope.launch(Dispatchers.IO) {
                val result = launchSafely<List<ChapterAqc>> { quranTextUseCaseAqc.fetchAllChapters() }
                result.handle(object : HandleResult<List<ChapterAqc>> {
                    override fun handleSuccess(data: List<ChapterAqc>) {
                        viewModelScope.launch {
                            Log.i("TAG", "handleSuccess: getAllChapters data $data")
                            observable.update(observable.state().value.copy(chapters = data))
                        }
                    }

                    override fun handleError(e: Exception) {
                        super.handleError(e)
                    }
                })
            }
        }

        override fun getArabicChapter(chapterNumber: Int) {
            viewModelScope.launch(Dispatchers.IO) {
                val result = launchSafely { quranTextUseCaseAqc.fetchArabicChapter(chapterNumber) }
                result.handle(object : HandleResult<ArabicChapter> {
                    override fun handleSuccess(data: ArabicChapter) {
                        viewModelScope.launch {
                            Log.i("TAG", "handleSuccess: getChapter data $data")
                            observable.update(observable.state().value.copy(currentArabicText = data))
                        }
                    }

                    override fun handleError(e: Exception) {
                        Log.e("TAG", "handleError: $e")
                    }
                })
            }
        }

    }
}