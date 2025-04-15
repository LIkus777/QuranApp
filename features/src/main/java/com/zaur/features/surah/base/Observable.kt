package com.zaur.features.surah.base

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

interface Observable {

    interface Update<T> {
        fun update(data: T)
    }

    interface Read<T> {
        fun state(): StateFlow<T> = throw IllegalStateException()
    }

    interface Mutable<T> : Update<T>, Read<T>

    abstract class Abstract<T>(
        private val initial: T,
        private val state: MutableStateFlow<T> = MutableStateFlow(initial)
    ) : Mutable<T> {
        override fun update(data: T) {
            state.update { data }
        }

        override fun state(): StateFlow<T> = state
    }

}