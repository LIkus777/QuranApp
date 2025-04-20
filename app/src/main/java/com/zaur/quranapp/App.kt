package com.zaur.quranapp

import android.app.Application
import com.zaur.di.DI

class App() : Application() {

    private lateinit var diModule: DI

    fun diModule(): DI = diModule

    override fun onCreate() {
        super.onCreate()
        diModule = DI.Base(this)
    }

}