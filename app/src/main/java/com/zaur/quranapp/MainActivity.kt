package com.zaur.quranapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.zaur.features.surah.viewmodel.OfflineViewModel
import com.zaur.features.surah.viewmodel.ThemeViewModel

/**
 * @author Zaur
 * @since 2025-05-12
 */

class MainActivity : ComponentActivity() {

    private val di by lazy { (application as App).diModule() }
    private val mainScreenModule by lazy { di.provideMainScreenModule() }
    private val dataModule by lazy { di.provideDataModule() }

    private val themeViewModel by lazy { ThemeViewModel.Base(themeUseCase = mainScreenModule.provideThemeUseCase()) }
    private val offlineViewModel by lazy { OfflineViewModel.Base(offlineUseCase = dataModule.provideOfflineUseCase()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppContent(di, themeViewModel, offlineViewModel, mainScreenModule)
        }
    }
}