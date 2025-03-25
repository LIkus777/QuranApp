package com.zaur.quranapp

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.zaur.di.DI

class App() : Application() {
    override fun onCreate() {
        super.onCreate()
        val diModule = DI.Base(this)
        val savedStateHandleText = SavedStateHandle()
    }

}