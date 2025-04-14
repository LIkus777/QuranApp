package com.zaur.features.surah.base

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

interface Observable {

    interface Update<T> {
        fun update(data: T)
    }

    interface Read<T> {
        fun state(): State<T> = throw IllegalStateException()
    }

    interface Mutable<T> : Update<T>, Read<T>

    abstract class Abstract<T>(
        private val initial: T,
        private val state: MutableState<T> = mutableStateOf(initial)
    ) : Mutable<T> {
        override fun update(data: T) {
            state.value = data
        }

        override fun state(): State<T> = state
    }

}