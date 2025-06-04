package com.zaur.features.surah.viewmodel

import androidx.lifecycle.viewModelScope
import com.zaur.core.BaseViewModel
import com.zaur.core.HandleResult
import com.zaur.domain.al_quran_cloud.use_case.MainUseCase
import com.zaur.features.surah.manager.ReciterManager
import com.zaur.features.surah.manager.TranslatorManager
import com.zaur.features.surah.observables.MainObservable
import com.zaur.presentation.ui.ui_state.main.MainState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * @author Zaur
 * @since 2025-05-12
 */

interface MainViewModel : MainObservable.Read {

    fun getReciter(): String?
    fun saveReciter(identifier: String)
    fun getReciterName(): String?

    fun getTranslator(): String?
    fun saveTranslator(identifier: String)
    fun getTranslatorName(): String?

    fun loadQuranData()
    fun loadChaptersArabic(chaptersNumbers: IntRange = 1..114)/*fun loadChaptersAudio(
        chaptersNumbers: IntRange = 1..114,
        reciter: String,
    )*/

    fun loadChaptersTranslate(
        chaptersNumbers: IntRange = 1..114,
        translator: String,
    )

    class Base(
        private val mainUseCase: MainUseCase,
        private val reciterManager: ReciterManager,
        private val translatorManager: TranslatorManager,
        private val observable: MainObservable.Mutable,
    ) : BaseViewModel(), MainViewModel {

        init {
            // При инициализации читаем флаги из LoadPrefs и обновляем состояние
            val initial = MainState(
                isChaptersLoaded = mainUseCase.isChaptersLoaded(),
                isChaptersArabicsLoaded = mainUseCase.isArabicsLoaded(),
                isChaptersTranslationsLoaded = mainUseCase.isTranslationsLoaded()
            )
            observable.update(initial)
        }

        override fun quranState(): StateFlow<MainState> = observable.quranState()

        override fun getReciter(): String? = reciterManager.getReciter()

        override fun saveReciter(identifier: String) = reciterManager.saveReciter(identifier)

        override fun getReciterName(): String? = reciterManager.getReciterName()

        override fun getTranslator(): String? = translatorManager.getTranslator()

        override fun saveTranslator(identifier: String) =
            translatorManager.saveTranslator(identifier)

        override fun getTranslatorName(): String? = translatorManager.getTranslatorName()

        override fun loadQuranData() {
            viewModelScope.launch(Dispatchers.IO) {
                launchSafely {
                    mainUseCase.loadChapters()
                }.handle(object : HandleResult<Unit> {
                    override fun handleSuccess(data: Unit) {
                        mainUseCase.markChaptersLoaded()
                        observable.update(observable.quranState().value.copy(isChaptersLoaded = true))
                    }
                })
            }
        }

        override fun loadChaptersArabic(chaptersNumbers: IntRange) {
            viewModelScope.launch(Dispatchers.IO) {
                launchSafely {
                    mainUseCase.loadChaptersArabic(chaptersNumbers)
                }.handle(object : HandleResult<Unit> {
                    override fun handleSuccess(data: Unit) {
                        mainUseCase.markArabicsLoaded()
                        observable.update(observable.quranState().value.copy(isChaptersArabicsLoaded = true))
                    }
                })
            }
        }

        /*override fun loadChaptersAudio( //todo не надо
            chaptersNumbers: IntRange,
            reciter: String,
        ) {
            viewModelScope.launch(Dispatchers.IO) {
                val result = launchSafely {
                    mainUseCase.loadChaptersAudio(chaptersNumbers, reciter)
                }
                result.handle(object : HandleResult<Unit> {
                    override fun handleSuccess(data: Unit) {
                        observable.update(observable.quranState().value.copy(isChaptersAudiosLoaded = true))
                    }
                })
            }
        }*/

        override fun loadChaptersTranslate(
            chaptersNumbers: IntRange,
            translator: String,
        ) {
            viewModelScope.launch(Dispatchers.IO) {
                launchSafely {
                    mainUseCase.loadChaptersTranslate(chaptersNumbers, translator)
                }.handle(object : HandleResult<Unit> {
                    override fun handleSuccess(data: Unit) {
                        mainUseCase.markTranslationsLoaded()
                        observable.update(
                            observable.quranState().value.copy(
                                isChaptersTranslationsLoaded = true
                            )
                        )
                    }
                })
            }
        }


    }

}