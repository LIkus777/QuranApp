package com.zaur.quranapp

import android.app.Application
import com.zaur.di.DI

/**
* @author Zaur
* @since 2025-05-12
*/

class App() : Application() {

    private lateinit var diModule: DI

    fun diModule(): DI = diModule

    override fun onCreate() {
        super.onCreate()
        diModule = DI.Base(this)
    }

}