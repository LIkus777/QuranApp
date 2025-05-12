package com.zaur.features.surah.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.zaur.core.BaseViewModel
import com.zaur.core.HandleResult
import com.zaur.domain.al_quran_cloud.models.chapter.ChapterAqc
import com.zaur.domain.al_quran_cloud.use_case.QuranTextUseCase
import com.zaur.features.surah.observables.SurahChooseObservable
import com.zaur.features.surah.ui_state.aqc.QuranTextAqcUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
* @author Zaur
* @since 2025-05-12
*/

interface SurahChooseViewModel : SurahChooseObservable.Read {

    fun getAllChapters()

    class Base(
        private val observable: SurahChooseObservable.Mutable,
        private val quranTextUseCase: QuranTextUseCase
    ) : BaseViewModel(), SurahChooseViewModel {

        override fun textState(): StateFlow<QuranTextAqcUIState.Base> = observable.textState()

        override fun getAllChapters() {
            Log.d("TAG", "getAllChaptersCloud() CALLED")
            viewModelScope.launch(Dispatchers.IO) {
                val result = launchSafely<List<ChapterAqc>> { quranTextUseCase.getAllChapters() }
                result.handle(object : HandleResult<List<ChapterAqc>> {
                    override fun handleSuccess(data: List<ChapterAqc>) {
                        viewModelScope.launch {
                            Log.i("TAGGG", "handleSuccess: getAllChapters data $data")
                            observable.update(observable.state().value.copy(chapters = data))
                        }
                    }
                })
            }
        }
    }
}