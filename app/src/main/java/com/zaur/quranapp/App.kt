package com.zaur.quranapp

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.zaur.di.DI

class App() : Application() {

    lateinit var diModule: DI

    override fun onCreate() {
        super.onCreate()
        diModule = DI.Base(this)
    }

}