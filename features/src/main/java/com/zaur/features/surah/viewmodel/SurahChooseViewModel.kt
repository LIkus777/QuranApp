package com.zaur.features.surah.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.zaur.core.BaseViewModel
import com.zaur.core.HandleResult
import com.zaur.domain.al_quran_cloud.models.chapter.ChaptersAqc
import com.zaur.domain.al_quran_cloud.use_case.QuranTextUseCaseAqc
import com.zaur.features.surah.observables.SurahChooseObservable
import com.zaur.features.surah.ui_state.aqc.QuranTextAqcUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

interface SurahChooseViewModel : SurahChooseObservable.Read {

    fun getAllChapters()

    class Base(
        private val observable: SurahChooseObservable.Mutable,
        private val quranTextUseCaseAqc: QuranTextUseCaseAqc
    ) : BaseViewModel(), SurahChooseViewModel {


        override fun textState(): StateFlow<QuranTextAqcUIState> = observable.textState()

        override fun getAllChapters() {
            viewModelScope.launch(Dispatchers.IO) {
                val result = launchSafely<ChaptersAqc> { quranTextUseCaseAqc.fetchAllChapters() }
                result.handle(object : HandleResult<ChaptersAqc> {
                    override fun handleSuccess(data: ChaptersAqc) {
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
    }
}