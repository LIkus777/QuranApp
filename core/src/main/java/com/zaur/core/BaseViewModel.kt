package com.zaur.core

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
* @author Zaur
* @since 2025-05-12
*/

abstract class BaseViewModel(
    private val savedStateHandle: SavedStateHandle? = null
) : ViewModel() {

    // Глобальный обработчик ошибок
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        /*_isLoading.value = false //todo
        _error.value = throwable.message*/ //todo
        handleError(throwable)
    }

    // Метод для обработки ошибок (можно переопределять в наследниках)
    protected open fun handleError(error: Throwable) {
        // получаем весь стек в виде строки
        val trace = error.stackTrace
            .firstOrNull { it.className.startsWith("com.zaur.quranapp") }
            ?.let { "${it.className}.${it.methodName}(${it.fileName}:${it.lineNumber})" }
            ?: "место не определено"

        Log.e("ErrorOrigin",
            "handleError: ${error::class.java.simpleName} в $trace",
            error
        )
    }

    // Запуск корутины с обработкой ошибок
    protected suspend fun <T> launchSafely(block: suspend () -> T): Result<T> {
        return withContext(Dispatchers.IO) {
            try {
                Result.Success(block()) // Вызываем функцию и получаем результат
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    /*// Отправка одноразового события
    protected fun sendEvent(event: UiEvent) {
        viewModelScope.launch { _eventFlow.emit(event) } //todo
    }*/
}