package com.zaur.features.surah.screen.surah_choose

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.zaur.domain.al_quran_cloud.use_case.QuranTextUseCase
import com.zaur.domain.storage.theme.ThemeUseCase
import com.zaur.features.surah.fakes.FakeOfflineRepos
import com.zaur.features.surah.fakes.FakeQTextRCloud
import com.zaur.features.surah.fakes.FakeQTextRLocal
import com.zaur.features.surah.fakes.FakeQuranStorage
import com.zaur.features.surah.fakes.FakeThemeStorage
import com.zaur.features.surah.observables.SurahChooseObservable
import com.zaur.presentation.ui.ui_state.aqc.QuranTextUIState
import com.zaur.features.surah.viewmodel.SurahChooseViewModel
import com.zaur.features.surah.viewmodel.ThemeViewModel
import com.zaur.navigation.Screen
import com.zaur.presentation.ui.DarkThemeColors
import com.zaur.presentation.ui.LightThemeColors
import com.zaur.presentation.ui.ModernSurahText
import com.zaur.presentation.ui.QuranAppTheme

/**
* @author Zaur
* @since 2025-05-12
*/

@Composable
fun SurahChooseScreen(
    themeViewModel: ThemeViewModel,
    surahChooseViewModel: SurahChooseViewModel,
    navController: NavController,
) {

    val isDarkTheme = themeViewModel.themeState().collectAsState().value.isDarkTheme
    val colors = if (isDarkTheme) DarkThemeColors else LightThemeColors

    LaunchedEffect(surahChooseViewModel) {
        Log.d("TAG", "LaunchedEffect(Unit) { getAllChaptersCloud() CALLED ")
        surahChooseViewModel.getAllChapters()
    }

    val textState = surahChooseViewModel.textState().collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val chapters = textState.value.chapters() ?: emptyList() // Избегаем повторного ?. вызова
        itemsIndexed(chapters) { index, chapter ->
            if (index == 0) {
                Spacer(Modifier.height(20.dp))
            } else {
                Spacer(Modifier.height(6.dp))
            }
            ModernSurahText(
                colors = colors,
                number = chapter.number(),
                englishName = chapter.englishName(),
                arabicName = chapter.name(),
                numberOfAyats = chapter.numberOfAyahs().toInt(),
                revelationType = chapter.revelationType(),
                modifier = Modifier.clickable {
                    navController.navigate(
                        Screen.SurahDetail.createRoute(
                            chapter.number().toInt(), chapter.englishName()
                        )
                    ) {
                        // Удаляем предыдущий SurahDetail из стека, чтобы не возвращаться к нему
                        popUpTo(Screen.SurahDetail.route) {
                            inclusive = true // полностью убрать предыдущий SurahDetail
                        }
                        launchSingleTop = true // чтобы не дублировать в стеке одинаковые экраны
                    }
                })
            Spacer(Modifier.height(6.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SurahChoosePreview() {
    val fakeNavController = rememberNavController()
    QuranAppTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SurahChooseScreen(
                themeViewModel = ThemeViewModel.Base(
                    themeUseCase = ThemeUseCase(FakeThemeStorage())
                ), surahChooseViewModel = SurahChooseViewModel.Base(
                    observable = SurahChooseObservable.Base(
                        QuranTextUIState.Base()
                    ), quranTextUseCase = QuranTextUseCase.Base(
                        FakeQuranStorage(),
                        FakeOfflineRepos(),
                        FakeQTextRLocal(),
                        FakeQTextRCloud()
                    )
                ), navController = fakeNavController
            )
        }
    }
}
