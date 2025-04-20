package com.zaur.di.provides

import androidx.lifecycle.ViewModel

interface ProvideViewModel {

    fun <T : ViewModel> viewModel(clasz: Class<T>): T

    class Base() : ProvideViewModel {
        override fun <T : ViewModel> viewModel(clasz: Class<T>): T {
            return when (clasz) {
                /*MainViewModel::class.java -> MainViewModel(
                    dispatchers, globalErrorCommunication
                )
                */
                else -> throw IllegalStateException("unknown $clasz")
            } as T
        }
    }
}