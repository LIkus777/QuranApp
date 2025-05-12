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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.zaur.features.surah.screen.surah_detail.ChooseReciterDialog
import com.zaur.features.surah.viewmodel.ThemeViewModel
import com.zaur.features.surah.viewmodel.factory.MainViewModelFactory
import com.zaur.navigation.Screen
import com.zaur.presentation.ui.DarkThemeColors
import com.zaur.presentation.ui.LightThemeColors

/**
* @author Zaur
* @since 2025-05-12
*/

@Composable
fun MainScreen(
    navController: NavHostController,
    themeViewModel: ThemeViewModel,
    mainViewModelFactory: MainViewModelFactory,
) {
    val mainViewModel = remember { mainViewModelFactory.create() }
    val mainState by mainViewModel.quranState().collectAsState()

    var showReciterDialog by rememberSaveable { mutableStateOf(mainViewModel.getReciter() == null) }
    var selectedReciter by rememberSaveable { mutableStateOf<String?>(null) }
    var showAudioChoiceDialog by rememberSaveable { mutableStateOf(false) }
    var showDownloadScreen by rememberSaveable { mutableStateOf(false) }

    val isDarkTheme = themeViewModel.getIsDarkTheme()
    val colors = if (isDarkTheme) DarkThemeColors else LightThemeColors

    LaunchedEffect(selectedReciter) {
        if (!selectedReciter.isNullOrEmpty()) {
            showAudioChoiceDialog = true
        }
    }

    if (showReciterDialog) {
        ChooseReciterDialog(
            showDialog = true, colors = colors
        ) { identifier ->
            if (!identifier.isNullOrEmpty()) mainViewModel.saveReciter(identifier)
            selectedReciter = mainViewModel.getReciterName().toString()
            showReciterDialog = false
        }
    }

    if (showAudioChoiceDialog) {
        ListenModeDialog(onDismiss = { showAudioChoiceDialog = true }, onChooseOnline = {
            showAudioChoiceDialog = false
            navController.navigate(Screen.SurahChoose.route)
        }, onChooseOffline = {
            showAudioChoiceDialog = false
            showDownloadScreen = true
            selectedReciter?.let {
                mainViewModel.loadQuranData()
                mainViewModel.loadChaptersArabic()
                mainViewModel.loadChaptersAudio(reciter = it)
                mainViewModel.loadChaptersTranslate(translator = "ru.kuliev")
            }
        })
    }

    if (showDownloadScreen) {
        QuranDataLoadingUI(mainState, navController)
    } else if (!showReciterDialog && !showAudioChoiceDialog) {
        // Мы попадаем сюда ТОЛЬКО после закрытия обоих диалогов, и только один раз
        LaunchedEffect(Unit) {
            Log.i("TAG", "navController.navigate(Screen.SurahChoose.route) CALLED")
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