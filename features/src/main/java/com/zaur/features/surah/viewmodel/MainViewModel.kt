package com.zaur.features.surah.viewmodel

import androidx.lifecycle.viewModelScope
import com.zaur.core.BaseViewModel
import com.zaur.core.HandleResult
import com.zaur.domain.al_quran_cloud.use_case.MainUseCase
import com.zaur.features.surah.manager.ReciterManager
import com.zaur.features.surah.observables.MainObservable
import com.zaur.features.surah.ui_state.main.MainState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

interface MainViewModel : MainObservable.Read {

    fun getReciter(): String?
    fun saveReciter(identifier: String)
    fun getReciterName(): String?

    fun loadQuranData()
    fun loadChaptersArabic(chaptersNumbers: IntRange = 1..114)
    fun loadChaptersAudio(
        chaptersNumbers: IntRange = 1..114,
        reciter: String,
    )

    fun loadChaptersTranslate(
        chaptersNumbers: IntRange = 1..114,
        translator: String,
    )

    class Base(
        private val mainUseCase: MainUseCase,
        private val reciterManager: ReciterManager,
        private val observable: MainObservable.Mutable,
    ) : BaseViewModel(), MainViewModel {

        override fun quranState(): StateFlow<MainState> = observable.quranState()

        override fun getReciter(): String? = reciterManager.getReciter()

        override fun saveReciter(identifier: String) = reciterManager.saveReciter(identifier)

        override fun getReciterName(): String? = reciterManager.getReciterName()

        override fun loadQuranData() {
            viewModelScope.launch(Dispatchers.IO) {
                val result = launchSafely {
                    mainUseCase.loadChapters()
                }
                result.handle(object : HandleResult<Unit> {
                    override fun handleSuccess(data: Unit) {
                        observable.update(observable.state().value.copy(isChaptersLoaded = true))
                    }

                    override fun handleError(e: Exception) {
                        super.handleError(e)
                    }
                })
            }
        }

        override fun loadChaptersArabic(chaptersNumbers: IntRange) {
            viewModelScope.launch(Dispatchers.IO) {
                val result = launchSafely {
                    mainUseCase.loadChaptersArabic(chaptersNumbers)
                }
                result.handle(object : HandleResult<Unit> {
                    override fun handleSuccess(data: Unit) {
                        observable.update(observable.state().value.copy(isChaptersArabicsLoaded = true))
                    }

                    override fun handleError(e: Exception) {
                        super.handleError(e)
                    }
                })
            }
        }

        override fun loadChaptersAudio(
            chaptersNumbers: IntRange,
            reciter: String,
        ) {
            viewModelScope.launch(Dispatchers.IO) {
                val result = launchSafely {
                    mainUseCase.loadChaptersAudio(chaptersNumbers, reciter)
                }
                result.handle(object : HandleResult<Unit> {
                    override fun handleSuccess(data: Unit) {
                        observable.update(observable.state().value.copy(isChaptersAudiosLoaded = true))
                    }

                    override fun handleError(e: Exception) {
                        super.handleError(e)
                    }
                })
            }
        }

        override fun loadChaptersTranslate(
            chaptersNumbers: IntRange,
            translator: String,
        ) {
            viewModelScope.launch(Dispatchers.IO) {
                val result = launchSafely {
                    mainUseCase.loadChaptersTranslate(chaptersNumbers, translator)
                }
                result.handle(object : HandleResult<Unit> {
                    override fun handleSuccess(data: Unit) {
                        observable.update(observable.state().value.copy(isChaptersTranslationsLoaded = true))
                    }

                    override fun handleError(e: Exception) {
                        super.handleError(e)
                    }
                })
            }
        }


    }

}