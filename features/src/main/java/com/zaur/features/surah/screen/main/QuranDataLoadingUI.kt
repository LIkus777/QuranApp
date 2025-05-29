package com.zaur.features.surah.screen.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.zaur.presentation.ui.ui_state.main.MainState
import com.zaur.navigation.Screen

/**
* @author Zaur
* @since 2025-05-12
*/

@Composable
fun QuranDataLoadingUI(mainState: MainState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Загрузка данных:", style = MaterialTheme.typography.titleMedium)

        LoadingItem(
            isLoaded = mainState.isChaptersLoaded,
            loadingText = "Загружается список сур...",
            successText = "Список сур успешно загружен!"
        )
        LoadingItem(
            isLoaded = mainState.isChaptersArabicsLoaded,
            loadingText = "Загружается арабский текст...",
            successText = "Арабский текст успешно загружен!"
        )
        /*LoadingItem(
            isLoaded = mainState.isChaptersAudiosLoaded,
            loadingText = "Загружается аудио...",
            successText = "Аудио успешно загружено!"
        )*/
        LoadingItem(
            isLoaded = mainState.isChaptersTranslationsLoaded,
            loadingText = "Загружается перевод...",
            successText = "Перевод успешно загружен!"
        )
    }
}