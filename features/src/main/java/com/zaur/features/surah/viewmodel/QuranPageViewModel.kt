package com.zaur.features.surah.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.zaur.core.BaseViewModel
import com.zaur.core.HandleResult
import com.zaur.domain.al_quran_cloud.models.page.QuranPage
import com.zaur.domain.al_quran_cloud.use_case.QuranPageUseCase
import com.zaur.features.surah.observables.QuranPageObservable
import com.zaur.features.surah.ui_state.aqc.QuranPageAqcUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


/**
 * @author Zaur
 * @since 14.05.2025
 */

interface QuranPageViewModel : QuranPageObservable.Read {

    suspend fun getUthmaniPage(page: Int)
    suspend fun getTranslatedPage(page: Int, translator: String)

    fun getLastReadPagePosition(): Int
    fun saveLastReadPagePosition(pageNumber: Int)

    class Base(
        private val quranPageUseCase: QuranPageUseCase,
        private val observable: QuranPageObservable.Mutable,
    ) : BaseViewModel(), QuranPageViewModel {
        override fun pageState(): StateFlow<QuranPageAqcUIState.Base> = observable.pageState()

        override suspend fun getUthmaniPage(page: Int) {
            viewModelScope.launch(Dispatchers.IO) {
                val result = launchSafely {
                    quranPageUseCase.getUthmaniPage(page).page()
                }
                result.handle(object : HandleResult<QuranPage> {
                    override fun handleSuccess(data: QuranPage) {
                        viewModelScope.launch(Dispatchers.IO) {
                            observable.update(observable.pageState().value.copy(uthmaniPage = data))
                        }
                    }
                })
            }
        }


        override suspend fun getTranslatedPage(page: Int, translator: String) {
            viewModelScope.launch(Dispatchers.IO) {
                val result = launchSafely {
                    quranPageUseCase.getTranslatedPage(page, translator).page()
                }
                result.handle(object : HandleResult<QuranPage> {
                    override fun handleSuccess(data: QuranPage) {
                        viewModelScope.launch(Dispatchers.IO) {
                            observable.update(observable.pageState().value.copy(translatedPage = data))
                        }
                    }
                })
            }
        }

        override fun getLastReadPagePosition(): Int {
            Log.i(
                "TAG",
                "getLastReadPagePosition: pageNumber ${quranPageUseCase.getLastReadPagePosition()}"
            )
            return quranPageUseCase.getLastReadPagePosition()
        }

        override fun saveLastReadPagePosition(pageNumber: Int) {
            Log.i("TAG", "saveLastReadPagePosition: pageNumber $pageNumber")
            quranPageUseCase.saveLastReadPagePosition(pageNumber)
            //todo сделать чтобы с сохранением страницы обновлялся observable getTranslatedPage.
            //todo чтобы арабский и перевод всегда после обновления были свежие, так как обновление вызывается только 1 раз - в SurahDetailEffects
        }
    }

}