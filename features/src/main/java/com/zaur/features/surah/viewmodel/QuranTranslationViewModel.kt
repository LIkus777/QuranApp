package com.zaur.features.surah.viewmodel

import androidx.lifecycle.viewModelScope
import com.zaur.core.BaseViewModel
import com.zaur.core.HandleResult
import com.zaur.domain.al_quran_cloud.models.translate.TranslationAqc
import com.zaur.domain.al_quran_cloud.models.translate.TranslationsChapterAqc
import com.zaur.domain.al_quran_cloud.use_case.QuranTranslationUseCaseAqc
import com.zaur.features.surah.observables.QuranTranslationObservable
import com.zaur.features.surah.ui_state.aqc.QuranTranslationAqcUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

interface QuranTranslationViewModel : QuranTranslationObservable.Read {
    fun getTranslationForChapter(chapterNumber: Int, translator: String)

    class Base(
        private val observable: QuranTranslationObservable.Mutable,
        private val quranTranslationUseCaseAqc: QuranTranslationUseCaseAqc
    ) : BaseViewModel(), QuranTranslationViewModel, QuranTranslationObservable.Read {

        override fun translationState(): StateFlow<QuranTranslationAqcUIState> =
            observable.translationState()

        override fun getTranslationForChapter(chapterNumber: Int, translator: String) {
            viewModelScope.launch(Dispatchers.IO) {
                val result = launchSafely {
                    quranTranslationUseCaseAqc.getTranslationForChapter(
                        chapterNumber, translator
                    )
                }
                result.handle(object : HandleResult<TranslationAqc> {
                    override fun handleSuccess(data: TranslationAqc) {
                        viewModelScope.launch {
                            observable.update(observable.state().value.copy(translations = data))
                        }
                    }

                    override fun handleError(e: Exception) {
                        super.handleError(e)
                    }
                })
            }
        }

    }
}