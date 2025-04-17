package com.zaur.features.surah.viewmodel

import androidx.lifecycle.viewModelScope
import com.zaur.core.BaseViewModel
import com.zaur.core.HandleResult
import com.zaur.domain.al_quran_cloud.models.chapter.ChaptersAqc
import com.zaur.domain.al_quran_cloud.use_case.MainUseCase
import com.zaur.features.surah.observables.MainObservable
import com.zaur.features.surah.ui_state.main.MainState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

interface MainViewModel : MainObservable.Read {

    fun loadQuranData()

    class Base(
        private val observable: MainObservable.Mutable,
        private val mainUseCase: MainUseCase
    ) : BaseViewModel(), MainViewModel {

        override fun quranState(): StateFlow<MainState> = observable.quranState()

        override fun loadQuranData() {
            viewModelScope.launch(Dispatchers.IO) {
                val result = launchSafely {
                    mainUseCase.loadChapters()
                }
                result.handle(object : HandleResult<ChaptersAqc> {
                    override fun handleSuccess(data: ChaptersAqc) {

                    }

                    override fun handleError(e: Exception) {
                        super.handleError(e)
                    }
                })
            }
        }
    }

}