package com.zaur.features.surah.screen.main

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.zaur.features.surah.screen.surah_detail.dialogs.ChooseReciterDialog
import com.zaur.features.surah.viewmodel.MainViewModel
import com.zaur.features.surah.viewmodel.ThemeViewModel
import com.zaur.navigation.Screen

/**
 * @author Zaur
 * @since 2025-05-12
 */

@Composable
fun MainScreen(
    navController: NavHostController,
    themeViewModel: ThemeViewModel,
    mainViewModel: MainViewModel,
) {
    val mainState by mainViewModel.quranState().collectAsState()
    var showReciterDialog by rememberSaveable { mutableStateOf(mainViewModel.getReciter() == null) }
    var showTranslatorDialog by rememberSaveable { mutableStateOf(mainViewModel.getReciter() == null) }
    var showLoader by rememberSaveable { mutableStateOf(false) }
    themeViewModel.themeState().collectAsState().value.isDarkTheme

    // Dialog to pick reciter once
    if (showReciterDialog) {
        ChooseReciterDialog(showDialog = true, onSelect = { id ->
            mainViewModel.saveReciter(id)
            showReciterDialog = false
            showLoader = true
            // start loading all data
            mainViewModel.loadQuranData()
            mainViewModel.loadChaptersArabic()
            //mainViewModel.loadChaptersAudio(reciter = id)
            mainViewModel.loadChaptersTranslate(translator = "ru.kuliev")
        })
    }

    if (showTranslatorDialog) {

    }

    // Show loading until all parts are loaded
    if (showLoader) {
        QuranDataLoadingUI(mainState)
        // Navigate when done
    }

    if (/*mainState.isChaptersLoaded && mainState.isChaptersArabicsLoaded && mainState.isChaptersTranslationsLoaded*/true) {
        LaunchedEffect(Unit) {
            Log.i("MainScreen", "All data loaded, navigating...")
            navController.navigate(Screen.SurahChoose.route)
        }
    }
}

@Composable
fun LoadingItem(
    isLoaded: Boolean,
    loadingText: String,
    successText: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isLoaded) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = Color(0xFF4CAF50) // зелёный
            )
            Text(successText)
        } else {
            CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
            Text(loadingText)
        }
    }
}