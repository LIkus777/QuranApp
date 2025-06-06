package com.zaur.features.surah.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.zaur.core.BaseViewModel
import com.zaur.core.HandleResult
import com.zaur.domain.al_quran_cloud.models.translate.Translation
import com.zaur.domain.al_quran_cloud.use_case.QuranTranslationUseCase
import com.zaur.features.surah.observables.QuranTranslationObservable
import com.zaur.presentation.ui.ui_state.aqc.QuranTranslationUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
* @author Zaur
* @since 2025-05-12
*/

interface QuranTranslationViewModel : QuranTranslationObservable.Read {
    fun getTranslationForChapter(chapterNumber: Int, translator: String)

    class Base(
        private val observable: QuranTranslationObservable.Mutable,
        private val quranTranslationUseCase: QuranTranslationUseCase
    ) : BaseViewModel(), QuranTranslationViewModel {

        override fun translationState(): StateFlow<QuranTranslationUIState.Base> =
            observable.translationState()

        override fun getTranslationForChapter(chapterNumber: Int, translator: String) {
            viewModelScope.launch(Dispatchers.IO) {
                Log.i("TAG", "getTranslationForChapter: chapterNumber $chapterNumber")
                Log.i("TAG", "getTranslationForChapter: translator $translator")
                val result = launchSafely {
                    quranTranslationUseCase.getTranslationForChapter(
                        chapterNumber, translator
                    )
                }
                result.handle(object : HandleResult<Translation> {
                    override fun handleSuccess(data: Translation) {
                        viewModelScope.launch(Dispatchers.Main) {
                            Log.i("TAG", "getTranslationForChapter: DATA - $data")
                            observable.update(observable.translationState().value.copy(translations = data))
                        }
                    }
                })
            }
        }

    }
}