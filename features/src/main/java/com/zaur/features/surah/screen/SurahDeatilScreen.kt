package com.zaur.features.surah.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun SurahDetailScreen(surahNumber: Int, controller: NavHostController) {
    Text("$surahNumber")
}