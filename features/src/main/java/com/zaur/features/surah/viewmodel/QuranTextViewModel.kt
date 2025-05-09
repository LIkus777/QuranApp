package com.zaur.features.surah.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.zaur.core.BaseViewModel
import com.zaur.core.HandleResult
import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChapter
import com.zaur.domain.al_quran_cloud.models.chapter.ChapterAqc
import com.zaur.domain.al_quran_cloud.use_case.QuranTextUseCaseAqc
import com.zaur.features.surah.observables.QuranTextObservable
import com.zaur.features.surah.ui_state.aqc.QuranTextAqcUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

interface QuranTextViewModel : QuranTextObservable.Read {

    fun getFontSizeArabic(): Float
    fun getFontSizeRussian(): Float
    fun saveFontSizeArabic(size: Float)
    fun saveFontSizeRussian(size: Float)

    fun getAllChapters()
    fun getArabicChapter(chapterNumber: Int)

    class Base(
        private val observable: QuranTextObservable.Mutable,
        private val quranTextUseCaseAqc: QuranTextUseCaseAqc,
    ) : BaseViewModel(), QuranTextViewModel {

        override fun textState(): StateFlow<QuranTextAqcUIState> = observable.textState()

        override fun getFontSizeArabic() = quranTextUseCaseAqc.getFontSizeArabic()

        override fun getFontSizeRussian() = quranTextUseCaseAqc.getFontSizeRussian()

        override fun saveFontSizeArabic(size: Float) {
            quranTextUseCaseAqc.saveFontSizeArabic(size)
        }

        override fun saveFontSizeRussian(size: Float) {
            quranTextUseCaseAqc.saveFontSizeRussian(size)
        }

        override fun getAllChapters() {
            viewModelScope.launch(Dispatchers.IO) {
                val result =
                    launchSafely<List<ChapterAqc.Base>> { quranTextUseCaseAqc.getAllChapters() }
                result.handle(object : HandleResult<List<ChapterAqc.Base>> {
                    override fun handleSuccess(data: List<ChapterAqc.Base>) {
                        viewModelScope.launch {
                            Log.i("TAG", "handleSuccess: getAllChapters data $data")
                            observable.update(observable.state().value.copy(chapters = data))
                        }
                    }
                })
            }
        }

        override fun getArabicChapter(chapterNumber: Int) {
            viewModelScope.launch(Dispatchers.IO) {
                val result = launchSafely { quranTextUseCaseAqc.getArabicChapter(chapterNumber) }
                result.handle(object : HandleResult<ArabicChapter.Base> {
                    override fun handleSuccess(data: ArabicChapter.Base) {
                        viewModelScope.launch {
                            observable.update(observable.state().value.copy(currentArabicText = data))
                        }
                    }
                })
            }
        }

    }
}