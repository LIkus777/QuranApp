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
import com.zaur.features.surah.ui_state.main.MainState
import com.zaur.navigation.Screen

@Composable
fun QuranDataLoadingUI(mainState: MainState, navController: NavHostController) {
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
            successText = "Список сур успешно загружен и сохранён!"
        )

        LoadingItem(
            isLoaded = mainState.isChaptersArabicsLoaded,
            loadingText = "Загружается арабский текст...",
            successText = "Арабский текст успешно загружен и сохранён!"
        )

        LoadingItem(
            isLoaded = mainState.isChaptersAudiosLoaded,
            loadingText = "Загружается аудио...",
            successText = "Аудио успешно загружено и сохранено!"
        )

        LoadingItem(
            isLoaded = mainState.isChaptersTranslationsLoaded,
            loadingText = "Загружается перевод...",
            successText = "Перевод успешно загружен и сохранён!"
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            navController.navigate(Screen.SurahChoose.route)
        }) {
            Text("Перейти к Корану")
        }
    }
}