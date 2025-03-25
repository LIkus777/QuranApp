package com.zaur.core

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Глобальный обработчик ошибок
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        /*_isLoading.value = false //todo
        _error.value = throwable.message*/ //todo
        handleError(throwable)
    }

    // Метод для обработки ошибок (можно переопределять в наследниках)
    protected open fun handleError(error: Throwable) {
        // Логирование или специфическая обработка
        Log.e("TAG", "handleError: $error")
    }

    // Запуск корутины с обработкой ошибок
    protected fun launchSafely(
        onSuccess: () -> Unit = {},
        onError: (Throwable) -> Unit = { handleError(it) },
        block: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch(exceptionHandler) {
            //_isLoading.value = true //todo
            try {
                block()
                onSuccess()
            } catch (e: Throwable) {
                onError(e)
            } finally {
                //_isLoading.value = false //todo
            }
        }
    }

    /*// Отправка одноразового события
    protected fun sendEvent(event: UiEvent) {
        viewModelScope.launch { _eventFlow.emit(event) } //todo
    }*/
}