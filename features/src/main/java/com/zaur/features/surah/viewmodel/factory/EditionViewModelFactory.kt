package com.zaur.features.surah.viewmodel.factory

import com.zaur.domain.al_quran_cloud.use_case.EditionUseCase
import com.zaur.features.surah.observables.EditionObservable
import com.zaur.features.surah.viewmodel.EditionViewModel
import com.zaur.presentation.ui.ui_state.aqc.EditionUIState


/**
 * @author Zaur
 * @since 21.05.2025
 */

interface EditionViewModelFactory {

    fun create(): EditionViewModel

    class Base(
        private val observable: EditionObservable.Mutable = EditionObservable.Base(
            EditionUIState.Base()
        ),
        private val editionUseCase: EditionUseCase
    ) : EditionViewModelFactory {
        override fun create() = EditionViewModel.Base(
            observable = observable,
            editionUseCase = editionUseCase
        )
    }

}