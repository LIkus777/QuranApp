package com.zaur.features.surah.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.zaur.features.surah.viewmodel.factory.MainViewModelFactory
import com.zaur.navigation.Screen

@Composable
fun MainScreen(navController: NavHostController, mainViewModelFactory: MainViewModelFactory) {

    val mainViewModel = remember { mainViewModelFactory.create() }
    val mainState by mainViewModel.quranState().collectAsState()

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
            mainViewModel.loadQuranData()
            mainViewModel.loadChaptersArabic()
            mainViewModel.loadChaptersAudio(1..114, reciter = "ar.alafasy")
            mainViewModel.loadChaptersTranslate(1..114, translator = "ru.kuliev")
        }) {
            Text("Начать загрузку данных")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            navController.navigate(Screen.SurahChoose.route)
        }) {
            Text("Перейти к Корану")
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