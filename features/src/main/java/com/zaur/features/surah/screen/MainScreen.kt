package com.zaur.features.surah.screen

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.zaur.navigation.Screen

@Composable
fun MainScreen(navController: NavHostController) {
    Button(
        onClick = {
            navController.navigate(Screen.SurahChoose)
        },
        content = {
            Text("Коран")
        }
    )
}