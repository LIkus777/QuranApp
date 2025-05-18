package com.zaur.features.surah.screen.surah_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.zaur.features.surah.viewmodel.SurahChooseViewModel
import com.zaur.features.surah.viewmodel.ThemeViewModel
import com.zaur.navigation.Screen
import com.zaur.presentation.ui.DarkThemeColors
import com.zaur.presentation.ui.LightThemeColors

/**
* @author Zaur
* @since 2025-05-12
*/

@Composable
fun SurahChooseMenu(
    themeViewModel: ThemeViewModel,
    surahChooseViewModel: SurahChooseViewModel,
    navController: NavController,
    modifier: Modifier,
) {
    val isDarkTheme = themeViewModel.themeState().collectAsState().value.isDarkTheme
    val colors = if (isDarkTheme) DarkThemeColors else LightThemeColors
    Box(
        Modifier
            .background(colors.cardBackground)
            .fillMaxHeight()
            .width(LocalConfiguration.current.screenWidthDp.dp / 1.5f)
    ) {
        LaunchedEffect(Unit) {
            surahChooseViewModel.getAllChapters()
        }

        val textState = surahChooseViewModel.textState().collectAsState()
        val chapters = textState.value.chapters()

        var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
        val tabTitles = listOf("Суры", "Джузы")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            Row {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        modifier = Modifier.weight(1f),
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = {
                            Text(
                                title,
                                fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal
                            )
                        })
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (selectedTabIndex == 0) itemsIndexed(chapters) { index, chapter ->
                    SurahChooseItem(
                        number = chapter.number(),
                        englishName = chapter.englishName(),
                        arabicName = chapter.name(),
                        numberOfAyats = chapter.numberOfAyahs().toInt(),
                        revelationType = chapter.revelationType(),
                        colors = colors,
                        modifier = Modifier.clickable {
                            navController.navigate(
                                Screen.SurahDetail.createRoute(
                                    chapter.number().toInt(), chapter.englishName()
                                )
                            )
                        })
                }
                else {
                    item {
                        Text("Здесь будут джузы", color = Color.Gray)
                    }
                }
            }
        }
    }
}