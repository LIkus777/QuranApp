package com.zaur.core

import android.util.Log

/**
* @author Zaur
* @since 2025-05-12
*/

sealed class Result<T> {
    abstract fun handle(handleResult: HandleResult<T>)

    class Success<T>(private val data: T) : Result<T>() {
        override fun handle(handleResult: HandleResult<T>) {
            handleResult.handleSuccess(data)
        }
    }

    class Error<T>(private val e: Exception) : Result<T>() { // Добавили <T>
        override fun handle(handleResult: HandleResult<T>) {
            handleResult.handleError(e)
        }
    }
}

interface HandleResult<T> {
    fun handleSuccess(data: T)
    fun handleError(e: Exception) {
        Log.e("TAG", "handleError: $e")
    }
}