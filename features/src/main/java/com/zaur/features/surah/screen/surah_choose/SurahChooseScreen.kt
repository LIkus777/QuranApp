package com.zaur.features.surah.screen.surah_choose

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.zaur.data.al_quran_aqc.utils.getFirstAyaOfJuz
import com.zaur.data.al_quran_aqc.utils.getSurahName
import com.zaur.data.al_quran_aqc.utils.pageToSurahMap
import com.zaur.features.surah.viewmodel.SurahChooseViewModel
import com.zaur.features.surah.viewmodel.ThemeViewModel
import com.zaur.navigation.Screen
import com.zaur.presentation.ui.DarkThemeColors
import com.zaur.presentation.ui.LightThemeColors
import com.zaur.presentation.ui.ModernSurahText
import com.zaur.presentation.ui.QuranAppTheme
import com.zaur.presentation.ui.SearchOverlay
import com.zaur.presentation.ui.SurahChooseTopBarComponent

/**
 * @author Zaur
 * @since 2025-05-12
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurahChooseScreen(
    themeViewModel: ThemeViewModel,
    surahChooseViewModel: SurahChooseViewModel,
    navController: NavController,
    onClickPlayer: () -> Unit,
) {
    val isDarkTheme = themeViewModel.themeState().collectAsState().value.isDarkTheme
    val colors = if (isDarkTheme) DarkThemeColors else LightThemeColors

    val textState = surahChooseViewModel.textState().collectAsState()
    var showPicker by remember { mutableStateOf(false) }
    var searchVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        surahChooseViewModel.getAllChapters()
    }

    Scaffold(
        topBar = {
            SurahChooseTopBarComponent(
                modifier = Modifier.fillMaxWidth(),
                colors = colors,
                onClickQuranLabel = { showPicker = true },
                onClickSearch = { searchVisible = true },
                onClickPlayer = { onClickPlayer() },
            )
        },
        containerColor = colors.background
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val chapters = textState.value.chapters() ?: emptyList()
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
                                chapter.number().toInt(),
                                chapter.englishName()
                            )
                        ) {
                            popUpTo(Screen.SurahDetail.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    })
                if (index == chapters.lastIndex) {
                    Spacer(Modifier.height(60.dp))
                } else {
                    Spacer(Modifier.height(6.dp))
                }
            }
        }
    }

    AnimatedVisibility(
        visible = searchVisible,
        enter = expandVertically(
            expandFrom = Alignment.Top, initialHeight = { 1 }
        ) + fadeIn(animationSpec = tween(500)),
        exit = shrinkVertically(
            shrinkTowards = Alignment.Top, targetHeight = { 1 }
        ) + fadeOut(animationSpec = tween(500))
    ) {
        SearchOverlay(colors = colors, onDismiss = { searchVisible = false })
    }

    QuranPickerDialog(
        isVisible = showPicker,
        colors = colors,
        onDismiss = { showPicker = false },
        onPageSelected = { pageNumber ->
            val (surahNum, surahNameRu) = pageToSurahMap[pageNumber]!!
            navController.navigate(
                Screen.PageDetail.createRoute(
                    pageNumber = pageNumber,
                    surahNumber = surahNum,
                    surahName = surahNameRu,
                    highlightAyah = 0
                )
            ) {
                popUpTo(Screen.PageDetail.route) { inclusive = true }
                launchSingleTop = true
            }
        },
        onSurahAndAyahSelected = { surahNumber, ayahNumber ->
            val surahName = getSurahName(surahNumber)
            navController.navigate(
                Screen.SurahDetail.createRoute(
                    surahNumber = surahNumber,
                    surahName = surahName,
                    highlightAyah = ayahNumber
                )
            ) {
                popUpTo(Screen.SurahDetail.route) { inclusive = true }
                launchSingleTop = true
            }
        },
        onJuzSelected = { juzNumber ->
            val firstAya = getFirstAyaOfJuz(juzNumber)
            val surahNum = firstAya.surahNumber.toInt()
            val surahName = getSurahName(surahNum)
            val ayahNum = firstAya.numberInSurah.toInt()
            navController.navigate(
                Screen.JuzDetail.createRoute(
                    juzNumber = juzNumber,
                    surahNumber = surahNum,
                    surahName = surahName,
                    highlightAyah = ayahNum
                )
            ) {
                popUpTo(Screen.JuzDetail.route) { inclusive = true }
                launchSingleTop = true
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun SurahChoosePreview() {
    rememberNavController()
    QuranAppTheme {
/*
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
*/
    }
}
