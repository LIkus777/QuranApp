package com.zaur.features.surah.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.zaur.core.BaseViewModel
import com.zaur.core.HandleResult
import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChapter
import com.zaur.domain.al_quran_cloud.models.chapter.ChapterAqc
import com.zaur.domain.al_quran_cloud.use_case.QuranTextUseCase
import com.zaur.features.surah.manager.SurahDetailStateManager
import com.zaur.features.surah.observables.QuranTextObservable
import com.zaur.presentation.ui.ui_state.aqc.QuranTextUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * @author Zaur
 * @since 2025-05-12
 */

interface QuranTextViewModel : QuranTextObservable.Read {

    fun onNextPage()
    fun onPreviousPage()

    fun getFontSizeArabic(): Float
    fun getFontSizeRussian(): Float
    fun saveFontSizeArabic(size: Float)
    fun saveFontSizeRussian(size: Float)

    fun getAllChapters()
    fun getArabicChapter(chapterNumber: Int)

    fun getLastReadSurah(): Int
    fun setLastReadSurah(surahNumber: Int)
    fun getLastReadAyahPosition(chapterNumber: Int): Int
    fun saveLastReadAyahPosition(chapterNumber: Int, ayahNumber: Int)

    class Base(
        private val observable: QuranTextObservable.Mutable,
        private val quranTextUseCase: QuranTextUseCase,
        private val stateManager: SurahDetailStateManager,
    ) : BaseViewModel(), QuranTextViewModel {

        override fun textState(): StateFlow<QuranTextUIState.Base> = observable.textState()

        override fun onNextPage() {
            viewModelScope.launch {
                launchSafely {
                    quranTextUseCase.onNextPage(
                        stateManager.state().value.textState().currentPageNumber()
                    )
                }
            }
        }

        override fun onPreviousPage() {
            viewModelScope.launch {
                launchSafely {
                    quranTextUseCase.onPreviousPage(
                        stateManager.state().value.textState().currentPageNumber()
                    )
                }
            }
        }

        override fun getFontSizeArabic() = quranTextUseCase.getFontSizeArabic()

        override fun getFontSizeRussian() = quranTextUseCase.getFontSizeRussian()

        override fun saveFontSizeArabic(size: Float) {
            quranTextUseCase.saveFontSizeArabic(size)
        }

        override fun saveFontSizeRussian(size: Float) {
            quranTextUseCase.saveFontSizeRussian(size)
        }

        override fun getAllChapters() {
            viewModelScope.launch(Dispatchers.IO) {
                val result =
                    launchSafely<List<ChapterAqc.Base>> { quranTextUseCase.getAllChapters() }
                result.handle(object : HandleResult<List<ChapterAqc.Base>> {
                    override fun handleSuccess(data: List<ChapterAqc.Base>) {
                        viewModelScope.launch(Dispatchers.Main) {
                            Log.i("TAG", "handleSuccess: getAllChapters data $data")
                            observable.update(observable.textState().value.copy(chapters = data))
                        }
                    }
                })
            }
        }

        override fun getArabicChapter(chapterNumber: Int) {
            viewModelScope.launch(Dispatchers.IO) {
                val result = launchSafely { quranTextUseCase.getArabicChapter(chapterNumber) }
                result.handle(object : HandleResult<ArabicChapter.Base> {
                    override fun handleSuccess(data: ArabicChapter.Base) {
                        viewModelScope.launch(Dispatchers.Main) {
                            Log.i("TAG", "getArabicChapter: DATA - $data")
                            observable.update(observable.textState().value.copy(currentArabicText = data))
                        }
                    }
                })
            }
        }

        override fun setLastReadSurah(surahNumber: Int) =
            quranTextUseCase.setLastReadSurah(surahNumber)

        override fun getLastReadSurah(): Int = quranTextUseCase.getLastReadSurah()

        override fun saveLastReadAyahPosition(chapterNumber: Int, ayahNumber: Int) {
            Log.d("DEBUG_AYAH", "SAVE: chapter=$chapterNumber, ayah=$ayahNumber")
            quranTextUseCase.saveLastReadAyahPosition(chapterNumber, ayahNumber)
            stateManager.setAyahInText(ayahNumber)
        }

        override fun getLastReadAyahPosition(chapterNumber: Int): Int {
            val result = quranTextUseCase.getLastReadAyahPosition(chapterNumber)
            Log.d("DEBUG_AYAH", "LOAD: chapter=$chapterNumber, ayahLoaded=$result")
            return result
        }

    }
}