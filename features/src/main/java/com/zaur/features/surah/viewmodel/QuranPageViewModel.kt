package com.zaur.features.surah.viewmodel

import com.zaur.core.BaseViewModel
import com.zaur.domain.al_quran_cloud.models.page.QuranPageAqc
import com.zaur.domain.al_quran_cloud.use_case.QuranPageUseCase
import com.zaur.features.surah.observables.QuranPageObservable
import com.zaur.features.surah.ui_state.aqc.QuranPageAqcUIState
import kotlinx.coroutines.flow.StateFlow


/**
 * @author Zaur
 * @since 14.05.2025
 */

interface QuranPageViewModel : QuranPageObservable.Read {

    suspend fun getUthmaniPage(page: Int): QuranPageAqc
    suspend fun getTranslatedPage(page: Int, translator: String): QuranPageAqc

    class Base(
        private val quranPageUseCase: QuranPageUseCase,
        private val observable: QuranPageObservable.Mutable,
    ) : BaseViewModel(), QuranPageViewModel {
        override fun pageState(): StateFlow<QuranPageAqcUIState.Base> = observable.pageState()

        override suspend fun getUthmaniPage(page: Int): QuranPageAqc =
            quranPageUseCase.getUthmaniPage(page)

        override suspend fun getTranslatedPage(page: Int, translator: String): QuranPageAqc =
            quranPageUseCase.getTranslatedPage(page, translator)
    }

}