package com.zaur.features.surah.viewmodel

import androidx.lifecycle.viewModelScope
import com.zaur.core.BaseViewModel
import com.zaur.core.HandleResult
import com.zaur.domain.al_quran_cloud.models.edition.Editions
import com.zaur.domain.al_quran_cloud.models.edition.Languages
import com.zaur.domain.al_quran_cloud.models.edition.Types
import com.zaur.domain.al_quran_cloud.use_case.EditionUseCase
import com.zaur.features.surah.observables.EditionObservable
import com.zaur.presentation.ui.ui_state.aqc.EditionUIState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


/**
 * @author Zaur
 * @since 21.05.2025
 */

interface EditionViewModel : EditionObservable.Read {

    fun getAllTypes()
    fun getAllEditions()
    fun getAllLanguages()
    fun getEditionByType(type: String)
    fun getEditionByLanguage(language: String)
    fun getEditionByParam(format: String, language: String, type: String)

    class Base(
        private val observable: EditionObservable.Mutable,
        private val editionUseCase: EditionUseCase,
    ) : BaseViewModel(), EditionViewModel {

        override fun editionState(): StateFlow<EditionUIState.Base> = observable.editionState()

        override fun getAllTypes() {
            viewModelScope.launch {
                val result = launchSafely { editionUseCase.getAllTypes() }
                result.handle(object : HandleResult<Types.Base> {
                    override fun handleSuccess(data: Types.Base) {
                        observable.update(observable.editionState().value.copy(allTypes = data))
                    }
                })
            }
        }

        override fun getAllEditions() {
            viewModelScope.launch {
                val result = launchSafely { editionUseCase.getAllEditions() }
                result.handle(object : HandleResult<Editions.Base> {
                    override fun handleSuccess(data: Editions.Base) {
                        observable.update(observable.editionState().value.copy(allEditions = data))
                    }
                })
            }
        }

        override fun getAllLanguages() {
            viewModelScope.launch {
                val result = launchSafely { editionUseCase.getAllLanguages() }
                result.handle(object : HandleResult<Languages.Base> {
                    override fun handleSuccess(data: Languages.Base) {
                        observable.update(observable.editionState().value.copy(allLanguages = data))
                    }
                })
            }
        }

        override fun getEditionByType(type: String) {
            viewModelScope.launch {
                val result = launchSafely { editionUseCase.getEditionByType(type) }
                result.handle(object : HandleResult<Editions.Base> {
                    override fun handleSuccess(data: Editions.Base) {
                        observable.update(observable.editionState().value.copy(editionByType = data))
                    }
                })
            }
        }

        override fun getEditionByLanguage(language: String) {
            viewModelScope.launch {
                val result = launchSafely { editionUseCase.getEditionByLanguage(language) }
                result.handle(object : HandleResult<Editions.Base> {
                    override fun handleSuccess(data: Editions.Base) {
                        observable.update(observable.editionState().value.copy(editionByLanguage = data))
                    }
                })
            }
        }

        override fun getEditionByParam(
            format: String,
            language: String,
            type: String,
        ) {
            viewModelScope.launch {
                val result =
                    launchSafely { editionUseCase.getEditionByParam(format, language, type) }
                result.handle(object : HandleResult<Editions.Base> {
                    override fun handleSuccess(data: Editions.Base) {
                        observable.update(observable.editionState().value.copy(editionByParam = data))
                    }
                })
            }
        }
    }

}